/**
 * 
 */
package com.thinkbiganalytics.security.auth.kerberos;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.kerberos.authentication.KerberosAuthenticationProvider;
import org.springframework.security.kerberos.authentication.sun.SunJaasKerberosClient;
import org.springframework.security.kerberos.web.authentication.SpnegoEntryPoint;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 *
 * @author Sean Felten
 */
@Configuration
@EnableWebSecurity
@Profile("auth-krb-login")
public class KerberosAuthWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(kerberosAuthenticationProvider());
    }

    @Bean
    public KerberosAuthenticationProvider kerberosAuthenticationProvider() throws IOException {
        KerberosAuthenticationProvider provider = new KerberosAuthenticationProvider();
        SunJaasKerberosClient client = new SunJaasKerberosClient();
        client.setDebug(true);
        provider.setKerberosClient(client);
        
        Properties users = new Properties();
        users.load(new StringReader("dladmin=thinkbig,admin"));
        
        provider.setUserDetailsService(new InMemoryUserDetailsManager(users));
        return provider;
    }

    @Bean
    @Primary
    public SpnegoEntryPoint spnegoEntryPoint() {
//        return new SpnegoEntryPoint();
        return new SpnegoEntryPoint("/login");
    }

}