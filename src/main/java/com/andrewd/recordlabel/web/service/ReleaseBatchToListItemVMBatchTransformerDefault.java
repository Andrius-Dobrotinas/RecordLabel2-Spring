package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.supermodel.Release;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ReleaseBatchToListItemVMBatchTransformerDefault implements ReleaseBatchToListItemVMBatchTransformer {

    @Autowired
    private ReleaseListItemViewModelBuilder listViewModelBuilder;

    @Override
    public BatchedResult<ReleaseListItemViewModel> transform(BatchedResult<Release> source) {
        BatchedResult<ReleaseListItemViewModel> viewModels = new BatchedResult<>();

        viewModels.batchCount = source.batchCount;
        viewModels.entries = source.entries.stream()
                .map(listViewModelBuilder::build)
                .collect(Collectors.toList());

        return viewModels;
    }
}