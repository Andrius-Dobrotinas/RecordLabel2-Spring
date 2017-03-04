package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.services.ReleaseService;
import com.andrewd.recordlabel.supermodels.MediaType;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class MediaTypesControllerTests {

    @InjectMocks
    MediaTypesController controller;

    @Mock
    ReleaseService svc;

    @Test
    public void getMediaTypeList_MustHitTheServiceAndReturnSomething() {
        List<MediaType> fake = new ArrayList<>();
        fake.add(new MediaType());

        Mockito.when(svc.getMediaTypeList()).thenReturn(fake);
        List<MediaType> result = controller.get();

        Assert.assertNotNull("Must return a non-null result", result);
        Assert.assertEquals("Result must match what the service returns", fake, result);
    }

    @Test
    public void getMetadataList_MustHitTheServiceAndReturnEmptyListIfNothingFound() {
        List<MediaType> fake = new ArrayList<>();

        Mockito.when(svc.getMediaTypeList()).thenReturn(fake);
        List<MediaType> result = controller.get();

        Assert.assertNotNull("Must return a non-null result even if no results are present", result);
    }
}