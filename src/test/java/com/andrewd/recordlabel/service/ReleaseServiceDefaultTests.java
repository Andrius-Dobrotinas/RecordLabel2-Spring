package com.andrewd.recordlabel.service;

import com.andrewd.recordlabel.data.model.MediaType;
import com.andrewd.recordlabel.data.model.Release;
import com.andrewd.recordlabel.data.repository.ReleaseRepository;
import com.andrewd.recordlabel.data.service.EntityToModelTransformer;
import com.andrewd.recordlabel.data.service.ReleaseServiceDefault;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseServiceDefaultTests {

    @InjectMocks
    ReleaseServiceDefault svc = new ReleaseServiceDefault();

    @Mock
    ReleaseRepository repository;

    @Mock
    EntityToModelTransformer entityTransformer;

    @Test
    public void getRelease_MustReturnSuperModel() {
        int entityId = 1;
        Release entity = new Release();
        com.andrewd.recordlabel.supermodel.Release expectedModel = new com.andrewd.recordlabel.supermodel.Release();

        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(entity);
        Mockito.when(entityTransformer.getRelease(Matchers.any(Release.class))).thenReturn(expectedModel);

        com.andrewd.recordlabel.supermodel.Release model = svc.getRelease(entityId);

        Mockito.verify(entityTransformer, Mockito.times(1)).getRelease(entity);
        Assert.assertEquals(expectedModel, model);
    }

    @Test
    public void getRelease_MustReturnNullWhenNotFound() {
        int entityId = 1;
        Release entity = new Release();
        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(entity);

        com.andrewd.recordlabel.supermodel.Release model = svc.getRelease(entityId);

        Assert.assertNull(model);
    }

    @Test
    public void getMediaTypeList_MustReturnSuperModel() {
        List<com.andrewd.recordlabel.supermodel.MediaType> expectedModels = new ArrayList<>();
        expectedModels.add(new com.andrewd.recordlabel.supermodel.MediaType());
        List<com.andrewd.recordlabel.supermodel.MediaType> expectedModels2 = new ArrayList<>();
        List<MediaType> entities = new ArrayList<>();

        Mockito.when(repository.getMediaTypeList()).thenReturn(entities);

        List<com.andrewd.recordlabel.supermodel.MediaType> result = svc.getMediaTypeList();

        Mockito.verify(repository, Mockito.times(1)).getMediaTypeList();
        Mockito.verify(entityTransformer, Mockito.times(1)).transformList(Mockito.eq(entities), Mockito.any());
    }

    /*@Test
    public void saveRelease_MustSave() {
        Release entity = new Release();
        com.andrewd.recordlabel.supermodel.Release expectedModel = new com.andrewd.recordlabel.supermodel.Release();

        Mockito.when(repository.getRelease(Matchers.anyInt())).thenReturn(entity);
        Mockito.when(entityTransformer.getRelease(Matchers.any(Release.class))).thenReturn(expectedModel);

        com.andrewd.recordlabel.supermodel.Release model = svc.getRelease(entityId);

        Mockito.verify(repository, Mockito.times(1)).save(entity);
    }*/
}