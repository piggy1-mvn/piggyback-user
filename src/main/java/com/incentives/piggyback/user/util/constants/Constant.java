package com.incentives.piggyback.user.util.constants;

public interface Constant {
	String USER_PUBLISHER_TOPIC = "user.publish.event";
	String USER_SOURCE_ID = "2";
	String USER_CREATED_EVENT = "User Events Created";
	String USER_UPDATED_EVENT = "User Events Updated";
	String USER_DEACTIVATED_EVENT = "User Events Deactivated";
	long JWT_TOKEN_VALIDITY = 5*60;
}