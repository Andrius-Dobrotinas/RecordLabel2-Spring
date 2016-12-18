package com.andrewd.recordlabel.common.service;

import com.andrewd.recordlabel.supermodel.IdTextModel;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnumDataGetterDefault implements EnumDataGetter {

    public List<IdTextModel> getEnumInfo(Class enumType) {
        List<IdTextModel> result = new ArrayList<>();

        if (!enumType.isEnum())
            throw new IllegalArgumentException("enumType's value is not of enum type");

        for(Object entry : enumType.getEnumConstants()){
            Enum e = (Enum)entry;
            IdTextModel model = new IdTextModel(e.ordinal(), e.name());
            result.add(model);
        }
        return result;
    }
}