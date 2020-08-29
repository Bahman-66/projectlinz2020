package at.projectlinz.motorhandler;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import at.projectlinz.controls.Control;
import at.projectlinz.hardware.IMotor;
import at.projectlinz.listeners.SensorListener;

public class MotorHandler {

	private Logger log = Logger.getLogger(MotorHandler.class.toString());
	private List<SensorListener> sensorListener = new ArrayList<SensorListener>();
	private final ExecutorService service = Executors.newSingleThreadExecutor();

	public void handle(final IMotor... motors) {

		if (motors.length == 0) {
			throw new IllegalArgumentException("no motors is given to handle!");
		}
		registerControl(new Control(motors, service));
		
		service.submit(new Runnable() {

			@Override
			public void run() {

				for (IMotor motor : motors) {
					motor.forward();
				}
			}
		});
		for (SensorListener l : sensorListener) {
			l.onSensorSampling();
		}

	}

	public ExecutorService getExecuter() {
		return service;
	}

	public void addSensorListener(SensorListener listener) {
		log.info("sensor listener seted");
		sensorListener.add(listener);
	}

	public void registerControl(Control c) {
		for (SensorListener l : sensorListener) {
			l.registerControl(c);
		}
	}

}
