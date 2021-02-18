package es.correos.soporte.minerva.envios.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
/**
 * Esta clase configura la seguridad por defecto de la aplicación. También es necesario configurar
 * aquí el filtro csrf, ya que de lo contrario solo deja pasar peticiones GET, HEAD, TRACE y
 * OPTIONS.
 */
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Override
  // con esta configuración csrf lo que se hace es que, al entrar por
  // un endpoint GET, se genera una cookie con el token XSRF-TOKEN, que habrá
  // que recoger y esa cookie tiene que ir, y además una cabecera en las siguientes
  // peticiones con el nombre X-XSRF-TOKEN y el valor de la cookie.
  // Se puede modificar esto de tal forma que no cualquier GET genere la cookie,
  // sino alguna en concreto y las demás urls, aunque GET, deban incluir la cabecera
  // Una especie de obtención de cabecera en un punto concreto. Para eso
  // habría que configurar en csrf().requireCsrfProtectionMatcher, donde se de una implementación
  // que solo deje sin comprar la url/metodo que se quiera.
  // Si se ve innecesario verificar este token, entonces csrf().disable()
  protected void configure(HttpSecurity http) throws Exception {
    http
        // se puede quitar el control csrf (csrf().disable()), aunque no es
        // recomendable. Si se incluye, hay que ver como lanzar las peticiones
        // para que incluyan el token siempre
        .csrf()
        .disable()
        //		.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
        .authorizeRequests()
        .antMatchers("/**")
        .permitAll()
        .and()
        .httpBasic()
        .disable();
  }
}
