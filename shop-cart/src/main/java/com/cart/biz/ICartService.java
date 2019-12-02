package com.cart.biz;

import java.util.Map;

public interface ICartService {
    void addcarts(String productid);

    Map<String, Object> catcarts();

    void incrcart(String shopid);

    void reduce(String shopid);

    void remove(String shopid);

    void uncheck(String shopid);

    void isallcheck(String checkclass);

    void updatecount(Integer count, String shopid);
}
