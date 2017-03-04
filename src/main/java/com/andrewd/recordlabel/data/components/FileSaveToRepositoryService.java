package com.andrewd.recordlabel.data.components;

import com.andrewd.recordlabel.data.entities.ContentBase;

public interface FileSaveToRepositoryService {

    void save(ContentBase owner, String[] fileNames);
}