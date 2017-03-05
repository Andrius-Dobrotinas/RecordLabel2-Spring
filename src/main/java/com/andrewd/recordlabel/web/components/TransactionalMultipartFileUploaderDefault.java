package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.web.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.*;
import java.util.*;
import java.util.function.Function;

@Service
public class TransactionalMultipartFileUploaderDefault implements TransactionalMultipartFileUploader {

    @Autowired
    private MultipartFileSaver multipartFileSaver;

    @Override
    public <Output> Output uploadFiles(MultipartFile[] files, File directory,
                                       Function<List<File>, Output> runOnSavedFiles)
            throws FileSaveException {

        if (files == null)
            throw new IllegalArgumentException("files is null");
        if (directory == null)
            throw new IllegalArgumentException("directory is null");
        if (runOnSavedFiles == null)
            throw new IllegalArgumentException("runOnSavedFiles is null");
        /* TODO: null value for the function could be acceptable,
        just write some unit test to cover that scenario
         */

        // Save files to the target directory
        List<File> savedFiles = new ArrayList<>();
        try {
            for(int i = 0; i < files.length; i++) {
                File saved = multipartFileSaver.saveFile(files[i], directory);
                savedFiles.add(saved);
            }
            return runOnSavedFiles.apply(savedFiles);
        }
        catch (Exception e) {
            // Rollback: delete saved files on exception
            savedFiles.forEach(File::delete);

            throw new FileSaveException(e);
        }
    }
}