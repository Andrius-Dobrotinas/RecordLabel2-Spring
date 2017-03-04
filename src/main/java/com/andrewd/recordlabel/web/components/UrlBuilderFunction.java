package com.andrewd.recordlabel.web.components;

@FunctionalInterface
public interface UrlBuilderFunction {
    String build(String virtualPath, String fileName);
}