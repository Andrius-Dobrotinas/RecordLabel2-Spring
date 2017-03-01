package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.*;
import com.andrewd.recordlabel.supermodel.Release;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class ReleaseBatchToListItemVMBatchTransformerDefaultTests {

    @InjectMocks
    ReleaseBatchToListItemVMBatchTransformerDefault transformer;

    @Mock
    ReleaseListItemViewModelBuilder listViewModelBuilder;

    String title;
    int batchCount;
    List<Release> entries;
    Release release1;
    BatchedResult<Release> originalBatchedResult;

    @Before
    public void before() {
        title = "Fun House";
        batchCount = 5;

        entries = new ArrayList<>();
        release1 = getRelease(title);
        entries.add(release1);

        originalBatchedResult = new BatchedResult<>(entries, batchCount);
    }

    @Test
    public void mustReturnBatchedListResult() {
        BatchedResult<ReleaseListItemViewModel> result = transformer.transform(originalBatchedResult);

        assertNotNull(result);
    }

    @Test
    public void mustCopyBatchCount() {
        BatchedResult<ReleaseListItemViewModel> result = transformer.transform(originalBatchedResult);

        assertEquals(batchCount, result.batchCount);
    }

    @Test
    public void mustUseReleaseListItemViewModelBuilderToTransformEachEntry() {
        Release release2 = getRelease("Raw Power");
        entries.add(release2);

        transformer.transform(originalBatchedResult);

        Mockito.verify(listViewModelBuilder, times(2)).build(Matchers.any());
        Mockito.verify(listViewModelBuilder, times(1)).build(Matchers.eq(release1));
        Mockito.verify(listViewModelBuilder, times(1)).build(Matchers.eq(release2));
    }

    @Test
    public void mustContainTransformedEntries() {
        Release release2 = getRelease("Raw Power");
        entries.add(release2);

        ReleaseListItemViewModel viewModel1 = new ReleaseListItemViewModel(release1, "");
        ReleaseListItemViewModel viewModel2 = new ReleaseListItemViewModel(release2, "");

        Mockito.when(listViewModelBuilder.build(Matchers.eq(release1))).
                thenReturn(viewModel1);
        Mockito.when(listViewModelBuilder.build(Matchers.eq(release2))).
                thenReturn(viewModel2);

        // Run
        BatchedResult<ReleaseListItemViewModel> result = transformer.transform(originalBatchedResult);

        // Verify
        assertNotNull("Result must contain entries", result.entries);
        assertEquals("Result must contain exactly the same number of entries",
                2, result.entries.size());
        assertTrue("Result must contain every transformed entry",
                result.entries.stream().anyMatch(x -> x == viewModel1));
        assertTrue("Result must contain every transformed entry",
                result.entries.stream().anyMatch(x -> x == viewModel2));
    }

    private static Release getRelease(String title) {
        Release release = new Release();
        release.id = 1;
        release.title = title;
        release.printStatus = PrintStatus.OutOfPrint;
        release.date = 1973;

        return release;
    }
}