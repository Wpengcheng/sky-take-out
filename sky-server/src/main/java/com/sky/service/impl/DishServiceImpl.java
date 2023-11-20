package com.sky.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.converter.DishConverter;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

	private final DishMapper dishMapper;
	private final DishFlavorMapper dishFlavorMapper;
	private final DishConverter dishConverter;
	private final CategoryMapper categoryMapper;
	private final SetmealDishMapper setmealDishMapper;

	/**
	 * 新增菜品和对应的口味
	 *
	 * @param dishDTO
	 */
	@Transactional
	public void saveWithFlavor(DishDTO dishDTO) {


		Dish dish = dishConverter.dto2Entity(dishDTO);
		dishMapper.insert(dish);
		Long dishId = dish.getId();

		List<DishFlavor> flavors = dishDTO.getFlavors();
		if (!CollectionUtils.isEmpty(flavors)) {
			flavors.forEach(d -> {
				d.setDishId(dishId);
				dishFlavorMapper.insert(d);
			});
		}

	}

	@Override
	public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
		int size = dishPageQueryDTO.getPageSize();
		int page = dishPageQueryDTO.getPage();
		LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
		wrapper.like(StringUtils.isNotBlank(dishPageQueryDTO.getName()), Dish::getName, dishPageQueryDTO.getName());
		wrapper.eq(dishPageQueryDTO.getCategoryId() != null, Dish::getCategoryId, dishPageQueryDTO.getCategoryId());
		wrapper.eq(dishPageQueryDTO.getStatus() != null, Dish::getStatus, dishPageQueryDTO.getStatus());
		IPage<Dish> dishIPage = dishMapper.selectPage(new Page<>(page, size), wrapper);
		long total = dishIPage.getTotal();
		List<Dish> records = dishIPage.getRecords();
		List<DishVO> dishVOS = dishConverter.entity2VoList(records);
		dishVOS.forEach(d -> {
			Long categoryId = d.getCategoryId();
			Category category = categoryMapper.selectById(categoryId);
			d.setCategoryName(category.getName());
		});
		return new PageResult(total, dishVOS);
	}


	/**
	 * 菜品批量删除
	 *
	 * @param ids
	 */
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(List<Long> ids) {
		//判断当前菜品是否能够删除---是否存在起售中的菜品？？
		for (Long id : ids) {
			Dish dish = dishMapper.selectById(id);
			if (dish.getStatus() == StatusConstant.ENABLE) {
				//当前菜品处于起售中，不能删除
				throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
			}
		}

		//判断当前菜品是否能够删除---是否被套餐关联了？？
		List<Long> setmealIds = setmealDishMapper.selectList(new LambdaQueryWrapper<SetmealDish>()
                                                             .in(!CollectionUtils.isEmpty(ids), SetmealDish::getDishId, ids))
                                                             .stream().map(SetmealDish::getId).collect(Collectors.toList());
		if (!CollectionUtils.isEmpty(setmealIds)) {
			//当前菜品被套餐关联了，不能删除
			throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
		}

		//删除菜品表中的菜品数据
		for (Long id : ids) {
			dishMapper.deleteById(id);
			//删除菜品关联的口味数据
			dishFlavorMapper.delete(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id));
		}
	}

	/**
	 * 根据id查询菜品和对应的口味数据
	 *
	 * @param id
	 * @return
	 */
	public DishVO getByIdWithFlavor(Long id) {
		Dish dish = dishMapper.selectById(id);
		List<DishFlavor> dishFlavors = dishFlavorMapper.selectList(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId, id));
		DishVO dishVO = dishConverter.entity2Vo(dish);
		dishVO.setFlavors(dishFlavors);
		return dishVO;
	}

    /**
     * 根据id修改菜品基本信息和对应的口味信息
     *
     * @param dishDTO
     */
    public void updateWithFlavor(DishDTO dishDTO) {
        Dish dish = dishConverter.dto2Entity(dishDTO);
		dish.setId(dishDTO.getId());
		dishMapper.updateById(dish);

        //删除原有的口味数据
        dishFlavorMapper.delete(new LambdaQueryWrapper<DishFlavor>().eq(DishFlavor::getDishId,dishDTO.getId()));

        //重新插入口味数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (!CollectionUtils.isEmpty(flavors)) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishDTO.getId());
				dishFlavorMapper.insert(dishFlavor);
            });

        }
    }

}