package com.example.map;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qiang
 */
@RestController
@SpringBootApplication
@MapperScan("com.example.map.mapper")
public class MapApplication {
    public static void main(String[] args) {
        SpringApplication.run(MapApplication.class, args);
    }

}
