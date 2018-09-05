package it.olegna.test.basic.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


@Configuration
@EnableTransactionManagement
public class DBConfig {
	@Bean(name="hikariDs", destroyMethod = "close")
	public DataSource hikariDataSource(){
		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setDriverClassName("org.h2.Driver");
		hikariConfig.setJdbcUrl("jdbc:h2:./db/springbasic");
		hikariConfig.setUsername("sa");
		hikariConfig.setPassword("");
		hikariConfig.setMaximumPoolSize(2);
		hikariConfig.setConnectionTestQuery("SELECT 1");
		hikariConfig.setPoolName("springHikariCP");
		hikariConfig.setIdleTimeout(6000);
		hikariConfig.addDataSourceProperty("dataSource.cachePrepStmts", "true");
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSize", "250");
		hikariConfig.addDataSourceProperty("dataSource.prepStmtCacheSqlLimit", "2048");
		hikariConfig.addDataSourceProperty("dataSource.useServerPrepStmts", "true");
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		return dataSource;
	}
	//Start H2 Web console on port 8082
	@Bean(initMethod="start",destroyMethod="stop")
	public org.h2.tools.Server h2WebConsonleServer () throws SQLException {
		return org.h2.tools.Server.createWebServer("-web","-webAllowOthers","-webDaemon","-webPort", "8082");
	}	
	private Properties hibProps()
	{
		Properties props = new Properties();
		
		props.put(org.hibernate.cfg.Environment.DIALECT, "org.hibernate.dialect.H2Dialect");
		props.put(org.hibernate.cfg.Environment.SHOW_SQL, "true");
		props.put(org.hibernate.cfg.Environment.GENERATE_STATISTICS, "true");
		props.put(org.hibernate.cfg.Environment.FORMAT_SQL, "true");
		props.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, "create");
		props.put(org.hibernate.cfg.Environment.USE_SECOND_LEVEL_CACHE, "true");
		props.put(org.hibernate.cfg.Environment.USE_QUERY_CACHE, "true");
		props.put(org.hibernate.cfg.Environment.CACHE_REGION_FACTORY, CacheRegionFactory.class.getName());
		props.put(org.hibernate.cfg.Environment.STATEMENT_BATCH_SIZE, 25);
		props.put("hibernate.javax.cache.provider", "org.ehcache.jsr107.EhcacheCachingProvider");
		return props;
	}
	@Bean
	public LocalSessionFactoryBean sessionFactory()
	{
		LocalSessionFactoryBean lsfb = new LocalSessionFactoryBean();
		lsfb.setPackagesToScan(new String[]{"it.olegna.test.basic.models"});
		lsfb.setDataSource(this.hikariDataSource());
		lsfb.setHibernateProperties(hibProps());
		return lsfb;
	}
	@Bean("hibTx")
	public PlatformTransactionManager hibTxMgr(SessionFactory s)
	{
		HibernateTransactionManager result = new HibernateTransactionManager();
		result.setSessionFactory(s);
		return result;
	}	
}