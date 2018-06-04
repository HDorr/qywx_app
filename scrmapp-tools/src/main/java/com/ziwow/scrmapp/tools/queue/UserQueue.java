package com.ziwow.scrmapp.tools.queue;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;


public class UserQueue<T> implements InitializingBean, DisposableBean {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserQueue.class);
	private static final ConcurrentLinkedQueue<Object> queue = new ConcurrentLinkedQueue<Object>();
	private Thread listenerThread;
	private QueueListener<T> listener;// 异步回调

	public QueueListener<T> getListener() {
		return listener;
	}

	public void setListener(QueueListener<T> listener) {
		this.listener = listener;
	}
	
	public static synchronized ConcurrentLinkedQueue<Object> getQueueInstance(){
        return queue;
    }

	@Override
	public void destroy() throws Exception {
		listenerThread.interrupt();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (listener != null) {
			listenerThread = new ListenerThread();
			listenerThread.start();
		}
	}

	class ListenerThread extends Thread {
		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			while (true) {
				Object obj = null;
				try {
					Thread.sleep(1);
					obj = UserQueue.queue.poll();
					// 逐个执行
					if (obj != null) {
						listener.onMessage((T) obj);
					}
				} catch (Exception e) {
					LOGGER.error("poll from user qunue exception", e);
					//UserQueue.queue.add(obj);
				}
			}
		}
	}
}