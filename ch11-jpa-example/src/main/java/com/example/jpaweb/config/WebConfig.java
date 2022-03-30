package com.example.jpaweb.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Arrays;

@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 정적리소스 url/file 위치 매핑
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(
                        "/assets/**",
                        "/css/**",
                        "/fonts/**",
                        "/js/**"
                )
                .addResourceLocations(
                        "classpath:/static/assets/",
                        "classpath:/static/css/",
                        "classpath:/static/fonts/",
                        "classpath:/static/js/"
                );

    }

    @Bean
    public ContentNegotiatingViewResolver viewResolver(ContentNegotiationManager contentNegotiationManager,
                                                       BeanNameViewResolver beanNameViewResolver,
                                                       ThymeleafViewResolver thymeleafViewResolver
    ) {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        MappingJackson2JsonView jackson2JsonView = new MappingJackson2JsonView();

        resolver.setViewResolvers(Arrays.asList(thymeleafViewResolver, beanNameViewResolver));
        resolver.setDefaultViews(Arrays.asList(jackson2JsonView));
        resolver.setContentNegotiationManager(contentNegotiationManager);

        return resolver;
    }

}
