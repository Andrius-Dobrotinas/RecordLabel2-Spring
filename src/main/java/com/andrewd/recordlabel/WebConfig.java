package com.andrewd.recordlabel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment env;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String imgVirtualPath = String.format("%s**", env.getProperty("recordlabel.img.virtualpath"));
        String imgPath = String.format("file:///%s", env.getProperty("recordlabel.img.path"));

        registry.addResourceHandler(imgVirtualPath)
                .addResourceLocations(imgPath);
    }
}