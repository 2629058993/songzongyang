package com.itzy.biz.Brand;

import com.itzy.mapper.Brand.BrandMapper;
import com.itzzy.commons.Datableresult;
import com.itzzy.po.Brand;
import com.itzzy.pram.BrandPram;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IBrandServiceImpl implements IBrandService {
    @Resource
    private BrandMapper brandmapper;

    @Override
    public Datableresult brandlist(BrandPram brandpram) {
        List<Brand> brandList = brandmapper.brandlist(brandpram);
        long querycount = brandmapper.querycount(brandpram);
        return new Datableresult(brandpram.getDraw(), querycount, querycount, brandList);
    }

    @Override
    public List<Brand> findbytypeid(long fatherid) {
        return brandmapper.findbytypeid(fatherid);
    }
}
