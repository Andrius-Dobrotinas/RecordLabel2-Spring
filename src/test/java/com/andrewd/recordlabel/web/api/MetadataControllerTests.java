package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.supermodel.Metadata;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class MetadataControllerTests {

    @InjectMocks
    MetadataController controller;

    @Mock
    ReleaseService svc;

    @Test
    public void getMetadataList_MustHitTheServiceAndReturnSomething() {
        List<Metadata> fake = new ArrayList<>();
        fake.add(new Metadata());

        Mockito.when(svc.getMetadataList()).thenReturn(fake);

        List<Metadata> result = controller.get();

        Assert.assertNotNull("Must return a non-null result", result);
        Assert.assertEquals("Result must match what the service returns", fake, result);
    }

    @Test
    public void getMetadataList_MustHitTheServiceAndReturnEmptyListIfNothingFound() {
        List<Metadata> fake = new ArrayList<>();

        Mockito.when(svc.getMetadataList()).thenReturn(fake);

        List<Metadata> result = controller.get();

        Assert.assertNotNull("Must return a non-null result even if no results are present", result);
    }
}