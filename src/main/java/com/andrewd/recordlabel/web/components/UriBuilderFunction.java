package com.andrewd.recordlabel.web.components;

@FunctionalInterface
public interface UriBuilderFunction {
    String build(String virtualPath, String fileName);
}