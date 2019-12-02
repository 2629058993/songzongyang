package com.zyzzz.biz.Shop;

import com.itzzy.Vo.ShopVo;
import com.itzzy.commons.Datableresult;
import com.itzzy.po.Shop;
import com.itzzy.pram.ShopPram;
import com.itzzy.util.dateutil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class IShopServiceImpl implements IShopService {
    @Resource
    private com.zyzzz.mapper.Shop.ShopMapper shopmapper;



    @Override
    public Datableresult shoplist(ShopPram shoppram) {
        List<Shop> shoplist = shopmapper.shoplist(shoppram);
        List<ShopVo> getshopvolist = getshopvolist(shoplist);
        long count = shopmapper.querycount(shoppram);
        return new Datableresult(shoppram.getDraw(), count, count, getshopvolist);
    }


    //po转vo
    public List<ShopVo> getshopvolist(List<Shop> shopList) {
        List<ShopVo> shopVoList = new ArrayList<ShopVo>();
        for (Shop shop : shopList) {
            ShopVo vo = new ShopVo();
            vo.setId(shop.getId());
            vo.setTypeid(shop.getTypeid() == 3 ? "数码家电" : "生活");
            vo.setName(shop.getName());
            vo.setSubtitle(shop.getSubtitle());
            vo.setMain_img(shop.getMain_img());
            vo.setSub_imgs(shop.getSub_imgs());
            vo.setDetail(shop.getDetail());
            vo.setPrice(shop.getPrice());
            vo.setStock(shop.getStock());
            vo.setStatus(shop.getStatus() == 0 ? "上架" : "未上架");
            vo.setCreate_time(dateutil.date2string(shop.getCreate_time(), dateutil.YYYY_MM_DD_HH_MM_SS));
            vo.setUpdate_time(dateutil.date2string(shop.getUpdate_time(), dateutil.YYYY_MM_DD_HH_MM_SS));
            shopVoList.add(vo);
        }
        return shopVoList;
    }


    //test没什么用啊不用在意
    @Transactional
    @Override
    public void testspring() {
        Shop shop = new Shop();
        shop.setTypeid(1);
        shopmapper.addtest(shop);
        throw new RuntimeException("一不小心丢个异常");
    }

    @Override
    public Shop findshopbyid(Integer productid) {
        return shopmapper.findshopbyid(productid);
    }

}
