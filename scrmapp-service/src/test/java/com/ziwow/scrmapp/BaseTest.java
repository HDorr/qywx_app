package com.ziwow.scrmapp;

/**
 * 
 */

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * ClassName: BaseTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * date: 2015-7-20 下午3:18:10 <br/>
 * 
 * @author john.chen
 * @version
 * @since JDK 1.6
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring/spring-context.xml", "classpath*:spring/spring-datasource.xml", "classpath*:spring/spring-redis.xml",
        "classpath*:spring/spring-resource.xml", "classpath*:spring/spring-transaction.xml" })
@Transactional
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class BaseTest {

}
