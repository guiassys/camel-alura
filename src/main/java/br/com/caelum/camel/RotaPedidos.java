package br.com.caelum.camel;


import java.util.logging.Logger;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

public class RotaPedidos {

	private static final Logger logger = Logger.getLogger(RotaPedidos.class.getName());
	public static void main(String[] args) throws Exception {

		logger.info("Inicio do programa");

		CamelContext context = new DefaultCamelContext();
		context.addRoutes(
				new RouteBuilder() {

					@Override
					public void configure() throws Exception {
						from("file:pedidos")
						.log("${id}: Transferindo ${file:name} para o diretório saida - bugfix")
						.to("file:saida");
					}
				}
		);

		context.start();
		Thread.sleep(20000);
		context.stop();
		logger.info("Fim do programa");
	}
}
