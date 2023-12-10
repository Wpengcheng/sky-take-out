package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.sky.entity.Setmeal;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import com.sky.vo.DishItemVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 套餐业务实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SetmealServiceImpl implements SetmealService {

    private final SetmealMapper setmealMapper;
    private final SetmealDishMapper setmealDishMapper;
    private final DishMapper dishMapper;

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    public List<Setmeal> list(Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.isNotBlank(setmeal.getName()),Setmeal::getName,setmeal.getName());
        wrapper.eq(Objects.nonNull(setmeal.getCategoryId()),Setmeal::getCategoryId,setmeal.getCategoryId());
        wrapper.eq(Objects.nonNull(setmeal.getStatus()),Setmeal::getStatus,setmeal.getStatus());
        return setmealMapper.selectList(wrapper);
    }

    /**
     * 根据id查询菜品选项
     * @param id
     * @return
     */
    public List<DishItemVO> getDishItemById(Long id) {
        return setmealMapper.getDishItemBySetmealId(id);
    }
}