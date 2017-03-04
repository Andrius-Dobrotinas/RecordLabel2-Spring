package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.data.services.ReleaseService;
import com.andrewd.recordlabel.supermodels.*;
import com.andrewd.recordlabel.common.ObjectNotFoundException;
import com.andrewd.recordlabel.web.models.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.function.Function;

@RunWith(MockitoJUnitRunner.class)
public class ReleasesControllerTests {

    @InjectMocks
    ReleasesController controller;

    @Mock
    ReleaseService svc;

    @Mock
    Function<Release, ReleaseViewModel> viewModelBuilder;

    @Mock
    Function<BatchedResult<Release>, BatchedResult<ReleaseListItemViewModel>> listViewModelTransformer;

    final int defaultBatchSize = 2;

    @Before
    public void init() {
        controller.itemsPerPage = defaultBatchSize;
    }

    @Test
    public void getTemplate_mustReturnTemplateWithCurrentYear() {
        short currentYear = (short)Calendar.getInstance().get(Calendar.YEAR);

        ReleaseSlim result = controller.getTemplate();

        Assert.assertNotNull(result);
        Assert.assertEquals("Release template's date must be set to current year",
                currentYear, result.date);
    }

    @Test
    public void get_mustRetrieveModelFromTheService() {
        int id = 5;

        try {
            controller.get(id);
        } catch (ObjectNotFoundException e) {
            e.printStackTrace();
        }

        Mockito.verify(svc, Mockito.times(1)).getRelease(Matchers.eq(id));
    }

    @Test
    public void get_mustTransformSuperModelToViewModel() throws ObjectNotFoundException {
        int id = 5;
        Release release = new Release();
        release.id = id;

        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(release);

        // Run
        controller.get(id);

        Mockito.verify(viewModelBuilder, Mockito.times(1)).apply(Matchers.eq(release));
    }

    @Test
    public void get_mustReturnASuccessResponseWithViewModel() throws ObjectNotFoundException {
        int id = 5;
        Release release = new Release();
        release.id = id;
        ReleaseViewModel viewModel = new ReleaseViewModel();

        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(release);
        Mockito.when(viewModelBuilder.apply(Matchers.eq(release)))
                .thenReturn(viewModel);

        // Run
        ReleaseViewModel result = controller.get(id);

        // Verify
        Assert.assertNotNull("Must return a non-null object", result);
        Assert.assertEquals("Must return a view model", viewModel, result);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void get_mustThrowException_whenEntityNotFound() throws ObjectNotFoundException {
        int id = 5;
        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(null);

        // Run
        controller.get(id);
    }

    @Test
    public void getForEdit_mustRetrieveModelFromTheService()  {
        int id = 5;

        try {
            controller.getForEdit(id);
        } catch (ObjectNotFoundException e) {

        }

        Mockito.verify(svc, Mockito.times(1)).getReleaseSlim(Matchers.eq(id));
    }

    @Test
    public void getForEdit_mustReturnSuccessResponseWithModel() throws ObjectNotFoundException {
        int id = 5;
        ReleaseSlim model = new ReleaseSlim();
        model.id = id;

        Mockito.when(svc.getReleaseSlim(Matchers.eq(id))).thenReturn(model);

        // Run
        ReleaseSlim result = controller.getForEdit(id);

        // Verify
        Assert.assertNotNull("Must return a non-null object", result);
        Assert.assertEquals("Must return the super model", model, result);
    }

    @Test(expected = ObjectNotFoundException.class)
    public void getForEdit_mustThrowException_whenEntityNotFound() throws ObjectNotFoundException {
        int id = 5;
        Mockito.when(svc.getReleaseSlim(Matchers.eq(id))).thenReturn(null);

        // Run
        controller.getForEdit(id);
    }

    @Test
    public void getBatch_mustHitTheServiceWithSuppliedValues() {
        int batchNumber = 1;
        int batchSize = 2;

        controller.getBatch(batchNumber, batchSize);

        Mockito.verify(svc, Mockito.times(1))
                .getReleases(Matchers.eq(batchNumber), Matchers.eq(batchSize));
    }

    @Test
    public void getBatch_mustUseDefaultValueForNumberIfInvalidValueSupplied_zero() {
        controller.getBatch(0, 1);

        Mockito.verify(svc, Mockito.times(1))
                .getReleases(Matchers.eq(controller.DEFAULT_BATCH_NUMBER), Matchers.anyInt());
    }

    @Test
    public void getBatch_mustUseDefaultValueForNumberIfInvalidValueSupplied_negative() {
        controller.getBatch(-1, 1);

        Mockito.verify(svc, Mockito.times(1))
                .getReleases(Matchers.eq(controller.DEFAULT_BATCH_NUMBER), Matchers.anyInt());
    }

    @Test
    public void getBatch_mustUseDefaultBatchSizeIfInvalidNumberSupplied_zero() {
        controller.getBatch(1, 0);

        Mockito.verify(svc, Mockito.times(1))
                .getReleases(Matchers.anyInt(), Matchers.eq(defaultBatchSize));
    }

    @Test
    public void getBatch_mustUseDefaultBatchSizeIfInvalidNumberSupplied_negative() {
        controller.getBatch(1, -1);

        Mockito.verify(svc, Mockito.times(1))
                .getReleases(Matchers.anyInt(), Matchers.eq(defaultBatchSize));
    }

    @Test
    public void getBatch_mustTransformResultsToListItemViewModelType() {
        int batchNumber = 1;
        int batchSize = 2;

        BatchedResult<Release> model = new BatchedResult<>();
        model.entries = new ArrayList<>();
        model.entries.add(new Release());

        Mockito.when(svc.getReleases(Matchers.eq(batchNumber), Matchers.eq(batchSize)))
                .thenReturn(model);

        // Run
        controller.getBatch(batchNumber, batchSize);

        // Verify
        Mockito.verify(listViewModelTransformer, Mockito.times(1))
                .apply(Matchers.eq(model));
    }

    @Test
    public void getBatch_mustReturnTransformedBatchedResultOfListViewItems() {
        int batchNumber = 1;
        int batchSize = 2;

        BatchedResult<Release> model = new BatchedResult<>(new ArrayList<Release>(), batchSize);

        BatchedResult<ReleaseListItemViewModel> batchedResult = new BatchedResult<>(
                new ArrayList<ReleaseListItemViewModel>(), batchSize);

        Mockito.when(svc.getReleases(Matchers.eq(batchNumber), Matchers.eq(batchSize)))
                .thenReturn(model);
        Mockito.when(listViewModelTransformer.apply(Matchers.eq(model)))
                .thenReturn(batchedResult);

        // Run
        BatchedResult<ReleaseListItemViewModel> result = controller
                .getBatch(batchNumber, batchSize);

        // Verify
        Assert.assertEquals(batchedResult, result);
    }

    @Test
    public void post_mustHitTheService() {
        ReleaseSlim superModel = new ReleaseSlim();
        superModel.id = 1;

        controller.post(superModel);

        Mockito.verify(svc, Mockito.times(1)).save(Matchers.eq(superModel));
    }

    // TODO: save: a case when a model is invalid
}