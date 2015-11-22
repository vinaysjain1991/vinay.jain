package com.hazelcast.examples.application;

import com.hazelcast.config.CacheConfig;
import com.hazelcast.config.CacheSimpleConfig;
import com.hazelcast.config.Config;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.examples.CompleteListerImpl;
import com.hazelcast.examples.Employee;
import com.hazelcast.examples.HibernateUtil;
import com.hazelcast.examples.application.cache.UserCacheLoader;
import com.hazelcast.examples.application.commands.CacheEntryProcessorCommand;

import com.hazelcast.examples.application.commands.CacheListCommand;
import com.hazelcast.examples.application.commands.CacheRemoveCommand;
import com.hazelcast.examples.application.commands.Command;

import com.hazelcast.examples.application.commands.DaoGetCommand;
import com.hazelcast.examples.application.commands.DaoListCommand;

import com.hazelcast.examples.application.dao.UserDaoImpl;
import com.hazelcast.examples.application.model.User;

import com.hazelcast.examples.application.commands.DaoRemoveCommand;
import com.hazelcast.examples.application.dao.UserDao;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.CompleteConfiguration;
import javax.cache.configuration.FactoryBuilder;
import javax.cache.configuration.MutableCacheEntryListenerConfiguration;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;
import javax.cache.integration.CompletionListener;
import javax.cache.spi.CachingProvider;

import org.hibernate.Session;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Main class to execute the Useraccount cache example
 */
public class MainImpl {
	public static Cache<Integer, Employee> userCache = null;
	public static void main(String[] args) throws Exception {


		// Configure to use server mode since client is available on classpath
		System.setProperty("hazelcast.jcache.provider.type", "server");

		// Create the fake database access
		UserDao userDao = new UserDaoImpl();

		// Create the JCache cache instance
		 userCache = configureCache(userDao);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		HashSet hashSet = new HashSet();
		for (int i = 100; i < 1000; i++) {
			hashSet.add(i);
		}
		Employee employee = (Employee) session.load(Employee.class, 100);
		System.out.println(employee);
		CompleteListerImpl completionListener = new CompleteListerImpl();
		userCache.loadAll(hashSet, true, completionListener);
		// Configure command mapping
		/*  Map<String, Command> commands = new LinkedHashMap<String, Command>();
        // General command mappings
        commands.put("help", new HelpCommand());
        commands.put("exit", new ExitCommand());
        // Cache command mappings
        commands.put("cachelist", new CacheListCommand());
        commands.put("cacheadd", new CacheAddCommand());
        commands.put("cacheget", new CacheGetCommand());
        commands.put("cacheremove", new CacheRemoveCommand());
        commands.put("cacheclear", new CacheClearCommand());
        commands.put("cacheupdateep", new CacheEntryProcessorCommand());
        // Dao command mappings
        commands.put("daolist", new DaoListCommand());
        commands.put("daoadd", new DaoAddCommand());
        commands.put("daoget", new DaoGetCommand());
        commands.put("daoremove", new DaoRemoveCommand());

        // Context creation
        Context context = new Context(System.in, System.out, userDao, userCache, commands);
        HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 1; i < 3; i++) {
			set.add(i);
		}
        userCache.loadAll(set, true, completionListener);
        System.out.println("size"+userCache.get(23));
        context.newLine();
        context.writeln("Allowed commands: " + commands.keySet().toString());
        context.write("cmd> ");
        for (;;) {
            // Read next command from commandline
            String command = context.readLine();
            if ("".equals(command) || command == null) {
                // Ignore and show new cmd line
            } else {
                // Try to get command from mapping
                Command cmd = commands.get(command);
                if (cmd != null) {
                    try {
                        cmd.execute(context);
                    } catch (Exception e) {
                        context.writeln("Problem: " + e);
                    }
                } else {
                    context.writeln("Allowed commands: " + commands.keySet().toString());
                }
            }
            if (command != null) {
                context.write("cmd> ");
            }
        }*/
	}

	/**
	 * Creates a new {@link javax.cache.Cache} instance backed by the given
	 * {@link com.hazelcast.examples.application.dao.UserDao} configured with
	 * read-through and write-through
	 *
	 * @param userDao the {@link com.hazelcast.examples.application.dao.UserDao} backing store for the cache
	 * @return the created {@link javax.cache.Cache} instance
	 */
	private static Cache<Integer, Employee> configureCache(UserDao userDao) {
		// Explicitly retrieve the Hazelcast backed javax.cache.spi.CachingProvider
		CachingProvider cachingProvider = Caching.getCachingProvider(
				"com.hazelcast.cache.HazelcastCachingProvider"
				);

		// Retrieve the javax.cache.CacheManager
		CacheManager cacheManager = cachingProvider.getCacheManager();

		// Create javax.cache.configuration.CompleteConfiguration subclass
		CompleteConfiguration<Integer, Employee> config =
				new MutableConfiguration<Integer, Employee>()
				// Configure the cache to be typesafe
				.setTypes(Integer.class, Employee.class)
				// Configure to expire entries 30 secs after creation in the cache
				/*   .setExpiryPolicyFactory(FactoryBuilder.factoryOf(
                                new AccessedExpiryPolicy(new Duration(TimeUnit.SECONDS, 30))
                        ))*/
				// Configure read-through of the underlying store
				.setReadThrough(true)
				// Configure write-through to the underlying store
				//  .setWriteThrough(true)
				// Configure the javax.cache.integration.CacheLoader
				.setCacheLoaderFactory(FactoryBuilder.factoryOf(    new UserCacheLoader(userDao) ));
		// Configure the javax.cache.integration.CacheWriter
		/*  .setCacheWriterFactory(FactoryBuilder.factoryOf(
                                new UserCacheWriter(userDao)
                        ))
                        // Configure the javax.cache.event.CacheEntryListener with no
                        // javax.cache.event.CacheEntryEventFilter, to include old value
                        // and to be executed synchronously
                    /*    .addCacheEntryListenerConfiguration(
                                new MutableCacheEntryListenerConfiguration<Integer, User>(
                                        new UserCacheEntryListenerFactory(),
                                        null, true, true
                                )
                        )*/


		// Create the cache called "users" and using the previous configuration
		return cacheManager.createCache("users", config);
	}
}
