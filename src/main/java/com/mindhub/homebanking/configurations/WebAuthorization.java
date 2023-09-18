package com.mindhub.homebanking.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@EnableWebSecurity // Habilita la seguridad web
@Configuration // Indica que esta clase es una configuración
class WebAuthorization extends WebSecurityConfigurerAdapter {
    // Configura la autorización de solicitudes HTTP
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Si quiero acceder con ("/web/index.html", "SIN ROL") puedo hacerlo, pero
        // si cambio el orden y pongo esta regla primero '.antMatchers("/**").hasAuthority("CLIENT")'
        // me va a rechazar la autorización, por que analiza primero esa linea.
        /*
        http.authorizeRequests()
                .antMatchers("/web/index.html","/web/css/**","/web/img/**","/web/js/**","/api/loans").permitAll()//
                .antMatchers(HttpMethod.POST, "/api/login","/api/logout","/api/clients").permitAll()
                .antMatchers(HttpMethod.POST,"/api/clients/current/accounts","/api/clients/current/cards"
                        ,"/api/transactions","/api/loans").hasAnyAuthority("CLIENT","ADMIN")
                .antMatchers("/web/**.html").hasAnyAuthority("CLIENT","ADMIN")

                .antMatchers("/api/**").hasAnyAuthority("CLIENT","ADMIN")
                //.antMatchers("/api/clients","/api/accounts","/api/cards","api/transactions"
                //       ,"api/clients/{id}","/api/accounts/{id}","/api/transaction/{id}",
                //       "/api/clients/current","/api/clients/current/accounts","/api/clients/current/cards").hasAnyAuthority("CLIENT","ADMIN")


                .anyRequest().denyAll();
                */

        http.authorizeRequests()
                .antMatchers("/web/index.html", "/web/css/**", "/web/img/**", "/web/js/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/clients/current/cards").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients/current").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/transfers").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/clients/current/accounts").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/Transfers").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/accounts/{id}").hasAuthority("CLIENT")///accounts/{id}
                .antMatchers(HttpMethod.POST, "/api/transactions/**").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/transaction/{id}").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/clients/{id}/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.GET, "/api/loans").hasAuthority("CLIENT")
                .antMatchers(HttpMethod.POST, "/api/loans").hasAuthority("CLIENT")
                .antMatchers("/web/accounts.html").hasAuthority("CLIENT") //
                .antMatchers(HttpMethod.PATCH,"/api/clients/current/cards/delete").hasAuthority("CLIENT") //
                .antMatchers("/web/account.html").permitAll() //
                .antMatchers("/web/transfers.html").hasAuthority("CLIENT") ///web/transfers.html
                .antMatchers("/web/cards.html").hasAuthority("CLIENT") ///web/cards.html
                .antMatchers("/web/create-cards.html").hasAuthority("CLIENT")
                .antMatchers("/web/loan-application.html").hasAuthority("CLIENT")
                .antMatchers("/h2-console").hasAuthority("ADMIN")
                .antMatchers("/h2-console").hasAuthority("CLIENT")
                .antMatchers("/rest/**").hasAuthority("ADMIN")
                .antMatchers("/h2-console/**").hasAnyAuthority("ADMIN", "CLIENT")
               .antMatchers("/**").hasAnyAuthority("ADMIN")
               // .antMatchers("/**").hasAuthority("ADMIN");
                .anyRequest().denyAll();


        // Define un recurso POST para hacer el login
        http.formLogin()
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/api/login");

        // Define el recurso para cerrar sesión
        http.logout().logoutUrl("/api/logout");

        // Deshabilita la verificación de tokens CSRF
        http.csrf().disable();

        // Deshabilita las opciones de marco para que se pueda acceder a h2-console
        http.headers().frameOptions().disable();

        // Si el usuario no está autenticado, simplemente envía una respuesta de fallo de autenticación
        http.exceptionHandling().authenticationEntryPoint((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el inicio de sesión es exitoso, simplemente borra las banderas que piden autenticación
        http.formLogin().successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        // Si el inicio de sesión falla, simplemente envía una respuesta de fallo de autenticación
        http.formLogin().failureHandler((req, res, exc) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED));

        // Si el cierre de sesión es exitoso, simplemente envía una respuesta exitosa
        http.logout().logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    // Borra los atributos de autenticación de la sesión actual
    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
    }
}


