package com.zyzzz.biz.Shop;


import com.itzzy.commons.Datableresult;
import com.itzzy.po.Shop;
import com.itzzy.pram.ShopPram;

public interface IShopService {
    Datableresult shoplist(ShopPram shoppram);

    void testspring();

    Shop findshopbyid(Integer productid);
}
