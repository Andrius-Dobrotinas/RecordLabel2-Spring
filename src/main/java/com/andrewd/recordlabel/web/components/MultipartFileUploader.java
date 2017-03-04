package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.data.entities.ContentBase;
import com.andrewd.recordlabel.web.io.FileSaveException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface MultipartFileUploader {
    String[] uploadFiles(ContentBase owner, MultipartFile[] files, File directory) throws FileSaveException;
}