package com.sky.converter;

import com.sky.dto.CategoryDTO;
import com.sky.dto.EmployeeDTO;
import com.sky.entity.Category;
import com.sky.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author 方唐镜
 * @Create 2023-11-12 11:23
 * @Description
 */
@Mapper(componentModel = "Spring")
public interface CategoryConverter extends BaseConverter<CategoryDTO,Category>{

	CategoryConverter INSTANCE = Mappers.getMapper(CategoryConverter.class);


}
