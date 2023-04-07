package com.muchenlou.phototovideo.config;

import com.muchenlou.phototovideo.filter.CrossDomainFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author Baojian Hong
 * @Date 2023/4/7 13:19
 * @Version 1.0
 */
@Configuration
public class FilterConfig{
    @Bean
    public FilterRegistrationBean<CrossDomainFilter> corsFilter() {
        FilterRegistrationBean<CrossDomainFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new CrossDomainFilter());
        bean.addUrlPatterns("/*");
        return bean;
    }
}
