package at.projectlinz.server;

import at.projectlinz.controls.Control;
import at.projectlinz.listeners.SensorListener.Sensor;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public class ReactServer extends AbstractVerticle {

	Control control;

	public ReactServer(final Control c) {
		this.control = c;
	}

	@Override
	public void start() throws Exception {
		// tag::backend[]
		Router router = Router.router(vertx);
		Route messageRoute = router.get("/api/message"); // <1>
		Route ulValue = router.get("/api/ul");
		
		
		messageRoute.handler(rc -> {
			rc.response().end("Hello React from Vert.x!"); // <2>
		});

		ulValue.handler(rc -> {
			rc.response().end(this.control.getSensors().get(Sensor.ULTRASONIC).getSampleValue());
		});
		
		router.get().handler(StaticHandler.create()); // <3>

		vertx.createHttpServer().requestHandler(router).listen(8080);
		// end::backend[]
	}

}
