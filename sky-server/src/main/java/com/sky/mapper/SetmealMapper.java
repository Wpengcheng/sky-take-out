package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Setmeal;
import com.sky.vo.DishItemVO;
import com.sky.vo.SetmealVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SetmealMapper extends BaseMapper<Setmeal> {


	/**
	 * 根据套餐id查询菜品选项
	 * @param setmealId
	 * @return
	 */
	@Select("select sd.name, sd.copies, d.image, d.description " +
		        "from setmeal_dish sd left join dish d on sd.dish_id = d.id " +
		        "where sd.setmeal_id = #{setmealId}")
	List<DishItemVO> getDishItemBySetmealId(Long setmealId);

	/**
	 * 根据id查询套餐和套餐菜品关系
	 * @param id
	 * @return
	 */
	SetmealVO getByIdWithDish(Long id);

}