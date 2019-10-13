package com.incentives.piggyback.user.subscriber;

import com.google.gson.Gson;
import com.incentives.piggyback.user.model.UserPartnerDto;
import com.incentives.piggyback.user.service.UserService;
import com.incentives.piggyback.user.util.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class KafkaReceiver {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaReceiver.class);

    Gson gson = new Gson();

    @Autowired
    private UserService userService;

    @KafkaListener(topics = {"partnerEvents"})
    public void listen(@Payload String message) {
        LOG.info("User Service: received message='{}'", message);

        List<String> eventList = Arrays.asList(message.split(";"));
        UserPartnerDto userPartnerDto = null;
        try {
            userPartnerDto = gson.fromJson(eventList.get(0), UserPartnerDto.class);
            if (eventList.get(1).equalsIgnoreCase(Constant.USER_PARTNER_MAPPING)) {
                userService.updateUserwithPartnerId(userPartnerDto);
            }
        } catch (Exception e) {
            LOG.error("User Service: messageReceiver for partner id failed error {}", e);
        }
    }

}