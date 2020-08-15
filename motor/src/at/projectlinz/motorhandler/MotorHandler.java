package at.projectlinz.motorhandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.naming.directory.InvalidAttributeValueException;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.Motor;

public class MotorHandler {

	private static Logger log = Logger.getLogger(MotorHandler.class);
	private final static ExecutorService service = Executors.newSingleThreadExecutor();

	public static void handel(final Motor... motors) throws InvalidAttributeValueException {

		if (motors.length == 0) {
			throw new InvalidAttributeValueException("no motors is given to handle!");
		}

		service.submit(new Runnable() {

			@Override
			public void run() {
				for (Motor motor : motors) {
					motor.forward();
				}
			}
		});

		try {
			if (!service.awaitTermination(5, TimeUnit.SECONDS)) {
				for (Motor motor : motors) {
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
