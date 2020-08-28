package at.projectlinz.motorhandler;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.IMotor;
import at.projectlinz.hardware.SensorListener;

public class MotorHandler {

	private static Logger log = Logger.getLogger(MotorHandler.class.toString());
	private static List<SensorListener> sensorListener = new ArrayList<SensorListener>();
	private final static ExecutorService service = Executors.newSingleThreadExecutor();

	public static void handle(final IMotor... motors) {
		
		final Control control = new Control(motors , service);

		if (motors.length == 0) {
			throw new IllegalArgumentException("no motors is given to handle!");
		}

		service.submit(new Runnable() {

			@Override
			public void run() {

				for (IMotor motor : motors) {
					motor.forward();
				}

				for (SensorListener l : sensorListener) {
					l.registerRobotControl(control);
					l.onSensorSampling();
				}
			}
		});

	}

	public ExecutorService getExecuter() {
		return service;
	}

	public static void addSensorListener(SensorListener listener) {
		log.info("sensor listener seted");
		sensorListener.add(listener);
	}

}
