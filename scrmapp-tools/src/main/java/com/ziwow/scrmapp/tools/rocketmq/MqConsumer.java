
package com.ziwow.scrmapp.tools.rocketmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;

public class MqConsumer {
	private final Logger logger = LoggerFactory.getLogger(MqConsumer.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;
    private String namesrvAddr;
    private String consumerGroup;
    private String topic;
    private String tags;
    private MessageListenerConcurrently listener;
    
    public void init() throws InterruptedException, MQClientException {
        // 参数信息
        logger.info("DefaultMQPushConsumer initialize!");
        logger.info(consumerGroup);
        logger.info(namesrvAddr);

        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
        // 注意：ConsumerGroupName需要由应用来保证唯一
        defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));

        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(topic, tags);

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费        
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置为集群消费(区别于广播消费)        
        //defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(listener);
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>        
        defaultMQPushConsumer.start();

        logger.info("DefaultMQPushConsumer start success!");
    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }
	public DefaultMQPushConsumer getDefaultMQPushConsumer() {
		return defaultMQPushConsumer;
	}
	public void setDefaultMQPushConsumer(DefaultMQPushConsumer defaultMQPushConsumer) {
		this.defaultMQPushConsumer = defaultMQPushConsumer;
	}
	public void setTopic(String topic){
	    	this.topic = topic;
	    }
	public void setTags(String tags){
	    	this.tags =  tags;
	    }

	public Logger getLogger() {
		return logger;
	}

	public String getNamesrvAddr() {
		return namesrvAddr;
	}

	public String getConsumerGroup() {
		return consumerGroup;
	}

	public String getTopic() {
		return topic;
	}

	public String getTags() {
		return tags;
	}

	public MessageListenerConcurrently getListener() {
		return listener;
	}

	public void setListener(MessageListenerConcurrently listener) {
		this.listener = listener;
	}
}

