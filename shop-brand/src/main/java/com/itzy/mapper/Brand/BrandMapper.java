package com.itzy.mapper.Brand;


import com.itzzy.po.Brand;
import com.itzzy.pram.BrandPram;

import java.util.List;

public interface BrandMapper {
    List<Brand> brandlist(BrandPram brandpram);

    long querycount(BrandPram brandpram);

    List<Brand> findbytypeid(long fatherid);
}
