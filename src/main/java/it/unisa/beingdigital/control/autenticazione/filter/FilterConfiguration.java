package it.unisa.beingdigital.control.autenticazione.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe di configurazione per i bean filtri.
 */

@Configuration
public class FilterConfiguration {

  @Autowired
  private AuthFilter authFilter;

  @Autowired
  private UtenteFilter utenteFilter;

  @Autowired
  private AdminFilter adminFilter;

  /**
   * Crea e configura il bean per AuthFilter.
   */
  @Bean
  public FilterRegistrationBean<AuthFilter> filterRegistrationBeanPersonaFilter() {
    FilterRegistrationBean<AuthFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(authFilter);
    registrationBean.addUrlPatterns("/auth/*");

    return registrationBean;
  }

  /**
   * Crea e configura il bean per UtenteFilter.
   */
  @Bean
  public FilterRegistrationBean<UtenteFilter> filterRegistrationBeanUtenteFilter() {
    FilterRegistrationBean<UtenteFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(utenteFilter);
    registrationBean.addUrlPatterns("/utente/*");

    return registrationBean;
  }

  /**
   * Crea e configura il bean per AdminFilter.
   */
  @Bean
  public FilterRegistrationBean<AdminFilter> filterRegistrationBeanAdminFilter() {
    FilterRegistrationBean<AdminFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(adminFilter);
    registrationBean.addUrlPatterns("/admin/*");

    return registrationBean;
  }
}
