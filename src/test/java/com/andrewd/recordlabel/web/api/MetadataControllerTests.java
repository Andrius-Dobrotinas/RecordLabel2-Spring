package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.supermodel.Metadata;
import org.junit.*;

import java.util.*;

public class MetadataControllerTests {

    MetadataController controller;

    @Before
    public void Init() {
        controller = new MetadataController();
    }


    @Test
    public void get_MustReturnSomething() {
        List<Metadata> result = controller.get();

        Assert.assertNotNull(result);
    }
}
