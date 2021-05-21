package com.example.practice;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;

//テスト用DBに接続するためのクラス
public class TestConfiguration {
    @Bean
    public DataSource dataSource() {
        return new TransactionAwareDataSourceProxy(
                DataSourceBuilder
                        .create()
                        .username("root")
                        .password("abcd1234")
                        .url("jdbc:mysql://localhost:3310/db_test?nullCatalogMeansCurrent=true")
                        .driverClassName("com.mysql.cj.jdbc.Driver")
                        .build());
    }
}
