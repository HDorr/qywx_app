package com.ziwow.scrmapp.tools.queue;

public interface QueueListener<T> {
	public void onMessage(T value);
}