package lsea.config;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * Configuration class for locale settings.
 */
@Configuration
public class LocaleConfig {
    /**
     * The active profile: prod or dev
     */
    @Value("${spring.profiles.active}")
    private String profile;

    /**
     * Sets the default locale to US if the profile is dev. Otherwise, the default
     * 
     * @return the locale resolver
     */
    @Bean
    LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        System.out.println("Using profile: " + profile);
        if (profile != null && profile.equals("dev")) {
            slr.setDefaultLocale(Locale.ENGLISH);
            Locale.setDefault(Locale.ENGLISH);
        }
        return slr;
    }
}
