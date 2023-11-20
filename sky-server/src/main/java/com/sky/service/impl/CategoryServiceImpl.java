package com.sky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sky.constant.StatusConstant;
import com.sky.converter.CategoryConverter;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import com.sky.utils.RandomNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 方唐镜
 * @Create 2023-11-14 21:29
 * @Description
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    private final CategoryMapper categoryMapper;
    private final CategoryConverter categoryConverter;

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = categoryConverter.dto2Entity(categoryDTO);
        category.setId(RandomNumberGenerator.generateRandomLong(10));
        category.setStatus(StatusConstant.ENABLE);
        categoryMapper.insert(category);
    }

    @Override
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        int size = categoryPageQueryDTO.getPageSize();
        int page = categoryPageQueryDTO.getPage();
        String name = categoryPageQueryDTO.getName();
        Integer type = categoryPageQueryDTO.getType();
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(categoryPageQueryDTO.getName()), Category::getName, name);
        wrapper.eq(type!=null,Category::getType,type);
        wrapper.orderByDesc(Category::getSort);
        IPage<Category> categoryIPage = categoryMapper.selectPage(new Page<>(page, size), wrapper);
        long total = categoryIPage.getTotal();
        List<Category> records = categoryIPage.getRecords();
        return new PageResult(total, records);
    }

    @Override
    public void deleteById(Long id) {
        categoryMapper.deleteById(id);

    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryConverter.dto2Entity(categoryDTO);
        categoryMapper.update(category,new LambdaQueryWrapper<Category>().eq(Category::getId,categoryDTO.getId()));
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        categoryMapper.update(Category.builder().status(status).build(),
                new LambdaQueryWrapper<Category>().eq(Category::getId,id));
    }

    @Override
    public List<Category> list(Integer type) {
        return categoryMapper.selectList(new LambdaQueryWrapper<Category>().eq(type!=null,Category::getType,type));
    }
}
