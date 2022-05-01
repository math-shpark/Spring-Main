package com.ssafy.board.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.ssafy.board.controller.LoginCheckInterceptor;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	@Value("${spring.mvc.view.prefix}")
	private String prefix;
	
	@Value("${spring.mvc.view.suffix}")
	private String suffix;
	
	@Bean
	public ViewResolver beanViewResolver() {
		BeanNameViewResolver bvr = new BeanNameViewResolver();
		bvr.setOrder(0);
		return bvr;
	}
	
	@Bean
	public ViewResolver internalViewResolver() {
		System.out.println(prefix + "----" + "suffix");
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setPrefix(prefix);
		irvr.setSuffix(suffix);
		irvr.setViewClass(JstlView.class);
		return irvr;
	}
	
	@Autowired
	LoginCheckInterceptor confirm;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(confirm)
				.addPathPatterns("/board/*");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}
}
