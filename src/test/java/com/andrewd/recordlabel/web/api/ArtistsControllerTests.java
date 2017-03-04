package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.services.ReleaseService;
import com.andrewd.recordlabel.supermodels.ArtistBarebones;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ArtistsControllerTests {

    @InjectMocks
    ArtistsController controller;

    @Mock
    ReleaseService svc;

    @Test
    public void getList_MustHitTheService() {
        controller.getList();

        Mockito.verify(svc, times(1)).getArtistBarebonesList();
    }

    @Test
    public void getList_MustReturnList() {
        List<ArtistBarebones> list = new ArrayList<>();
        Mockito.when(svc.getArtistBarebonesList()).thenReturn(list);

        // Run
        List<ArtistBarebones> result = controller.getList();

        // Verify
        Assert.assertEquals(list, result);
    }
}