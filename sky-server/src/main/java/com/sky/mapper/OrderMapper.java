package com.sky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper extends BaseMapper<Orders> {

	/**
	 * 根据状态和下单时间查询订单
	 * @param status
	 * @param orderTime
	 */
	@Select("select * from orders where status = #{status} and order_time < #{orderTime}")
	List<Orders> getByStatusAndOrdertimeLT(Integer status, LocalDateTime orderTime);

}
