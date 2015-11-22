package com.hazelcast.examples.application.cache;

import com.hazelcast.examples.Employee;
import com.hazelcast.examples.application.dao.UserDao;
import com.hazelcast.examples.application.model.User;

import javax.cache.integration.CacheLoader;
import javax.cache.integration.CacheLoaderException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserCacheLoader
        implements CacheLoader<Integer, Employee>, Serializable {

    private final UserDao userDao;
    int count =0;

    public UserCacheLoader(UserDao userDao) {
        // Store the dao instance created externally
        this.userDao = userDao;
    }

    @Override
    public Employee load(Integer key) throws CacheLoaderException {
        // Just call through into the dao
        return userDao.findUserById(key);
    }

    @Override
    public Map<Integer, Employee> loadAll(Iterable<? extends Integer> keys)
            throws CacheLoaderException {
//System.out.println("IMM"+count++);
System.out.println("keys"+keys);
        // Create the resulting map
        Map<Integer, Employee> loaded = new ConcurrentHashMap<Integer, Employee>();
        // For every key in the given set of keys
        for (Integer key : keys) {
            // Try to retrieve the user
            Employee user = userDao.findUserById(key);
            // If user is not found do not add the key to the result set
            if (user != null) {
                loaded.put(key, user);
            }
        }
        return loaded;
    }
}
