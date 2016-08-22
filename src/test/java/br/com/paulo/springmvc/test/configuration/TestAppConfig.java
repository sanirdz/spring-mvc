package br.com.paulo.springmvc.test.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
 
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"br.com.paulo.springmvc"}, 
				excludeFilters = {@ComponentScan.Filter(pattern = "br\\.com\\.paulo\\.springmvc\\.configuration.*", type = FilterType.REGEX)})
public class TestAppConfig {
     

}