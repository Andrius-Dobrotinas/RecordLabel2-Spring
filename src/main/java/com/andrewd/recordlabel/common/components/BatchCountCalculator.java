package com.andrewd.recordlabel.common.components;

public final class BatchCountCalculator {
    private BatchCountCalculator() {

    }

    public static int calc(int totalItems, int itemsPerPage) {
        if (totalItems == 0) return 1;
        return (int)Math.ceil((double)totalItems / (double)itemsPerPage);
    }
}
