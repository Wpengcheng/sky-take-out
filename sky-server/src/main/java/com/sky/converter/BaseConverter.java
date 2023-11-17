package com.sky.converter;

import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author 方唐镜
 * @Create 2023-11-12 11:23
 * @Description
 */
//@Mapper(componentModel = "Spring")
public interface BaseConverter<DTO, Entity>  {

	//BaseConverter INSTANCE = Mappers.getMapper(BaseConverter.class);

	Entity dtoToEntity(DTO dto);

	DTO entityToDto(Entity entity);

}
