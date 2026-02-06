package fr.nextoo.devfest2024_back.security;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("${cors.allowed-origin}")
    private String allowedOrigin;
    @Value("${cors.allowed-method}")
    private String allowedMethod;
    @Value("${cors.allowed-header}")
    private String allowedHeader;
    @Value("${cors.allow-credentials}")
    private boolean allowCredentials;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern(allowedOrigin);
        config.addAllowedHeader(allowedHeader);
        config.addAllowedMethod(allowedMethod);
        config.setAllowCredentials(allowCredentials);
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}