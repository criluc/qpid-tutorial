package it.nic.demo;

import lombok.Getter;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class CamelDemo {
	
	@Getter
	private CamelContext context;
	
	private static final String BROKER_URI = "failover://(tcp://localhost:61616)";
	
	public CamelDemo() throws Exception {
		context = new DefaultCamelContext();
		context.setTracing(false);
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URI);
		context.addComponent("activemq", 
				JmsComponent.jmsComponentAutoAcknowledge(new PooledConnectionFactory(connectionFactory)));
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("activemq:camel.demo.queue?concurrentConsumers=5")
				.to("stream:out")
				.to("activemq:camel.demo.newqueue");
			}
		});
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("activemq:camel.demo.mail.queue?concurrentConsumers=2")
				.to("smtp://smtp.iit.cnr.it?port=25&to=lorenzo.luconi@iit.cnr.it&from=cristian.lucchesi@iit.cnr.it&username=lorenzo.luconi@iit.cnr.it&subject=Demo&password="+Properties.password);
			}
		});
		
		context.addRoutes(new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("activemq:camel.demo.transform")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						exchange.getIn().setBody("Ho processato: " + exchange.getIn().getBody());
					}
				})
				.to("activemq:camel.demo.destQueue");
			}
		});
		
		
	}
	
	public static void main(String[] args) throws Exception {
		CamelDemo camelDemo = new CamelDemo();
		camelDemo.getContext().start();
	}

}
