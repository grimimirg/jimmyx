package com.jmxf.core;

import java.util.Set;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmxf.core.annotation.ApiDefaultResponse;
import com.jmxf.core.annotation.Controller;
import com.jmxf.core.annotation.RequestMapping;
import com.jmxf.core.annotation.SerializeJson;

import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Try;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * @author Andrea_Grimandi
 */
public class ApiBuilder {

	private Router router;

	private Vertx vertx;

	private Reflections reflections;

	/**
	 * @param vertx
	 */
	public ApiBuilder(Vertx vertx) {
		super();
		this.vertx = vertx;
		this.router = Router.router(vertx);
		this.router.route().handler(BodyHandler.create());
	}

	/**
	 * @param vertx
	 * @param configuration
	 */
	public ApiBuilder(Vertx vertx, ConfigurationBuilder configuration) {
		super();
		this.vertx = vertx;
		this.router = Router.router(vertx);
		this.router.route().handler(BodyHandler.create());
		this.reflections = new Reflections(configuration);
		this.services();
	}

	/**
	 * @param vertx
	 * @param path
	 */
	public ApiBuilder(Vertx vertx, String path) {
		super();
		this.vertx = vertx;
		this.router = Router.router(vertx);
		this.router.route().handler(BodyHandler.create());
		this.reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage(path)));
		this.services();
	}

	/**
	 *
	 */
	private void services() {
		Set<Class<?>> services = this.reflections.getTypesAnnotatedWith(Controller.class);
		for (Class<?> cls : services) {
			Try.of(() -> this.service(cls.newInstance())).onFailure(action -> {
				System.err.println(action.getMessage());
			});
		}
	}

	/**
	 * @param path
	 * @param handler
	 * @return
	 */
	public ApiBuilder handler(String path, Handler<RoutingContext> handler) {
		this.router.get(path).handler(handler);
		return this;
	}

	/**
	 * @param service
	 * @return
	 */
	public ApiBuilder service(Object service) {
		List.of(service.getClass().getDeclaredMethods()).forEach(method -> {

			Controller apiController = service.getClass().getAnnotation(Controller.class);
			RequestMapping apiMapping = method.getAnnotation(RequestMapping.class);
			SerializeJson apiSerializeJson = method.getAnnotation(SerializeJson.class);
			ApiDefaultResponse apiDefaultResponse = method.getAnnotation(ApiDefaultResponse.class);

			if (apiMapping != null) {
				this.router.routeWithRegex(apiMapping.method(), apiController.path() + apiMapping.path())
						.handler(context -> {
							Object apiResult = Try
									.of(() -> new InvocationHelper().invokeMethod(service, method, context))
									.getOrElseGet(exceptionProvider -> exceptionProvider.getCause().getMessage());

							String response = null;
							if (apiSerializeJson != null) {
								response = Try.of(() -> new ObjectMapper().writeValueAsString(apiResult))
										.getOrElse(new String());
							}

							HttpServerResponse httpResponse = context.response();

							if (!apiMapping.headers().equals("")) {
								List.of(apiMapping.headers().split(";")).map(element -> Map
										.entry(element.split("=")[0].trim(), element.split("=")[1].trim()))
										.forEach(tuple -> {
											httpResponse.putHeader(tuple._1(), tuple._2());
										});
							}

							if (apiDefaultResponse != null) {
								httpResponse.setStatusCode(apiDefaultResponse.status());
							}

							if (response != null) {
								httpResponse.end(apiSerializeJson != null ? response : apiResult.toString());
							} else {
								httpResponse.end();
							}

						});

				System.out
						.println(service.getClass().getName() + " [" + apiController.path() + apiMapping.path() + "]");
			}
		});

		return this;
	}

	/**
	 * @param socket
	 */
	public void start(int socket) {
		System.out.println("Running on " + socket);
		this.vertx.createHttpServer().requestHandler(this.router::accept).listen(socket);
	}
}
