package com.realorder.biz;

import com.itzzy.commons.ServerResponse;

public interface IROrderService {
    ServerResponse addrorder(Long recipientsid);

    ServerResponse initEWMA(String outTradeNo);

    ServerResponse detectionisqr(long orderid, String outTradeNo);
}
