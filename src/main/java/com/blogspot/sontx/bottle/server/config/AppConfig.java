package com.blogspot.sontx.bottle.server.config;

import com.blogspot.sontx.bottle.server.ApplicationRoot;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Bootstrap the spring application and set package to scan controllers and resources also
 * supports web MVC that automatically creates and registers default JSON and XML converters.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = ApplicationRoot.class)
public class AppConfig {
}
