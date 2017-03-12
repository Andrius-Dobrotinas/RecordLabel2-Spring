package com.andrewd.recordlabel.io;

import org.springframework.stereotype.Component;

@Component
public class FileExtensionGetterDefault implements FileExtensionGetter {

    public String getFileExtension(String fileName) {
        return getFileExtension(fileName, false);
    }

    public String getFileExtension(String fileName, boolean includeDot) {
        if (fileName == null)
            throw new IllegalArgumentException("fileName is null");

        int index = fileName.lastIndexOf('.');

        if (index == -1)
            return "";
        else
        {
            if (!includeDot)
                ++index;
            return fileName.substring(index);
        }
    }
}