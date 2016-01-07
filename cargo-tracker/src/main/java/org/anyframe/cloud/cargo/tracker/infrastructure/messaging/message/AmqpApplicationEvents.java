package org.anyframe.cloud.cargo.tracker.infrastructure.messaging.message;

import org.anyframe.cloud.cargo.tracker.application.ApplicationEvents;
import org.anyframe.cloud.cargo.tracker.domain.model.cargo.Cargo;
import org.anyframe.cloud.cargo.tracker.domain.model.handling.HandlingEvent;
import org.anyframe.cloud.cargo.tracker.interfaces.handling.HandlingEventRegistrationAttempt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class AmqpApplicationEvents implements ApplicationEvents, Serializable {

	private static final long serialVersionUID = 4718575734320731591L;

	@Autowired
	private Environment env;

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
    private final Logger logger = LoggerFactory.getLogger(AmqpApplicationEvents.class);

    @Override
    public void cargoWasHandled(HandlingEvent event) {
    	Cargo cargo = event.getCargo();
        logger.debug("## Cargo was handled {} ##", event);
        rabbitTemplate.convertAndSend(env.getProperty("amqp.exchange.name"), env.getProperty("amqp.routingkey.cargoHandled"), cargo.getTrackingId().getIdString());
    }

    @Override
    public void cargoWasMisdirected(Cargo cargo) {
        logger.debug("## Cargo was misdirected {} ##", cargo);
//        rabbitTemplate.convertAndSend(env.getProperty("amqp.exchange.name"), env.getProperty("amqp.routingkey.misdirected"), cargo.getTrackingId().getIdString());
    }

    @Override
    public void cargoHasArrived(Cargo cargo) {
        logger.debug("## Cargo has arrived {} ##", cargo);
//        rabbitTemplate.convertAndSend(env.getProperty("amqp.exchange.name"), env.getProperty("amqp.routingkey.delivered"), cargo.getTrackingId().getIdString());
    }

    @Override
    public void receivedHandlingEventRegistrationAttempt(HandlingEventRegistrationAttempt attempt) {
        logger.debug("## Received handling event registration attempt {} ##", attempt);
        logger.debug("## Event Type Changed 3 : {} ##", attempt.getType());
        rabbitTemplate.convertAndSend(env.getProperty("amqp.exchange.name"), env.getProperty("amqp.routingkey.handlingEvent"), attempt);
    }
    
}
