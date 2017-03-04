package com.andrewd.recordlabel.web.components;

import org.springframework.stereotype.Component;

@Component
public class UrlBuilder implements UrlBuilderFunction {

    @Override
    public String build(String virtualPath, String fileName) {
        if (virtualPath == null)
            throw new IllegalArgumentException("virtualPath is null");
        if (fileName == null || fileName == "")
            throw new IllegalArgumentException("fileName is null or empty");

        if (virtualPath == "")
            return fileName;

        if (!virtualPath.endsWith("/"))
            virtualPath = virtualPath + "/";

        return virtualPath + fileName;
    }
}