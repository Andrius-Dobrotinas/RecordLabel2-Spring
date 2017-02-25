package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.data.model.ContentBase;
import com.andrewd.recordlabel.web.io.FileSaveException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface MultipartFileUploadService {
    String[] uploadFiles(ContentBase owner, MultipartFile[] files, File directory) throws FileSaveException;
}