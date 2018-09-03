package com.home.budgetplanner.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

import nz.net.ultraq.thymeleaf.LayoutDialect;

@EnableWebMvc
@Configuration
public class WebMvcConfig  implements WebMvcConfigurer {
 
    @Autowired
    private WebFlowConfig webFlowConfig;
    
    
    
    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("/webjars/");
        
        registry.addResourceHandler("/**").addResourceLocations("/");

        
        
    }
    
    //////workswith jsp
    /*

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
    */
    
    /*
    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix("templates/");
            templateResolver.setCacheable(false);
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML5");
            templateResolver.setCharacterEncoding("UTF-8");
            return templateResolver;
    }
    @Bean
    public AjaxThymeleafViewResolver ajaxThymeleafViewResolver() {
        AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
        viewResolver.setViewClass(FlowAjaxThymeleafView.class);
        viewResolver.setTemplateEngine(springTemplateEngine);
        return viewResolver;
    }
*/
    @Bean
    public FlowHandlerMapping flowHandlerMapping() {
        FlowHandlerMapping handlerMapping = new FlowHandlerMapping();
        handlerMapping.setOrder(-1);
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
    @Bean
    @Description("Thymeleaf AJAX view resolver for Spring WebFlow")
    public AjaxThymeleafViewResolver thymeleafViewResolver() {
            AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
            viewResolver.setViewClass(FlowAjaxThymeleafView.class);
            viewResolver.setTemplateEngine(this.templateEngine());
            viewResolver.setCharacterEncoding("UTF-8");
            return viewResolver;
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    public ClassLoaderTemplateResolver templateResolver() {
            ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
            templateResolver.setPrefix("templates/");
            templateResolver.setCacheable(false);
            templateResolver.setSuffix(".html");
            templateResolver.setTemplateMode("HTML5");
            templateResolver.setCharacterEncoding("UTF-8");
            return templateResolver;
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    public SpringTemplateEngine templateEngine() {
            SpringTemplateEngine templateEngine = new SpringTemplateEngine();
            templateEngine.setTemplateResolver(this.templateResolver());
            return templateEngine;
    }
    
    */
    
    
    
    @Bean
    public ITemplateResolver templateResolver() {
        //SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix( "templates/" );
        templateResolver.setSuffix( ".html" );
        templateResolver.setTemplateMode( "HTML5" );
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("UTF-8");

 
        return templateResolver;
    }
 
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver( templateResolver() );
        templateEngine.addDialect(new LayoutDialect());
        return templateEngine;
    }
 
    ////////////// this works

    
   @Bean
    public ViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
                
        viewResolver.setTemplateEngine( templateEngine() );
        viewResolver.setOrder( 1 );
 
        return viewResolver;
    }
    
    
    
    
    
    /*
    @Bean
    @Description("Thymeleaf AJAX view resolver for Spring WebFlow")
    public AjaxThymeleafViewResolver viewResolver() {
            AjaxThymeleafViewResolver viewResolver = new AjaxThymeleafViewResolver();
            viewResolver.setViewClass(FlowAjaxThymeleafView.class);
            viewResolver.setTemplateEngine(this.templateEngine());
            viewResolver.setCharacterEncoding("UTF-8");
            return viewResolver;
    }
    */
    
   @Bean
   public LayoutDialect layoutDialect() {
       return new LayoutDialect();
   }
    
    
    
    
    
}