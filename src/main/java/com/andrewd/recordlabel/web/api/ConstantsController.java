package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.common.service.EnumDataGetter;
import com.andrewd.recordlabel.web.model.Constants;
import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.supermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

@RestController
@RequestMapping("api/constants/")
public class ConstantsController {

    @Autowired
    private EnumDataGetter enumData;

    @RequestMapping(value = "get", method = RequestMethod.GET)
    public Constants get() {
        Constants model = new Constants();
        model.printStatuses =  enumData.getEnumInfo(PrintStatus.class);
        model.referenceTypes = enumData.getEnumInfo(ReferenceType.class);
        model.defaultReference = new Reference();
        model.defaultTrack = new Track();
        model.minDate = 1950;
        model.maxDate = Calendar.getInstance().get(Calendar.YEAR);

        return model;
    }
}