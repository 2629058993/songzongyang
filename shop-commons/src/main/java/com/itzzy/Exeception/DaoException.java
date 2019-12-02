package com.itzzy.Exeception;

import com.itzzy.commons.ResopnseEnum;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class DaoException extends RuntimeException {
    private Integer code;


    public DaoException(ResopnseEnum resopnseEnum) {
        super(resopnseEnum.getMsg());
        this.code = resopnseEnum.getCode();
    }
}