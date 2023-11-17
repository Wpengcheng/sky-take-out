package com.sky.converter;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @Author 方唐镜
 * @Create 2023-11-12 11:23
 * @Description
 */
@Mapper(componentModel = "Spring")
public interface DishConverter extends BaseConverter<DishDTO, Dish>{

	DishConverter INSTANCE = Mappers.getMapper(DishConverter.class);


}
