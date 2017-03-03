package com.andrewd.recordlabel.web.service;

@FunctionalInterface
public interface UrlBuilderFunction {
    String build(String virtualPath, String fileName);
}