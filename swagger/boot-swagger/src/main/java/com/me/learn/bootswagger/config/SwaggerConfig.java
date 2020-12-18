/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.bootswagger.config;


import com.google.common.base.Predicate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.actuate.endpoint.http.ApiVersion;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author Administrator
 * @date 2020/12/9 23:18
 * Project Name: boot-swagger
 */

@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "swagger.enable", havingValue = "true")
//public class SwaggerConfig  extends WebMvcConfigurerAdapter implements InitializingBean {
public class SwaggerConfig  extends WebMvcConfigurerAdapter  {
    @Value("${swagger.enable}")
    private boolean enableSwagger;
    //
    @Value("${swagger.env}")
    private String env;

    @Value("${project.env}-${project.version}")
    private String version;

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/api/v2/api-docs", "/v2/api-docs").setKeepQueryParams(true);
        registry.addRedirectViewController("/api/swagger-resources/configuration/ui","/swagger-resources/configuration/ui");
        registry.addRedirectViewController("/api/swagger-resources/configuration/security","/swagger-resources/configuration/security");
        registry.addRedirectViewController("/api/swagger-resources", "/swagger-resources");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/**").addResourceLocations("classpath:/META-INF/resources/");
    }

    // swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis((Predicate<RequestHandler>) RequestHandlerSelectors.basePackage("com.me.learn.bootswagger"))
                .paths(PathSelectors.any())
                .build();
    }

    // 构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                // 创建人信息
                .contact(new Contact("Jed Li", "enlbi@163.com", "http://melearn.org"))
                // 版本号
                .version("1.0")
                // 描述
                .description("API 描述")
                .build();
    }

//    private Docket buildDocket(String groupName) {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .groupName(groupName)
//                .select()
//                .apis(method -> {
//                    // 每个方法会进入这里进行判断并归类到不同分组，**请不要调换下面两段代码的顺序，在方法上的注解有优先级**
//
//                    // 该方法上标注了版本
//                    if (method.isAnnotatedWith(ApiVersion.class)) {
//                        ApiVersion apiVersion = method.getHandlerMethod().getMethodAnnotation(ApiVersion.class);
//                        if (apiVersion.value() != null && apiVersion.value().length != 0) {
//                            if (Arrays.asList(apiVersion.value()).contains(groupName)) {
//                                return true;
//                            }
//                        }
//
//                    }
//
//                    // 方法所在的类是否标注了?
//                    ApiVersion annotationOnClass = method.getHandlerMethod().getBeanType().getAnnotation(ApiVersion.class);
//                    if (annotationOnClass != null) {
//                        if (annotationOnClass.value() != null && annotationOnClass.value().length != 0) {
//                            if (Arrays.asList(annotationOnClass.value()).contains(groupName)) {
//                                return true;
//                            }
//                        }
//                    }
//                    return false;
//                })
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    /**
//     * 动态得创建Docket bean
//     *
//     * @throws Exception
//     */
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        // ApiConstantVersion 里面定义的每个变量会成为一个docket
//        Class<ApiVersionConstant> clazz = ApiVersionConstant.class;
//        Field[] declaredFields = clazz.getDeclaredFields();
//
//        // 动态注入bean
//        AutowireCapableBeanFactory autowireCapableBeanFactory = applicationContext.getAutowireCapableBeanFactory();
//        if (autowireCapableBeanFactory instanceof DefaultListableBeanFactory) {
//            DefaultListableBeanFactory capableBeanFactory = (DefaultListableBeanFactory) autowireCapableBeanFactory;
//            for (Field declaredField : declaredFields) {
//
//                // 要注意 "工厂名和方法名"，意思是用这个bean的指定方法创建docket
//                AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
//                        .genericBeanDefinition()
//                        .setFactoryMethodOnBean("buildDocket", "swaggerConfig")
//                        .addConstructorArgValue(declaredField.get(ApiVersionConstant.class)).getBeanDefinition();
//                capableBeanFactory.registerBeanDefinition(declaredField.getName(), beanDefinition);
//            }
//        }
//    }

}
