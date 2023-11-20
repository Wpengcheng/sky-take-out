package com.sky.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sky.converter.DishConverter;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Category;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.entity.Employee;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class DishServiceImpl implements DishService {

    private final DishMapper dishMapper;
    private final DishFlavorMapper dishFlavorMapper;
    private final DishConverter dishConverter;
    private final CategoryMapper categoryMapper;

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
        wrapper.eq(dishPageQueryDTO.getCategoryId()!=null,Dish::getCategoryId,dishPageQueryDTO.getCategoryId());
        wrapper.eq(dishPageQueryDTO.getStatus()!=null,Dish::getStatus,dishPageQueryDTO.getStatus());
        IPage<Dish> dishIPage = dishMapper.selectPage(new Page<>(page, size), wrapper);
        long total = dishIPage.getTotal();
        List<Dish> records = dishIPage.getRecords();
        List<DishVO> dishVOS = dishConverter.entity2VoList(records);
        dishVOS.forEach(d->{
            Long categoryId = d.getCategoryId();
            Category category = categoryMapper.selectById(categoryId);
            d.setCategoryName(category.getName());
        });
        return new PageResult(total, dishVOS);
    }

}