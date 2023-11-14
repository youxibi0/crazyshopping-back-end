package com.zjgsu.crazyshopping.service;

import com.zjgsu.crazyshopping.entity.Goods;
import com.zjgsu.crazyshopping.entity.RespGoodsBean;

import java.util.List;

public interface GoodsService {
    public int addGoods(Goods goods);
    public Goods getGoods(int onEnable);
    public RespGoodsBean getAllGoods();
    public int modifyGoods(int id , Goods goods);
    public int deleteGoods(int id);


}
