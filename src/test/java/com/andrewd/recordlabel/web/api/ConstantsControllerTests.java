package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.common.service.EnumDataGetter;
import com.andrewd.recordlabel.web.model.Constants;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ConstantsControllerTests {

    @InjectMocks
    ConstantsController controller;

    @Mock
    EnumDataGetter enumData;

    @Test
    public void get_MustReturnSomething() {
        Constants result = controller.get();

        Assert.assertNotNull(result);
    }

    @Test
    public void get_MustReturnValuesForTemplates() {
        Constants result = controller.get();

        Assert.assertNotNull(result.defaultReference);
        Assert.assertNotNull(result.defaultTrack);
    }

    @Test
    public void get_MustReturnPrintStatuses() {
        Constants result = controller.get();

        Mockito.verify(enumData, Mockito.times(1)).getEnumInfo(Matchers.eq(PrintStatus.class));

        Assert.assertNotNull(result.printStatuses);
    }

    @Test
    public void get_MustReturnReferenceTypes() {
        Constants result = controller.get();

        Mockito.verify(enumData, Mockito.times(1)).getEnumInfo(Matchers.eq(ReferenceType.class));

        Assert.assertNotNull(result.referenceTypes);
    }

    @Test
    public void get_MaxDateMustBeCurrentYear() {
        short currentYear = (short)Calendar.getInstance().get(Calendar.YEAR);
        Constants result = controller.get();

        Assert.assertEquals("Max date must be set to current year", currentYear, result.maxDate);
    }
}