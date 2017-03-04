package com.andrewd.recordlabel.common.components;

import com.andrewd.recordlabel.supermodels.IdTextModel;

import java.util.List;

public interface EnumDataGetter {
    List<IdTextModel> getEnumInfo(Class enumType);
}
