package com.andrewd.recordlabel.common.service;

import com.andrewd.recordlabel.common.PrintStatus;
import com.andrewd.recordlabel.supermodel.IdTextModel;
import org.junit.*;

import java.util.*;

public class EnumDataGetterDefaultTests {

    private EnumDataGetterDefault enumData;

    @Before
    public void Init() {
        enumData = new EnumDataGetterDefault();
    }

    @Test(expected = IllegalArgumentException.class)
    public void MustThrowWhenArgIsNotEnum() {
        enumData.getEnumInfo(String.class);
    }

    @Test
    public void TransformEnum() {
        List<IdTextModel> result = enumData.getEnumInfo(PrintStatus.class);

        Assert.assertEquals("Result is supposed to contain 2 entries", 2, result.size());
        Assert.assertEquals("Result's first entry is supposed to be InPrint with its ordinal value",
                PrintStatus.InPrint.ordinal(), result.get(0).id);
        Assert.assertEquals("Result's second entry is supposed to be OutOfPrint with its ordinal value",
                PrintStatus.OutOfPrint.ordinal(), result.get(1).id);

        Assert.assertEquals("Result's first entry is supposed to be InPrint with its name value",
                PrintStatus.InPrint.name(), result.get(0).text);
        Assert.assertEquals("Result's first entry is supposed to be OutOfPrint with its name value",
                PrintStatus.OutOfPrint.name(), result.get(1).text);
    }
}