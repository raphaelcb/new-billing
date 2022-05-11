package br.com.cpqd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * This class is responsible for initializing the application with Spring's context.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan
public class SpringBootBillingCompanyApplication extends SpringBootServletInitializer {

    /**
     * Method responsible for initializing the Spring application.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param args The arguments
     */
    public static void main(String[] args) {

        SpringApplication.run(SpringBootBillingCompanyApplication.class, args);
    }

}
