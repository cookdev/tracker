package org.anyframe.cloud.cargo.user.infrastructure.amqp.rabbit.publisher;

import org.anyframe.cloud.cargo.user.application.ApplicationEvents;
import org.anyframe.cloud.cargo.user.domain.RegisteredUser;
import org.anyframe.cloud.cargo.user.infrastructure.amqp.rabbit.message.NewRegistrationNotification;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

//TODO Service로 등록하는게 맞나? 아니면 별도로 생성한 @을 통해 로딩되어야 하나?
//@Service
public class RabbitApplicationEvents implements ApplicationEvents {

//	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Override
	public void userRegistered(RegisteredUser registeredUser) {
		// TODO Auto-generated method stub
		NewRegistrationNotification newRegistrationNotification =new NewRegistrationNotification(registeredUser.getId(), registeredUser.getEmailAddress());
		rabbitTemplate.convertAndSend("user-registrations", "notify-new-user", newRegistrationNotification);
	}

}
