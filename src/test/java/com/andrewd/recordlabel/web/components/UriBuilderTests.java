package com.andrewd.recordlabel.web.components;

import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UriBuilderTests {

    @InjectMocks
    UriBuilder builder;

    @Test
    public void mustAppendFileNameToVirtualPath_ifVirtualPathEndsWithABackslash() {
        String virtualPath = "path/";
        String fileName = "file.ext";
        String expectedResult = virtualPath + fileName;

        String result = builder.build(virtualPath, fileName);

        Assert.assertEquals(expectedResult, result);
    }

    @Test
    public void mustAppendFileNameToVirtualPath_insertingABackSlash_ifVirtualPathDoesNotEndWithABackslash() {
        String virtualPath = "path";
        String fileName = "file.ext";
        String expectedResult = virtualPath + "/" + fileName;

        String result = builder.build(virtualPath, fileName);

        Assert.assertEquals("A backslash must be inserted between virtual path and file name when the " +
                "supplied virtual path does not end with a backslash", expectedResult, result);
    }

    @Test
    public void ifVirtualPathIsEmpty_mustSimplyReturnFileName() {
        String virtualPath = "";
        String fileName = "file.ext";
        String expectedResult = fileName;

        String result = builder.build(virtualPath, fileName);

        Assert.assertEquals(expectedResult, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void ifFileNameIsEmpty_mustThrowAnException() {
        String virtualPath = "";
        String fileName = "";

        builder.build(virtualPath, fileName);
    }
}