package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.supermodels.Release;
import com.andrewd.recordlabel.web.models.ReleaseListItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ReleaseBatchToListItemVMBatchTransformer implements
        Function<BatchedResult<Release>, BatchedResult<ReleaseListItemViewModel>> {

    @Autowired
    private Function<Release, ReleaseListItemViewModel> listViewModelBuilder;

    @Override
    public BatchedResult<ReleaseListItemViewModel> apply(BatchedResult<Release> source) {
        BatchedResult<ReleaseListItemViewModel> viewModels = new BatchedResult<>();

        viewModels.batchCount = source.batchCount;
        viewModels.entries = source.entries.stream()
                .map(listViewModelBuilder::apply)
                .collect(Collectors.toList());

        return viewModels;
    }
}