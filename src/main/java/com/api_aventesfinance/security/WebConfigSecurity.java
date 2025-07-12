package com.api_aventesfinance.security;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.api_aventesfinance.service.ImplementacaoUserDetailsService;

import jakarta.servlet.http.HttpServletRequest;


@Configuration
@EnableWebSecurity
public class WebConfigSecurity {

    @Autowired
    private ImplementacaoUserDetailsService implementacaoUserDetailsService;

      private static final List<String> UPTIMEROBOT_IPS = Arrays.asList(
        "104.131.107.63", "122.248.234.23", "128.140.106.114", "128.140.41.193",
        "128.199.195.156", "135.181.154.9", "138.197.150.151", "139.59.173.249",
        "142.132.180.39", "146.185.143.14", "157.90.155.240", "157.90.156.63",
        "159.203.30.41", "159.69.158.189", "159.89.8.111", "165.227.83.148",
        "167.235.143.113", "167.99.209.234", "168.119.123.75", "168.119.53.160",
        "168.119.96.239", "178.62.52.237", "18.116.205.62", "18.180.208.214",
        "216.144.248.16/28", "216.245.221.80/28", "3.105.133.239", "3.105.190.221",
        "3.12.251.153", "3.20.63.178", "3.212.128.62", "34.198.201.66",
        "35.166.228.98", "35.84.118.171", "37.27.28.153", "37.27.29.68",
        "37.27.30.213", "37.27.34.49", "37.27.82.220", "37.27.87.149",
        "44.227.38.253", "46.101.250.135", "49.13.130.29", "49.13.134.145",
        "49.13.164.148", "49.13.167.123", "49.13.24.81", "5.161.61.238",
        "5.161.75.7", "5.78.118.142", "5.78.87.38", "52.15.147.27",
        "52.22.236.30", "54.167.223.174", "54.249.170.27", "54.64.67.106",
        "54.79.28.129", "65.109.129.165", "65.109.142.78", "65.109.8.202",
        "69.162.124.224/28", "78.46.190.63", "78.46.215.1", "78.47.173.76",
        "78.47.98.55", "88.99.80.227"
    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
      
    	return http  
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())  
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll() 
                    .requestMatchers(HttpMethod.POST, "/auth/register").permitAll() 
                    .requestMatchers(HttpMethod.POST, "/auth/logout").permitAll() 
                    .requestMatchers(HttpMethod.GET, "/status/").permitAll() 
            		.requestMatchers(HttpMethod.OPTIONS,"/**").permitAll()
            		.requestMatchers(HttpMethod.OPTIONS,"/index").permitAll()
            		// .requestMatchers(HttpMethod.OPTIONS,"/chat-socket/**").permitAll()
                    .requestMatchers(
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/swagger-ui.html"),
                        new AntPathRequestMatcher("/swagger-resources/**"),
                        new AntPathRequestMatcher("/webjars/**")
                    ).permitAll()
                    .anyRequest().authenticated() 
            )
            .addFilterBefore(new JwtApiAutenticacaoFilter(), UsernamePasswordAuthenticationFilter.class)
            // .addFilterBefore(new JWTLoginFilter("/login", authenticationManager), UsernamePasswordAuthenticationFilter.class)
            .build();  

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200", "https://0bcedae10c6a.ngrok-free.app")); // Libera o Angular
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true); 

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

     @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(new RequestMatcher() {
            // Cria uma lista de IpAddressMatcher para cada IP/CIDR fornecido
            private final List<IpAddressMatcher> ipMatchers = UPTIMEROBOT_IPS.stream()
                .map(IpAddressMatcher::new)
                .collect(Collectors.toList());

            @Override
            public boolean matches(HttpServletRequest request) {
                // Verifica se o IP remoto da requisição corresponde a algum dos IPs do UptimeRobot
                return ipMatchers.stream().anyMatch(matcher -> matcher.matches(request));
            }
        });
    }
}