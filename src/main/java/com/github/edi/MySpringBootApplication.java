package com.github.edi;

import org.apache.camel.CamelContext;
import org.smooks.Smooks;
import org.smooks.cartridges.camel.processor.SmooksProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.xml.sax.SAXException;

import java.io.IOException;

@SpringBootApplication
public class MySpringBootApplication {

    /**
     * A main method to start this application.
     */
    public static void main(String[] args) {
        SpringApplication.run(MySpringBootApplication.class, args);
    }

}
