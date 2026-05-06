package com.tp3.portailetudiants.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.XmlViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.Locale;

/**
 * WebConfig Configuration Class
 * 
 * This class configures Spring MVC for the Student Event Portal project.
 * It demonstrates 4 key concepts:
 * 
 * Ex 1: JSP View Resolver (Internal Resource View Resolver)
 * Ex 2: Thymeleaf View Resolver
 * Ex 3: XML View Resolver
 * Ex 4: Internationalization (i18n) with MessageSource
 * 
 * View Resolution Priority (using setOrder()):
 * 1. XML View Resolver (Order: 1)
 * 2. Thymeleaf View Resolver (Order: 2) ← Belkis's focus
 * 3. JSP View Resolver (Order: 3)
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.tp3.portailetudiants")
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    // ==========================================
    // EXEMPLE 4 : Internationalization (i18n)
    // ==========================================
    
    /**
     * MessageSource Bean
     * 
     * Responsible for loading translation files:
     * - messages_fr.properties (French)
     * - messages_en.properties (English)
     * - messages_ar.properties (Arabic)
     * 
     * Files located in: src/main/resources/messages/
     * 
     * How it works:
     * 1. setBasename("classpath:messages/messages") → Load messages_XX.properties
     * 2. When locale = Locale.FRENCH → Loads messages_fr.properties
     * 3. When locale = Locale.ENGLISH → Loads messages_en.properties
     * 4. When locale = new Locale("ar") → Loads messages_ar.properties
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:messages/messages");
        source.setDefaultEncoding("UTF-8");
        source.setCacheSeconds(3600); // Cache for 1 hour
        return source;
    }

    /**
     * LocaleResolver Bean (SessionLocaleResolver)
     * 
     * Determines the current user's locale in this order:
     * 1. Check if locale is stored in session (from previous ?lang=XX click)
     * 2. If no session locale, use defaultLocale (French)
     * 
     * Why SessionLocaleResolver?
     * - Stores user's language preference in HTTP session
     * - Persists across page requests (doesn't reset on refresh)
     * - Secure (stored server-side, not in cookies)
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.FRENCH); // Default language: French
        return slr;
    }

    /**
     * LocaleChangeInterceptor Bean
     * 
     * Intercepts HTTP requests to detect language change parameter (?lang=XX)
     * 
     * How it works:
     * 1. Student clicks: <a href="?lang=en">🇬🇧</a>
     * 2. URL becomes: /hello?lang=en
     * 3. LocaleChangeInterceptor intercepts the request
     * 4. Extracts ?lang=en parameter
     * 5. Creates new Locale("en")
     * 6. SessionLocaleResolver stores it in session
     * 7. All subsequent requests use this locale
     * 
     * Parameter name: "lang" (the "L" in ?lang=en)
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Listen for ?lang=XX parameter
        return lci;
    }

    /**
     * Register the LocaleChangeInterceptor
     * 
     * Adds the interceptor to the request pipeline so it runs BEFORE the controller.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    // ==========================================
    // EXEMPLE 3 : XML View Resolver
    // ==========================================
    
    /**
     * XmlViewResolver Bean
     * 
     * Priority: 1 (highest)
     * Location: src/main/resources/views.xml
     * 
     * Purpose: Map view names to actual view files via XML configuration
     * Example in views.xml:
     *   <bean name="vueXmlTest" class="org.springframework.web.servlet.view.JstlView">
     *       <property name="url" value="/WEB-INF/jsp/test-xml.jsp"/>
     *   </bean>
     * 
     * If controller returns: return "vueXmlTest";
     * → Spring looks in views.xml first
     * → Finds the mapping → Returns /WEB-INF/jsp/test-xml.jsp
     */
    @Bean
    public ViewResolver xmlViewResolver() {
        XmlViewResolver resolver = new XmlViewResolver();
        resolver.setLocation(new ClassPathResource("views.xml"));
        resolver.setOrder(1); // Check this resolver first
        return resolver;
    }

    // ==========================================
    // EXEMPLE 2 : Thymeleaf View Resolver
    // ==========================================
    
    /**
     * SpringResourceTemplateResolver Bean
     * 
     * Tells Thymeleaf WHERE to find HTML template files:
     * - Prefix: /WEB-INF/templates/
     * - Suffix: .html
     * 
     * Example:
     * If controller returns: return "welcome";
     * → Template resolver looks for: /WEB-INF/templates/welcome.html
     * 
     * Important properties:
     * - setTemplateMode("HTML") → Process as HTML (not XML, not RAW)
     * - setCharacterEncoding("UTF-8") → Support special characters (accents, Arabic, etc.)
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(applicationContext);
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        resolver.setTemplateMode("HTML");
        resolver.setCharacterEncoding("UTF-8");
        resolver.setCacheable(true);
        return resolver;
    }

    /**
     * SpringTemplateEngine Bean
     * 
     * The "brain" of Thymeleaf. Processes HTML templates with:
     * - Template resolver (WHERE to find files)
     * - MessageSource (FOR i18n translations)
     * - Other Thymeleaf configuration
     * 
     * Critical: setTemplateEngineMessageSource(messageSource())
     * This connects the i18n translations to Thymeleaf processing!
     * 
     * Without this line:
     * - #{key} syntax in templates won't work
     * - All translations will show as ?key?
     * 
     * How it works:
     * 1. Thymeleaf encounters: <h1 th:text="#{welcome.title}">Default</h1>
     * 2. Extracts key: "welcome.title"
     * 3. Calls MessageSource.getMessage("welcome.title", currentLocale)
     * 4. Retrieves translated value from messages_XX.properties
     * 5. Renders: <h1>Bienvenue au Portail Étudiant</h1> (if French)
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.setTemplateEngineMessageSource(messageSource()); // ✅ CRITICAL for i18n!
        engine.setEnableSpringELCompiler(true); // Enable Spring Expression Language
        return engine;
    }

    /**
     * ThymeleafViewResolver Bean
     * 
     * Priority: 2 (second)
     * Template location: /WEB-INF/templates/
     * File suffix: .html
     * 
     * Flow:
     * 1. Controller returns: return "welcome";
     * 2. Spring asks ThymeleafViewResolver: "Do you have a view named 'welcome'?"
     * 3. Resolver asks TemplateResolver: "Is there /WEB-INF/templates/welcome.html?"
     * 4. If YES → Loads welcome.html
     * 5. Thymeleaf processes it with i18n support
     * 6. HTML sent to browser
     * 
     * Character encoding UTF-8:
     * - Supports French accents (é, è, ê, ë, etc.)
     * - Supports Arabic characters
     * - Supports all Unicode characters
     */
    @Bean
    public ViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine());
        resolver.setCharacterEncoding("UTF-8");
        resolver.setOrder(2); // Check this resolver second
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    // ==========================================
    // EXEMPLE 1 : JSP View Resolver
    // ==========================================
    
    /**
     * InternalResourceViewResolver Bean (JSP)
     * 
     * Priority: 3 (last)
     * Template location: /WEB-INF/jsp/
     * File suffix: .jsp
     * 
     * Purpose: Handle JSP template files (classic Java web approach)
     * 
     * Example:
     * If controller returns: return "welcome";
     * And Thymeleaf didn't find it...
     * → JSP Resolver looks for: /WEB-INF/jsp/welcome.jsp
     * 
     * Why last priority?
     * - JSP is older technology
     * - Thymeleaf is more modern (our focus)
     * - XML mapping is most specific (our configuration)
     * - So JSP is last resort
     */
    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(3); // Check this resolver last
        return resolver;
    }

}
