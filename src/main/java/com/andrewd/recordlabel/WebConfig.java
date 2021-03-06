package com.andrewd.recordlabel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    public static final String IMAGES_VIRTUAL_PATH_SETTINGS_KEY = "recordlabel.img.virtualpath";
    public static final String IMAGES_PHYSICAL_PATH_SETTINGS_KEY = "recordlabel.img.path";
    public static final String IMAGES_PREFIX = "recordlabel.img.prefix";

    public static final String THUMBNAILS_PHYSICAL_PATH_SETTINGS_KEY = "recordlabel.thumb.path";
    public static final String THUMBNAILS_WIDTH_SETTINGS_KEY = "recordlabel.thumb.width";
    public static final String THUMBNAILS_PREFIX_SETTINGS_KEY = "recordlabel.thumb.prefix";
    public static final String THUMBNAILS_RESIZE_METHOD_SETTINGS_KEY = "recordlabel.thumb.resizemethod";

    public static final String ITEMS_PER_PAGE_SETTINGS_KEY = "recordlabel.web.itemsPerPage";

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imgVirtualPath = String.format("%s**", env.getProperty(IMAGES_VIRTUAL_PATH_SETTINGS_KEY));
        String imgPhysicalPath = String.format("file:///%s", env.getProperty(IMAGES_PHYSICAL_PATH_SETTINGS_KEY));

        registry.addResourceHandler(imgVirtualPath)
                .addResourceLocations(imgPhysicalPath);
    }
}