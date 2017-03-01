package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.common.BatchedResult;
import com.andrewd.recordlabel.supermodel.Release;
import com.andrewd.recordlabel.web.model.ReleaseListItemViewModel;

public interface ReleaseBatchToListItemVMBatchTransformer {
    BatchedResult<ReleaseListItemViewModel> transform(BatchedResult<Release> source);
}