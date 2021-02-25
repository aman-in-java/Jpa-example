package org.sample;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@ComponentScan
@Configuration
@EnableJpaRepositories(basePackages = "org.sample.data")
@EnableTransactionManagement
@PropertySource("classpath:properties/db.properties")
public class ApplicationConfiguration
{
  @Bean(destroyMethod = "close")
  public DataSource dataSource(final Environment env)
  {
    final HikariConfig config = new HikariConfig();
    config.setDriverClassName(env.getRequiredProperty("db.h2.driver.class"));
    config.setDriverClassName(env.getRequiredProperty("db.h2.driver.class"));
    config.setJdbcUrl(env.getRequiredProperty("db.h2.url"));
    config.setPassword(env.getRequiredProperty("db.h2.password"));
    config.setUsername(env.getRequiredProperty("db.h2.username"));

    return new HikariDataSource(config);
  }

  @Bean
  LocalContainerEntityManagerFactoryBean entityManagerFactory(final DataSource dataSource, final Environment env)
  {
    final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
    entityManagerFactoryBean.setDataSource(dataSource);
    entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    entityManagerFactoryBean.setPackagesToScan("org.sample.domain");

    final Properties jpaProperties = new Properties();

    //Configures the used database dialect. This allows Hibernate to create SQL
    //that is optimized for the used database.
    jpaProperties.put("hibernate.dialect", env.getRequiredProperty("db.h2.dialect"));
    jpaProperties.put("hibernate.enable_lazy_load_no_trans", true);
    //Specifies the action that is invoked to the database when the Hibernate
    //SessionFactory is created or closed.
    jpaProperties.put("hibernate.hbm2ddl.auto",
                      env.getRequiredProperty("db.hibernate.hbm2ddl.auto"));
    jpaProperties.put("hibernate.show_sql",
                      env.getRequiredProperty("db.hibernate.sql.show"));

    entityManagerFactoryBean.setJpaProperties(jpaProperties);

    return entityManagerFactoryBean;
  }

  @Bean
  public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory, final TransactionInterceptor transactionInterceptor)
  {
    final PlatformTransactionManager transactionManager = new JpaTransactionManager(entityManagerFactory);
    transactionInterceptor.setTransactionManager(transactionManager);

    return transactionManager;
  }
}
