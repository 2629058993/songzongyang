package com.zyzzz.mapper.Shop;


import com.itzzy.po.Shop;
import com.itzzy.pram.ShopPram;

import java.util.List;

public interface ShopMapper {
    List<Shop> shoplist(ShopPram shoppram);

    long querycount(ShopPram shoppram);

    void addtest(Shop shop);

    Shop findshopbyid(Integer productid);
}
