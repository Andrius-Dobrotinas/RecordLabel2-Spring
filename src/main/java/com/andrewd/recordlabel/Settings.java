package com.andrewd.recordlabel;

public final class Settings {
    private Settings() {

    }

    // TODO: move the falue to a config file or something
    private static int itemsPerPage = 2;
    public static int getItemsPerPage() {
        return itemsPerPage;
    }
}