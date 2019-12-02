package com.itfh.mapper;


import com.itzzy.po.Type;
import com.itzzy.pram.TypePram;

import java.util.List;

public interface TypeMapper {
    List<Type> typeList(TypePram typepram);

    long querycount(TypePram typepram);

    List<Type> jointbytype();

}
