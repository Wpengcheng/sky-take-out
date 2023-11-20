package com.sky.converter;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.vo.DishVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author 方唐镜
 * @Create 2023-11-12 11:23
 * @Description
 */
@Mapper(componentModel = "Spring")
public interface DishConverter extends BaseConverter<DishDTO, Dish>{

	DishConverter INSTANCE = Mappers.getMapper(DishConverter.class);

	List<DishVO> entity2VoList(List<Dish> dishList);
	DishVO entity2Vo(Dish dish);


}
