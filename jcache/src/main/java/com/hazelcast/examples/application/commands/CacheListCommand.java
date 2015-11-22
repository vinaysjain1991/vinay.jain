package com.hazelcast.examples.application.commands;

import com.hazelcast.examples.application.Context;
import com.hazelcast.examples.application.model.User;

import java.util.Collection;
import java.util.Map.Entry;

import javax.cache.Cache;

public class CacheListCommand implements Command {

    @Override
    public void execute(Context context) throws Exception {
        Cache<Integer, User> userIds = context.getUserCache();
        System.out.println("<<<<<<<<<<<<");
      for (javax.cache.Cache.Entry<Integer, User> entry : userIds) {
		
		System.out.println(entry.getKey());
	} 
    }

    @Override
    public String description() {
        return "Lists all accounts stored in the cache";
    }
}
