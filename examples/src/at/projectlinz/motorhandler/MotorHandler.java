package at.projectlinz.motorhandler;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import at.projectlinz.controls.Control;
import at.projectlinz.hardware.IMotor;
import at.projectlinz.hardware.Robot;
import at.projectlinz.listeners.ISensorListener;
import at.projectlinz.listeners.TouchSensorListenner;
import at.projectlinz.listeners.UltrasonicSensorListener;
import at.projectlinz.listeners.events.Event;
import at.projectlinz.listeners.events.MotorEvent;
import at.projectlinz.listeners.events.MoveDirection;
import at.projectlinz.listeners.events.SensorEvent;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class MotorHandler {

	private static Logger log = Logger.getLogger(MotorHandler.class.toString());
	private Control control;
	private ExecutorService pool = Executors.newCachedThreadPool();
	private Map<String, ISensorListener> sensorListener = new HashMap<>();
	private Robot robot;

	public void handle() {
		if (robot.numberOfMotors() == 0) {
			throw new IllegalArgumentException("no motors is given to handle!");
		}
		try {
			control = new Control(this);
			addSensorListener("ulsensor",
					new UltrasonicSensorListener((EV3UltrasonicSensor) robot.getSensor("ulsensor")));
			addSensorListener("touchsensor", new TouchSensorListenner((EV3TouchSensor) robot.getSensor("touchsensor")));
			registerControl(control);
			MotorEvent event = new MotorEvent(control);
			event.setDirection(MoveDirection.FORWARD);
			updateMotor(event);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	public void addSensorListener(String key, final ISensorListener listener) {
		log.info("sensor listener seted");
		sensorListener.put(key, listener);
	}

	public void registerControl(Control c) {
		for (ISensorListener l : sensorListener.values()) {
			l.setControl(c);
		}
	}

	public Collection<ISensorListener> getListeners() {
		return sensorListener.values();
	}

	public Robot getRobot() {
		return robot;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public void updateMotor(final Event event) throws Exception {
		if (event instanceof SensorEvent) {
			for (IMotor m : event.getControl().getMotors().values()) {
				m.stop();
			}
		} else if (event instanceof MotorEvent) {
			log.info("motor events arrived");
			switch (((MotorEvent) event).getDirection()) {
			case FORWARD:
				execute();
				for (IMotor m : event.getControl().getMotors().values()) {
					m.forward();
				}
				break;
			case RIGHT:
				log.info("move right");
				log.info("ulsensor is " + sensorListener.get("ulsensor").isSampling());
				execute();
				event.getControl().getMotors().get("right").stop();
				MotorEvent newEvent = new MotorEvent(event.getListener());
				newEvent.setDirection(MoveDirection.FORWARD);
				updateMotor(newEvent);
				break;
			default:
				break;
			}

		}
	}

	public Control getControl() {
		return this.control;
	}

	private void execute() {
		for (final String key : sensorListener.keySet()) {
			log.info("execution of sampling sensor " + key + " is " + sensorListener.get(key).isSampling());
			log.info("execute " + key);
			if (!sensorListener.get(key).isSampling()) {
				pool.execute(new Runnable() {

					@Override
					public void run() {
						sensorListener.get(key).onSensorSampling();
					}
				});

			}
		}
	}

}
