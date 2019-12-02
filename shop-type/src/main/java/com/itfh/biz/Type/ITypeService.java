package com.itfh.biz.Type;

import com.itzzy.commons.Datableresult;
import com.itzzy.po.Type;
import com.itzzy.pram.TypePram;

import java.util.List;

public interface ITypeService {
    Datableresult typeList(TypePram typepram);

    List<Type> jointbytype();
}
