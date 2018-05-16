package com.jimmyxframework.core;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.function.Consumer;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;

/**
 * 
 * @author Andrea_Grimandi
 *
 */
public class Server extends AbstractVerticle {

	/**
	 * 
	 */
	@Override
	public void start() throws Exception {
		Properties applicationProperties = new Properties();
		applicationProperties.load(new FileInputStream("app.config"));
		new ApiBuilder(vertx, applicationProperties.getProperty("path"))
				.start(Integer.parseInt(applicationProperties.getProperty("socket")));
	}

	/**
	 * 
	 */
	public void startup() {
		Consumer<Vertx> runner = vertx -> {
			vertx.deployVerticle(Server.class.getName());
		};

		runner.accept(Vertx.vertx());
	}
}
