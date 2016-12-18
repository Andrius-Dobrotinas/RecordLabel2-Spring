package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.supermodel.MediaType;
import org.junit.*;

import java.util.*;

public class MediaTypesControllerTests {

    MediaTypesController controller;

    @Before
    public void Init() {
        controller = new MediaTypesController();
    }


    @Test
    public void get_MustReturnSomething() {
        List<MediaType> result = controller.get();

        Assert.assertNotNull(result);
    }
}