package com.andrewd.recordlabel.io;

/**
 * Returns file extension
 */
public interface FileExtensionGetter {

    /**
     * Returns file extension
     * @param filename name of file whose extension to return
     * @return file extension
     */
    String getFileExtension(String filename);

    /**
     * Returns file extension
     * @param filename name of file whose extension to return
     * @param includeDot indication of whether to return extension
     *                   with a preceding dot
     * @return file extension
     */
    String getFileExtension(String filename, boolean includeDot);
}