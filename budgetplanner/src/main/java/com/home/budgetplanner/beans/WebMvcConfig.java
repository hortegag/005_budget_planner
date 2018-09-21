package com.home.budgetplanner.beans;

import org.springframework.context.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.spring5.webflow.view.AjaxThymeleafViewResolver;
import org.thymeleaf.spring5.webflow.view.FlowAjaxThymeleafView;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@EnableWebMvc
@EnableSpringDataWebSupport
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private WebFlowConfig         webFlowConfig;

    @Autowired
    private SpringTemplateEngine  springTemplateEngine;

    @Autowired
    LocaleChangeInterceptor       localeChangeInterceptor;

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { "/", "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");

        ///registry.addResourceHandler("/jqgrid/**").addResourceLocations("/jqgrid/");

        //registry.addResourceHandler("/jqgrid/**").addResourceLocations("/jqgrid/");
        
        registry.addResourceHandler("/jqgrid/**").addResourceLocations("classpath:/static/jqgrid/");

  
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);

    }

    ////// workswith jsp
    /*
     * 
     * @Bean public InternalResourceViewResolver viewResolver() {
     * InternalResourceViewResolver viewResolver = new
     * InternalResourceViewResolver();
     * viewResolver.setViewClass(JstlView.class);
     * viewResolver.setPrefix("/WEB-INF/views/");
     * viewResolver.setSuffix(".jsp"); return viewResolver; }
     */

    /*
     * @Bean
     * 
     * @Description("Thymeleaf template resolver serving HTML 5") public
     * ClassLoaderTemplateResolver templateResolver() {
     * ClassLoaderTemplateResolver templateResolver = new
     * ClassLoaderTemplateResolver(); templateResolver.setPrefix("templates/");
     * templateResolver.setCacheable(false);
     * templateResolver.setSuffix(".html");
     * templateResolver.setTemplateMode("HTML5");
     * templateResolver.setCharacterEncoding("UTF-8"); return templateResolver;
     * }
     * 
     * @Bean public AjaxThymeleafViewResolver ajaxThymeleafViewResolver() {
     * AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
     * viewResolver.setViewClass(FlowAjaxThymeleafView.class);
     * viewResolver.setTemplateEngine(springTemplateEngine); return
     * viewResolver; }
     */

    ///// remover temporalmente ------------quizas se debe habilitar para que
    ///// funcione

    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);

        // se agrega locale interceptor
        handlerMapping.setInterceptors(new Object[] { localeChangeInterceptor });

        handlerMapping.setFlowRegistry(this.webFlowConfig.flowRegistry());
        return handlerMapping;
    }

    @Bean
    public FlowHandlerAdapter flowHandlerAdapter() {
        FlowHandlerAdapter handlerAdapter = new FlowHandlerAdapter();
        handlerAdapter.setFlowExecutor(this.webFlowConfig.flowExecutor());
        handlerAdapter.setSaveOutputToFlashScopeOnRedirect(true);
        return handlerAdapter;
    }

    /*
     * @Bean
     * 
     * @Description("Thymeleaf AJAX view resolver for Spring WebFlow") public
     * AjaxThymeleafViewResolver thymeleafViewResolver() {
     * AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
     * viewResolver.setViewClass(FlowAjaxThymeleafView.class);
     * viewResolver.setTemplateEngine(this.templateEngine());
     * viewResolver.setCharacterEncoding("UTF-8"); return viewResolver; }
     * 
     * @Bean
     * 
     * @Description("Thymeleaf template resolver serving HTML 5") public
     * ClassLoaderTemplateResolver templateResolver() {
     * ClassLoaderTemplateResolver templateResolver = new
     * ClassLoaderTemplateResolver(); templateResolver.setPrefix("templates/");
     * templateResolver.setCacheable(false);
     * templateResolver.setSuffix(".html");
     * templateResolver.setTemplateMode("HTML5");
     * templateResolver.setCharacterEncoding("UTF-8"); return templateResolver;
     * }
     * 
     * @Bean
     * 
     * @Description("Thymeleaf template engine with Spring integration") public
     * SpringTemplateEngine templateEngine() { SpringTemplateEngine
     * templateEngine = new SpringTemplateEngine();
     * templateEngine.setTemplateResolver(this.templateResolver()); return
     * templateEngine; }
     * 
     */

    @Bean
    public ITemplateResolver templateResolver() {
        // SpringResourceTemplateResolver templateResolver = new
        // SpringResourceTemplateResolver();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        // templateResolver.setTemplateMode( "HTML5" );
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");

        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.addDialect(new LayoutDialect());

        return templateEngine;
    }

    ////////////// this works

    @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();

        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setOrder(1);
        viewResolver.setCharacterEncoding("UTF-8");

        return viewResolver;
    }

    @Bean
    // @Description("Thymeleaf AJAX view resolver for Spring WebFlow")
    public AjaxThymeleafViewResolver ajaxViewResolver() {
        AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
        viewResolver.setViewClass(FlowAjaxThymeleafView.class);
        viewResolver.setOrder(2);
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    /*
     * @Override public void
     * addArgumentResolvers(List<HandlerMethodArgumentResolver>
     * argumentResolvers) { PageableHandlerMethodArgumentResolver resolver = new
     * PageableHandlerMethodArgumentResolver(); resolver.setFallbackPageable(new
     * PageRequest(0, 5)); argumentResolvers.add(resolver);
     * super.addArgumentResolvers(argumentResolvers); }
     */

}