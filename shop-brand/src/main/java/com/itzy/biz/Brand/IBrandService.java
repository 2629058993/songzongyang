package com.itzy.biz.Brand;


import com.itzzy.commons.Datableresult;
import com.itzzy.po.Brand;
import com.itzzy.pram.BrandPram;

import java.util.List;

public interface IBrandService {
    Datableresult brandlist(BrandPram brandpram);

    List<Brand> findbytypeid(long fatherid);
}
