package it.nic.demo;

import org.apache.camel.ProducerTemplate;

public class CamelSender {
	
	public static void main(String[] args) throws Exception {
		CamelDemo camelDemo = new CamelDemo();
		camelDemo.getContext().start();
		
		ProducerTemplate producer = camelDemo.getContext().createProducerTemplate();
		producer.sendBody("activemq:camel.demo.queue", "Prova invio messsagio");
		
		camelDemo.getContext().stop();
		
		// Request-reply
		//String response = producer.requestBody("activemq:camel.demo.queue", "Prova invio messsagio", String.class);
	}

}
