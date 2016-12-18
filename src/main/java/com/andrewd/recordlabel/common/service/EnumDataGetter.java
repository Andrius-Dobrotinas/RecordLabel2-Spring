package com.andrewd.recordlabel.common.service;

import com.andrewd.recordlabel.supermodel.IdTextModel;

import java.util.List;

public interface EnumDataGetter {
    List<IdTextModel> getEnumInfo(Class enumType);
}
