package main.VeterinaryClinic.Config;

import main.VeterinaryClinic.Service.AccountUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig  {
    @EnableWebSecurity
    public class LineLoginSecurityConfig extends WebSecurityConfigurerAdapter {
        @Autowired
        private AccountUserDetailService accountUserDetailService;
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/js/**", "/accounts","/img/**").permitAll() // Don't need login can use (Home Page, CSS, JS)
//                    .antMatchers("/authTest").access("hasRole('ROLE_ADMIN')")
                    .anyRequest().authenticated() // Other path need login
            .and()
                    .oauth2Login()
                    .defaultSuccessUrl("/").permitAll()
                    .userInfoEndpoint()
                    .userService(accountUserDetailService) // convert to CustomOAuth2User Model
            .and()
//                    .successHandler(oAuth2LoginSuccessHandler) // handle info
            .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // Bypass confirm logout page
                    .logoutSuccessUrl("/").permitAll();
        }
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(this.lineClientRegistration());
    }

    // Line Login Config
    private ClientRegistration lineClientRegistration() {
        return ClientRegistration.withRegistrationId("line")
                .clientId("1657706124")
                .clientSecret("dcc6c8fc2b6ecdd6b9c45ec6359f78f0")
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
//                .redirectUriTemplate("https://f269-49-228-233-0.ap.ngrok.io" + "/login/oauth2/code/{registrationId}")
                .scope("profile")
                .authorizationUri("https://access.line.me/oauth2/v2.1/authorize")
                .tokenUri("https://api.line.me/oauth2/v2.1/token")
                .userNameAttributeName("userId")
                .userInfoUri("https://api.line.me/v2/profile")
                .clientName("LINE")
                .build();
    }
}
