package com.andrewd.recordlabel.common.service;

public class BatchCountCalculator {
    public static int calc(int totalItems, int itemsPerPage) {
        return (int)Math.ceil((double)totalItems / (double)itemsPerPage);
    }
}
