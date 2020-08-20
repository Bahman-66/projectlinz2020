package at.projectlinz.motorhandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.IMotor;
import at.projectlinz.hardware.TouchSensorListener;

public class MotorHandler {

	private static Logger log = Logger.getLogger(MotorHandler.class.toString());
	private static TouchSensorListener touchSensorListener;
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
				touchSensorListener.onFetchSampleTouchSensor();
				service.shutdown();
			}
		});

	}

	public static void setTouchSensorListener(TouchSensorListener onTouchListener) {
		log.info("sensor listener seted");
		MotorHandler.touchSensorListener = onTouchListener;
	}

}
