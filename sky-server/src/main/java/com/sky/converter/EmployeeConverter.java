package com.sky.converter;

import com.sky.dto.EmployeeDTO;
import com.sky.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author 方唐镜
 * @Create 2023-11-12 11:23
 * @Description
 */
@Mapper(componentModel = "Spring")
public interface EmployeeConverter extends BaseConverter<EmployeeDTO,Employee>{

	EmployeeConverter INSTANCE = Mappers.getMapper(EmployeeConverter.class);


}
