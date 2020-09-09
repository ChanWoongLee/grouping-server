package com.covengers.grouping.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    private static final String CLASSPATH_RESOURCE_LOCATIONS = "resources/groupimages/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/groupimages/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
}