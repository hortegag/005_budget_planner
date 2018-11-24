package com.home.budgetplanner.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.ConversionService;
import org.springframework.binding.convert.service.DefaultConversionService;

import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.webflow.config.AbstractFlowConfiguration;
import org.springframework.webflow.mvc.builder.MvcViewFactoryCreator;
import org.springframework.webflow.security.SecurityFlowExecutionListener;
import org.springframework.webflow.engine.builder.support.FlowBuilderServices;

import org.springframework.web.servlet.ViewResolver;

@Configuration
@Order(1)
public class WebFlowConfig extends AbstractFlowConfiguration {

    @Autowired
    private WebMvcConfig webMvcConfig;

    @Bean
    public FlowDefinitionRegistry flowRegistry() {
        return getFlowDefinitionRegistryBuilder(flowBuilderServices()).addFlowLocation("/WEB-INF/flows/activation-flow.xml", "activationFlow")
                .addFlowLocation("/WEB-INF/flows/identificationType-flow.xml", "identificationTypeFlow")
                .addFlowLocation("/WEB-INF/flows/people-flow.xml", "peopleFlow")
                .addFlowLocation("/WEB-INF/flows/peopleTest-flow.xml", "peopleFlowTest")
                .addFlowLocation("/WEB-INF/flows/peopleTestBootstrap-flow.xml", "peopleTestBootstrap")
                .addFlowLocation("/WEB-INF/flows/transactionType-flow.xml", "transactionTypeFlow")
                .addFlowLocation("/WEB-INF/flows/transaction-flow.xml", "transactionFlow")
                .addFlowLocation("/WEB-INF/flows/statistics-flow.xml", "statisticsFlow")

                
                

                .addFlowLocationPattern("/WEB-INF/flows/*-flow.xml").build();
    }

    @Bean
    public FlowExecutor flowExecutor() {
        return getFlowExecutorBuilder(flowRegistry()).addFlowExecutionListener(new SecurityFlowExecutionListener()).build();
    }

    @Bean
    public FlowBuilderServices flowBuilderServices() {

        DefaultConversionService webFlowConversionService = new DefaultConversionService(this.conversionService);

        // webFlowConversionService.addConverter(new StringToNullConverter());
        // webFlowConversionService.addConverter(this.webMvcConfig.i);

        return getFlowBuilderServicesBuilder().setViewFactoryCreator(mvcViewFactoryCreator()).setConversionService(webFlowConversionService)
                .setDevelopmentMode(true).build();
    }

    /*
     * @Bean public MvcViewFactoryCreator mvcViewFactoryCreator() {
     * MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
     * factoryCreator.setViewResolvers(Collections.singletonList(this.
     * webMvcConfig.viewResolver()));
     * factoryCreator.setUseSpringBeanBinding(true); return factoryCreator; }
     */
    /*
     * 
     * @Bean public MvcViewFactoryCreator mvcViewFactoryCreator() {
     * MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
     * factoryCreator.setViewResolvers(Collections.singletonList(viewResolvers))
     * ; factoryCreator.setUseSpringBeanBinding(true); return factoryCreator; }
     */

    @Bean
    public MvcViewFactoryCreator mvcViewFactoryCreator() {
        MvcViewFactoryCreator factoryCreator = new MvcViewFactoryCreator();
        // factoryCreator.setViewResolvers(Collections.singletonList(this.webMvcConfig.viewResolver()));

        // List<ViewResolver> viewResolvers = new ArrayList<>();

        // Collections.addAll(viewResolvers, this.webMvcConfig.viewResolver(),
        // this.webMvcConfig.ajaxViewResolver());
        // factoryCreator.setViewResolvers(viewResolvers);
        factoryCreator.setViewResolvers(Collections.singletonList(this.webMvcConfig.ajaxViewResolver()));

        factoryCreator.setUseSpringBeanBinding(true);
        return factoryCreator;
    }
    /*
     * @Bean public FlowHandlerMapping flowHandlerMapping() { FlowHandlerMapping
     * flowHandlerMapping = new FlowHandlerMapping();
     * flowHandlerMapping.setInterceptors(new Object[] {
     * this.webMvcConfig.localeChangeInterceptor()});
     * 
     * Object interceptors; flowHandlerMapping.setInterceptors(interceptors);
     * 
     * flowHandlerMapping.setFlowRegistry(flowRegistry()); return
     * flowHandlerMapping; }
     */

    //////////////// deshabilitar temporalmente -- esta configuracion la realiza
    //////////////// el WebMvcConfig

    // @Bean
    // public FlowHandlerAdapter flowHandlerAdapter() {
    // FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
    // flowHandlerAdapter.setFlowExecutor(flowExecutor());
    //
    //
    // return flowHandlerAdapter;
    // }
    //
    // @Bean
    // public FlowHandlerMapping flowHandlerMapping() {
    // FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
    // handlerMapping.setInterceptors(new Object[] { localeChangeInterceptor });
    // handlerMapping.setFlowRegistry(flowRegistry());
    // return handlerMapping;
    // }

    @Autowired
    private ConversionService conversionService;

}