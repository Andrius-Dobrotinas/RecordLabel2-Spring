package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.data.services.ReleaseService;
import com.andrewd.recordlabel.supermodels.*;
import com.andrewd.recordlabel.web.models.*;
import com.andrewd.recordlabel.web.components.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Calendar;

@RunWith(MockitoJUnitRunner.class)
public class ReleasesControllerTests {

    @InjectMocks
    ReleasesController controller;

    @Mock
    ReleaseService svc;

    @Mock
    ReleaseViewModelBuilder viewModelBuilder;

    @Mock
    ReleaseBatchToListItemVMBatchTransformer listViewModelTransformer;

    final int defaultBatchSize = 2;

    @Before
    public void init() {
        controller.itemsPerPage = defaultBatchSize;
    }

    @Test
    public void getTemplate_MustReturnTemplateWithCurrentYear() {
        short currentYear = (short)Calendar.getInstance().get(Calendar.YEAR);

        ReleaseSlim result = controller.getTemplate();

        Assert.assertNotNull(result);
        Assert.assertEquals("Release template's date must be set to current year", currentYear, result.date);
    }

    @Test
    public void get_MustRetrieveModelFromTheService() {
        int id = 5;

        controller.get(id);

        Mockito.verify(svc, Mockito.times(1)).getRelease(Matchers.eq(id));
    }

    @Test
    public void get_MustTransformSuperModelToViewModel() {
        int id = 5;
        Release release = new Release();
        release.id = id;

        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(release);

        // Run
        controller.get(id);

        Mockito.verify(viewModelBuilder, Mockito.times(1)).apply(Matchers.eq(release));
    }

    @Test
    public void get_MustReturnASuccessResponseWithViewModel() {
        int id = 5;
        Release release = new Release();
        release.id = id;
        ReleaseViewModel viewModel = new ReleaseViewModel();

        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(release);
        Mockito.when(viewModelBuilder.apply(Matchers.eq(release)))
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
    public void get_MustReturnNotFoundResponseIfEntityNotFound() {
        int id = 5;
        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(null);

        // Run
        ResponseEntity<ReleaseViewModel> response = controller.get(id);

        // Verify
        // Not supposed to call this when no model found in the repository
        Mockito.verifyZeroInteractions(viewModelBuilder);

        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getForEdit_MustRetrieveModelFromTheService() {
        int id = 5;

        controller.getForEdit(id);

        Mockito.verify(svc, Mockito.times(1)).getReleaseSlim(Matchers.eq(id));
    }

    @Test
    public void getForEdit_MustReturnSuccessResponseWithModel() {
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
    public void getForEdit_MustReturnNotFoundResponseIfEntityNotFound() {
        int id = 5;
        Mockito.when(svc.getReleaseSlim(Matchers.eq(id))).thenReturn(null);

        // Run
        ResponseEntity<ReleaseSlim> result = controller.getForEdit(id);

        // Verify
        Assert.assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    public void getBatch_MustHitTheServiceWithSuppliedValues() {
        int batchNumber = 1;
        int batchSize = 2;

        controller.getBatch(batchNumber, batchSize);

        Mockito.verify(svc, Mockito.times(1)).getReleases(Matchers.eq(batchNumber), Matchers.eq(batchSize));
    }

    @Test
    public void getBatch_MustUseDefaultValueForNumberIfInvalidValueSupplied_Zero() {
        controller.getBatch(0, 1);

        Mockito.verify(svc, Mockito.times(1))
                .getReleases(Matchers.eq(controller.DEFAULT_BATCH_NUMBER), Matchers.anyInt());
    }

    @Test
    public void getBatch_MustUseDefaultValueForNumberIfInvalidValueSupplied_Negative() {
        controller.getBatch(-1, 1);

        Mockito.verify(svc, Mockito.times(1))
                .getReleases(Matchers.eq(controller.DEFAULT_BATCH_NUMBER), Matchers.anyInt());
    }

    @Test
    public void getBatch_MustUseDefaultBatchSizeIfInvalidNumberSupplied_Zero() {
        controller.getBatch(1, 0);

        Mockito.verify(svc, Mockito.times(1)).getReleases(Matchers.anyInt(), Matchers.eq(defaultBatchSize));
    }

    @Test
    public void getBatch_MustUseDefaultBatchSizeIfInvalidNumberSupplied_Negative() {
        controller.getBatch(1, -1);

        Mockito.verify(svc, Mockito.times(1)).getReleases(Matchers.anyInt(), Matchers.eq(defaultBatchSize));
    }

    @Test
    public void getBatch_MustTransformResultsToListItemViewModelType() {
        int batchNumber = 1;
        int batchSize = 2;

        BatchedResult<Release> model = new BatchedResult<>();
        model.entries = new ArrayList<>();
        model.entries.add(new Release());

        Mockito.when(svc.getReleases(Matchers.eq(batchNumber), Matchers.eq(batchSize))).thenReturn(model);

        // Run
        BatchedResult<ReleaseListItemViewModel> result = controller.getBatch(batchNumber, batchSize);

        // Verify
        Mockito.verify(listViewModelTransformer, Mockito.times(1)).apply(Matchers.eq(model));
    }

    @Test
    public void getBatch_MustReturnTransformedBatchedResultOfListViewItems() {
        int batchNumber = 1;
        int batchSize = 2;

        BatchedResult<Release> model = new BatchedResult<>(new ArrayList<Release>(), batchSize);

        BatchedResult<ReleaseListItemViewModel> batchedResult = new BatchedResult<>(
                new ArrayList<ReleaseListItemViewModel>(), batchSize);

        Mockito.when(svc.getReleases(Matchers.eq(batchNumber), Matchers.eq(batchSize))).thenReturn(model);
        Mockito.when(listViewModelTransformer.apply(Matchers.eq(model))).thenReturn(batchedResult);

        // Run
        BatchedResult<ReleaseListItemViewModel> result = controller.getBatch(batchNumber, batchSize);

        // Verify
        Assert.assertEquals(batchedResult, result);
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