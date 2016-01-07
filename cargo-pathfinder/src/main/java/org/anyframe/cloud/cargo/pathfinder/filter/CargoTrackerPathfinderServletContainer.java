package org.anyframe.cloud.cargo.pathfinder.filter;

import ch.qos.logback.access.tomcat.LogbackValve;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;

public class CargoTrackerPathfinderServletContainer implements
		EmbeddedServletContainerCustomizer {

	@Override
	public void customize(
			ConfigurableEmbeddedServletContainer paramConfigurableEmbeddedServletContainer) {

		if (paramConfigurableEmbeddedServletContainer instanceof TomcatEmbeddedServletContainerFactory) {

			TomcatEmbeddedServletContainerFactory tomcatEmbeddedServletContainerFactory = (TomcatEmbeddedServletContainerFactory) paramConfigurableEmbeddedServletContainer;

			LogbackValve logbackValve = new LogbackValve();
			logbackValve.setFilename("src/main/resources/logback-access.xml");
			tomcatEmbeddedServletContainerFactory
					.addContextValves(logbackValve);

		}

	}

}