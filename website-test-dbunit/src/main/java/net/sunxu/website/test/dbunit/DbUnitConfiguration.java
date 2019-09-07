package net.sunxu.website.test.dbunit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbUnitConfiguration {

    @Bean
    public DbUnitRuleFactory dbUnitRuleFactory() {
        return new DbUnitRuleFactory();
    }
}
