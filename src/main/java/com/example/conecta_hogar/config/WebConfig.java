package com.example.conecta_hogar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Expone la carpeta 'uploads' para que las fotos sean accesibles mediante URL públicas
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/");
    }
}