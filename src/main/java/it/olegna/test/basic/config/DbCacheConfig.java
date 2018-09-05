package it.olegna.test.basic.config;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.persistence.Entity;

import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.config.DefaultConfiguration;
import org.ehcache.expiry.ExpiryPolicy;
import org.ehcache.jsr107.EhcacheCachingProvider;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.jcache.JCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

@Configuration
@EnableCaching
public class DbCacheConfig extends CachingConfigurerSupport {
	@Bean("cacheManager")
	@Override
	public org.springframework.cache.CacheManager cacheManager()
	{

		return new JCacheCacheManager(createCacheManager());
	}
	private CacheManager createCacheManager()
	{
		long dimensioneCache = 1000;
		long ttlMillisecondi = 1000;
		ExpiryPolicy<Object, Object> ec = ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMillis(ttlMillisecondi));
		org.ehcache.config.CacheConfiguration<Object, Object> cacheConfiguration = CacheConfigurationBuilder.
																								newCacheConfigurationBuilder(Object.class, Object.class, 
																								ResourcePoolsBuilder.heap(dimensioneCache)
																								).withExpiry(ec).build();
		Map<String, org.ehcache.config.CacheConfiguration<?, ?>> caches = createCacheConfigurations(cacheConfiguration);
		ResourcePoolsBuilder rpb = ResourcePoolsBuilder.heap(dimensioneCache*1000000);
		ExpiryPolicy<Object, Object> infinite = ExpiryPolicyBuilder.noExpiration();
		org.ehcache.config.CacheConfiguration<Object, Object> eternalCacheConfiguration = CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class, rpb).withExpiry(infinite).build();
		caches.put("default-update-timestamps-region", eternalCacheConfiguration);
		EhcacheCachingProvider provider = getCachingProvider();
		DefaultConfiguration configuration = new DefaultConfiguration(caches, provider.getDefaultClassLoader());
		CacheManager result = provider.getCacheManager(provider.getDefaultURI(), configuration);
		return result;
	}

	private Map<String, org.ehcache.config.CacheConfiguration<?, ?>> createCacheConfigurations(org.ehcache.config.CacheConfiguration<Object, Object> cacheConfiguration)
	{
		Map<String, org.ehcache.config.CacheConfiguration<?, ?>> caches = new HashMap<>();
		ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
		scanner.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
		for (BeanDefinition bd : scanner.findCandidateComponents("it.olegna.test.basic.models"))
		{
			String className = bd.getBeanClassName();
			caches.put(className, cacheConfiguration);
		}
		caches.put("default-query-results-region", cacheConfiguration);
		return caches;
	}
	
	private EhcacheCachingProvider getCachingProvider()
	{
		return (EhcacheCachingProvider) Caching.getCachingProvider();
	}
}
