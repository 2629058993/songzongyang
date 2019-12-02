package com.itfh.biz.Type;


import com.itfh.mapper.TypeMapper;
import com.itzzy.commons.Datableresult;
import com.itzzy.po.Type;
import com.itzzy.pram.TypePram;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ITypeServiceImpl implements ITypeService {
    @Resource
    private TypeMapper typemapper;

    @Override
    public Datableresult typeList(TypePram typepram) {
        List<Type> typelist = typemapper.typeList(typepram);
        long count = typemapper.querycount(typepram);
        return new Datableresult(typepram.getDraw(), count, count, typelist);
    }

    @Override
    public List<Type> jointbytype() {
        return typemapper.jointbytype();
    }
}
