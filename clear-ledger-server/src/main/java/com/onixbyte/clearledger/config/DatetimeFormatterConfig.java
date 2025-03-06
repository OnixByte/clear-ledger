package com.onixbyte.clearledger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

@Configuration
public class DatetimeFormatterConfig {

    @Bean
    public DateTimeFormatter dateFormatter() {
        return DateTimeFormatter.ofPattern("yyMMdd");
    }

    @Bean
    public DateTimeFormatter datetimeFormatter() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

}
