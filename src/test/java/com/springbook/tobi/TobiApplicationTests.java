package com.springbook.tobi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(locations = "/applicationContext.xml")
class TobiApplicationTests {
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DataSource PdataSource;

    @Test
    void contextLoads() {
        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        DataSource dataSource2 = (DataSource) applicationContext.getBean("dataSource");
        assertEquals(dataSource, dataSource2);
    }

    @Test
    void equalsLoads() {
        DataSource dataSource = (DataSource) applicationContext.getBean("dataSource");
        assertEquals(dataSource, PdataSource);
    }

}
