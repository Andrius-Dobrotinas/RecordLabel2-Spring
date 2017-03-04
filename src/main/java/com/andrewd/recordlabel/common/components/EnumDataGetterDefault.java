package com.andrewd.recordlabel.common.components;

import com.andrewd.recordlabel.supermodels.IdTextModel;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class EnumDataGetterDefault implements EnumDataGetter {

    @Override
    public List<IdTextModel> getEnumInfo(Class enumType) {
        if (!enumType.isEnum())
            throw new IllegalArgumentException("enumType's value is not of enum type");

        return Arrays.stream(enumType.getEnumConstants())
                .map(entry -> {
                    Enum e = (Enum)entry;
                    return new IdTextModel(e.ordinal(), e.name());
                }).collect(Collectors.toList());
    }
}