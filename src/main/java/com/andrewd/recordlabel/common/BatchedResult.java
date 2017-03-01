package com.andrewd.recordlabel.common;

import java.util.List;

public class BatchedResult<T> {
    public List<T> entries;
    public int batchCount;

    public BatchedResult() {

    }

    public BatchedResult(List<T> entries, int batchCount) {
        this.entries = entries;
        this.batchCount = batchCount;
    }
}