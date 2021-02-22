package com.cgg.zkstudy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.cgg.zkstudy.dao")
@Configuration
public class JpaConfig {
}
