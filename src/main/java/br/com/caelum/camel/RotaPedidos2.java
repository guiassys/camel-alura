package br.com.caelum.camel;


import java.util.logging.Logger;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpMethods;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos2 {

	private static final Logger logger = Logger.getLogger(RotaPedidos2.class.getName());
	public static void main(String[] args) throws Exception {

		logger.info("Inicio do programa");

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(
				new RouteBuilder() {

					@Override
					public void configure() throws Exception {
						from("file:pedidos?delay=5s&noop=true")
							.split()
								.xpath("/pedido/itens/item")
							.filter()
								.xpath("/item/formato[text()='EBOOK']")
							.log("${id} - ${exchange.pattern} - Transferindo ${file:name} para webservice item")
							.convertBodyTo(byte[].class)
							.marshal()
								.xmljson()
							.log("${body}")
								.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
						.to("http4://localhost:8080/webservices/ebook/item");
					}
				}
		);

		context.start();
		Thread.sleep(20000);
		context.stop();
		logger.info("Fim do programa");
	}
}
