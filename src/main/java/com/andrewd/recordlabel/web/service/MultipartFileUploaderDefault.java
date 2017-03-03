package com.andrewd.recordlabel.web.service;

import com.andrewd.recordlabel.data.model.ContentBase;
import com.andrewd.recordlabel.data.service.*;
import com.andrewd.recordlabel.web.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;

@Service
public class MultipartFileUploaderDefault implements MultipartFileUploader {

    @Autowired
    private MultipartFileSaver multipartFileSaver;

    @Autowired
    private FileSaveToRepositoryService fileSaveToRepositoryService;

    @Override
    public String[] uploadFiles(ContentBase owner, MultipartFile[] files, File directory)
            throws FileSaveException {

        if (owner == null)
            throw new IllegalArgumentException("owner is null");
        if (files == null)
            throw new IllegalArgumentException("files is null");
        if (directory == null)
            throw new IllegalArgumentException("directory is null");

        // Save files to the target directory
        String[] fileNames = new String[files.length];
        List<File> savedFiles = new ArrayList<>();
        try {
            for(int i = 0; i < files.length; i++) {
                File saved = multipartFileSaver.saveFile(files[i], directory);
                savedFiles.add(saved);
                fileNames[i] = saved.getName();
            }
        } catch (IOException e) {
            // Rollback: delete saved files on exception
            savedFiles.forEach(x -> x.delete());

            throw new FileSaveException(e);
        }

        // Save entries to the database
        try {
            fileSaveToRepositoryService.save(owner, fileNames);
        }
        catch (Exception e) {
            // Rollback: delete saved files on exception
            savedFiles.forEach(x -> x.delete());

            throw new FileSaveException(e);
        }

        return fileNames;
    }
}