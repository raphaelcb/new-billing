package br.com.cpqd.billing.comptech.locale.core;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * This class is responsible for configuring the {@link Locale} for this application.
 * 
 * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
 * @since 1.0
 * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer
 */
@Configuration
public class LocaleConfiguration implements WebMvcConfigurer {

    /**
     * Method responsible for defining a {@link LocaleResolver} which determine the current locale that is
     * being used.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link LocaleResolver} object
     */
    @Bean
    public LocaleResolver localeResolver() {

        var slr = new SessionLocaleResolver();
        slr.setDefaultLocale(new Locale("pt", "BR"));
        return slr;
    }

    /**
     * Method responsible for defining an intercepter bean that will switch to a new locale based on the value
     * of the lang parameter appended to a request.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link LocaleChangeInterceptor} object
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {

        var lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * Method responsible for defining a bean to define the classpath to messages files on each comptech.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @return The {@link MessageSource} object
     */
    @Bean
    public MessageSource messageSource() {

        var messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:/messages/audit/messages",
                "classpath:/messages/cache/messages", 
                "classpath:/messages/commons/messages",
                "classpath:/messages/security/messages");
        return messageSource;
    }

    /**
     * Method responsible for configuring the internationalization for messages when the javax.validation is
     * called.
     * 
     * @author Barbosa, Raphael de Carvalho - raphaelb@cpqd.com.br
     * @since 1.0
     * @param messageSource The {@link MessageSource} object
     * @return The {@link LocalValidatorFactoryBean} object
     */
    @Bean
    public LocalValidatorFactoryBean localValidatorFactoryBean(MessageSource messageSource) {

        var bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource);
        return bean;
    }

}
