package com.hazelcast.examples;

import javax.cache.integration.CompletionListener;

import com.hazelcast.examples.application.MainImpl;

public class CompleteListerImpl implements CompletionListener{

	@Override
	public void onCompletion() {
		// TODO Auto-generated method stub
		System.out.println("completed");
	System.out.println("test"+	MainImpl.userCache.get(9999));
		
	}

	@Override
	public void onException(Exception e) {
		// TODO Auto-generated method stub
		
	}

}
