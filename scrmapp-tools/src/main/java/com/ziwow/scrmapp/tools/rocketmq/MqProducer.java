
package com.ziwow.scrmapp.tools.rocketmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
/**
 * 
 * ClassName: MqProducer <br/>
 * Function: 全局producer<br/>
 * date: 2015-8-17 上午10:13:09 <br/>
 *
 * @author daniel.wang
 * @version 
 * @since JDK 1.6
 */
public class MqProducer extends DefaultMQProducer{
	private final Logger logger = LoggerFactory.getLogger(MqProducer.class);

    private DefaultMQProducer defaultMQProducer;
    private String producerGroup;
    private String namesrvAddr;
   /**
    * 
    * init:(初始化). <br/>
    *
    * @author daniel.wang
    * @throws MQClientException
    * @since JDK 1.6
    */
    public void init() throws MQClientException {
        // 参数信息
        logger.info("DefaultMQProducer initialize!");
        logger.info(producerGroup);
        logger.info(namesrvAddr);

        // 初始化
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        
        defaultMQProducer.start();
        logger.info("DefaultMQProudcer start success!");
    }
    
    /**
     * 
     * destroy:(关闭producer). <br/>
     *
     * @author daniel.wang
     * @since JDK 1.6
     */
    public void destroy() {
    	logger.info("DefaultMQProudcer shutdown success!");
        defaultMQProducer.shutdown();
    }
    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }
}

