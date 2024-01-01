package com.sky.service;

import com.sky.context.BaseContext;
import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * 清空购物车商品
     */
    void cleanShoppingCart();

    /**
     * 查看购物车
     * @return
     */
    List<ShoppingCart> showShoppingCart();
}