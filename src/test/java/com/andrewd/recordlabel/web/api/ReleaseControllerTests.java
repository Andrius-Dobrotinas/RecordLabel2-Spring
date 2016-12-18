package com.andrewd.recordlabel.web.api;

import com.andrewd.recordlabel.data.service.ReleaseService;
import com.andrewd.recordlabel.supermodel.*;
import com.andrewd.recordlabel.web.model.ReleaseViewModel;
import com.andrewd.recordlabel.web.service.ReleaseViewModelTransformer;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

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
        ReleaseViewModel result = controller.get(id);

        // Verify
        Assert.assertEquals(viewModel, result);
    }

    @Test
    public void get_MustHitTheServiceAndReturnNullIfNothingFound() {
        int id = 5;
        Release release = new Release();
        release.id = id;

        Mockito.when(svc.getRelease(Matchers.eq(id))).thenReturn(null);

        // Run
        ReleaseViewModel result = controller.get(id);

        // Verify
        // Not supposed to call this when no model found in the repository
        Mockito.verifyZeroInteractions(viewModelTransformer);

        Assert.assertEquals(null, result);
    }
}