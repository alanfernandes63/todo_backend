package com.alanfernandes.todos;

import java.util.EnumSet;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;

import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan({"com.alanfernandes.todos"})
@SpringBootApplication
public class TodosApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodosApplication.class, args);
	}
	@SuppressWarnings({"rawtypes", "unchecked"})
	@Bean
    public ServletRegistrationBean servletRegistrationBean() {

        FacesServlet servlet = new FacesServlet();

        return new ServletRegistrationBean(servlet, "*.jsf");

    }
	@SuppressWarnings({"rawtypes", "unchecked"})
    @Bean
    public FilterRegistrationBean rewriteFilter() {

        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());

        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,

                DispatcherType.ASYNC, DispatcherType.ERROR));

        rwFilter.addUrlPatterns("/*");

        return rwFilter;

    }

}
