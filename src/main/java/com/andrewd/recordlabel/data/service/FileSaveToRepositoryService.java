package com.andrewd.recordlabel.data.service;

import com.andrewd.recordlabel.data.model.ContentBase;

public interface FileSaveToRepositoryService {

    void save(ContentBase owner, String[] fileNames);
}