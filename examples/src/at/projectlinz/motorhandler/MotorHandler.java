package at.projectlinz.motorhandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.IMotor;

public class MotorHandler {

	private static Logger log = Logger.getLogger(MotorHandler.class.toString());
	private final static ExecutorService service = Executors.newSingleThreadExecutor();

	public static void handle(final IMotor... motors) {

		if (motors.length == 0) {
			throw new IllegalArgumentException("no motors is given to handle!");
		}

		service.submit(new Runnable() {

			@Override
			public void run() {
				for (IMotor motor : motors) {
					motor.forward();
				}
			}
		});

		try {
			if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
				for (IMotor motor : motors) {
					motor.stop();
				}
				service.shutdown();
			}
		} catch (InterruptedException e) {
			log.error("motor thread is interrupted");
			e.printStackTrace();
		}
	}

}
