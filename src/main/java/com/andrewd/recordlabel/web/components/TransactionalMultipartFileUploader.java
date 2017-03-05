package com.andrewd.recordlabel.web.components;

import com.andrewd.recordlabel.web.io.FileSaveException;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;
import java.util.function.Function;

/**
 * Represents a function that saves multipart files on file system
 * and runs a function on saved files all in transaction. In case
 * of failure in any step, saved files must be deleted from file
 * system.
 */
@FunctionalInterface
public interface TransactionalMultipartFileUploader {

    /**
     * Saves multipart files on file system and runs a function on
     * saved files all in transaction
     * @param files files to be saved on file system
     * @param directory directory where files must be saved
     * @param runOnSavedFiles function that will be run on saved files
     * @param <Out> return type of the supplied function
     * @return anything returned by the supplied function
     * @throws FileSaveException
     */
    <Out> Out uploadFiles(MultipartFile[] files, File directory,
                          Function<List<File>, Out> runOnSavedFiles)
            throws FileSaveException;
}