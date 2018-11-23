package com.afis.cloud.auth.config;

import java.sql.SQLException;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.afis.cloud.auth.business.store.LogManagements;
import com.afis.cloud.auth.business.store.impl.LogManagementsImpl;
import com.afis.cloud.auth.model.kafka.LoginLogModel;
import com.afis.cloud.auth.model.kafka.OperateLogModel;

@Component
public class KafkaConfig {

	// 登录日志topic
	public static final String AFIS_AUTH_LOGIN_TOPIC = "auth-login-topic";
	// 操作日志topic
	public static final String AFIS_AUTH_OPERATE_TOPIC = "auth-operate-topic";

	private Logger logger = LoggerFactory.getLogger(getClass());

	private LogManagements logManagements = new LogManagementsImpl();

	@KafkaListener(topics = AFIS_AUTH_LOGIN_TOPIC)
	public void receiveOperateLog(ConsumerRecord<?, ?> consumer) {
		logger.info("{} - :{}", consumer.topic(), consumer.value());
		LoginLogModel log = (LoginLogModel) consumer.value();
		try {
			logManagements.insertLoginLog(log);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@KafkaListener(topics = AFIS_AUTH_OPERATE_TOPIC)
	public void receiveLoginLog(ConsumerRecord<?, ?> consumer) {
		logger.info("{} - :{}", consumer.topic(), consumer.value());
		OperateLogModel log = (OperateLogModel) consumer.value();
		try {
			logManagements.insertOperateLog(log);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
