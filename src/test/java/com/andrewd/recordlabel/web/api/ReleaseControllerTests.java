package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseViewModel;
import com.andrewd.recordlabel.web.service.ReleaseViewModelTransformer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Calendar;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseControllerTests {

    @InjectMocks
    ReleasesController controller;

    @Mock
    ReleaseService svc;

    @Mock
    ReleaseViewModelTransformer viewModelTransformer;

    @Test
    public void getTemplate_MustReturnTemplateWithCurrentYear() {
        short currentYear = (short)Calendar.getInstance().get(Calendar.YEAR);
        ReleaseSlim result = controller.getTemplate();

        Assert.assertNotNull(result);
        Assert.assertEquals("Release template's date must be set to current year", currentYear, result.date);
    }

    @Test
    public void get_MustHitTheServiceAndTransformModel() {
        int id = 5;
        Release release = new Release();
        release.id = id;
        ReleaseViewModel viewModel = new ReleaseViewModel();

        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(release);
        Mockito.when(viewModelTransformer.transform(Matchers.eq(release)))
                .thenReturn(viewModel);

        // Run
        ResponseEntity<ReleaseViewModel> response = controller.get(id);
        ReleaseViewModel result = response.getBody();

        // Verify
        Assert.assertEquals("HTTP Status of a successful operation must be OK", HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull("Must contain a response body", result);
        Assert.assertEquals("Must return the view model", viewModel, result);
    }

    @Test
    public void get_MustHitTheServiceAndReturnNullIfNothingFound() {
        int id = 5;
        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(null);

        // Run
        ResponseEntity<ReleaseViewModel> response = controller.get(id);

        // Verify
        // Not supposed to call this when no model found in the repository
        Mockito.verifyZeroInteractions(viewModelTransformer);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getForEdit_MustHitTheServiceAndTransformModel() {
        int id = 5;
        ReleaseSlim model = new ReleaseSlim();
        model.id = id;

        Mockito.when(svc.getReleaseSlim(Matchers.eq(id))).thenReturn(model);

        // Run
        ResponseEntity<ReleaseSlim> response = controller.getForEdit(id);
        ReleaseSlim result = response.getBody();

        // Verify
        Assert.assertEquals("HTTP Status of a successful operation must be OK", HttpStatus.OK, response.getStatusCode());
        Assert.assertNotNull("Must contain a response body", result);
        Assert.assertEquals("Must return the super model", model, result);
    }

    @Test
    public void getForEdit_MustHitTheServiceAndReturnNotFoundWhenNotFound() {
        int id = 5;
        Mockito.when(svc.getReleaseSlim(Matchers.eq(id))).thenReturn(null);

        // Run
        ResponseEntity<ReleaseSlim> result = controller.getForEdit(id);

        // Verify
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void getBatch_MustHitTheServiceAndReturn() {
        BatchedResult<Release> model = new BatchedResult<>();
        model.entries = new ArrayList<>();
        model.entries.add(new Release());

        Mockito.when(svc.getReleases(Matchers.anyInt(), Matchers.anyInt())).thenReturn(model);

        // Run
        BatchedResult<Release> result = controller.get(1, 2);

        // Verify
        Mockito.verify(svc, Mockito.times(1)).getReleases(Matchers.anyInt(), Matchers.anyInt());

        Assert.assertEquals(model, result);
    }

    @Test
    public void post_MustHitTheServiceAndReturnProperResponse() {
        ReleaseSlim superModel = new ReleaseSlim();
        superModel.id = 1;

        ResponseEntity response = controller.post(superModel);

        Mockito.verify(svc, Mockito.times(1)).save(Matchers.eq(superModel));

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void post_MustReturnBadRequestOnException() {
        ReleaseSlim superModel = new ReleaseSlim();
        superModel.id = 1;

        Mockito.doThrow(Exception.class).when(svc).save(Matchers.eq(superModel));

        ResponseEntity response = controller.post(superModel);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    // TODO: save: a case when a model is invalid
}