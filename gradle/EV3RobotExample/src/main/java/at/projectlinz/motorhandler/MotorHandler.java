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
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;


public class MotorHandler {

	private static Logger log = Logger.getLogger(MotorHandler.class.toString());
	private Control control;
	private ExecutorService pool = Executors.newScheduledThreadPool(2);
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

	public Control getControl() {
		return this.control;
	}

	public void updateMotor(final Event event) throws Exception {
		if (event instanceof SensorEvent) {
			for (IMotor m : event.getControl().getMotors().values()) {
				m.stop();
			}
			log.info(pool.shutdownNow());
			System.exit(0);
		} else if (event instanceof MotorEvent) {
			MotorEvent newEvent = new MotorEvent(event.getListener());
			log.info("motor events arrived");
			switch (((MotorEvent) event).getDirection()) {
			case FORWARD:
				executeSampling();
				activateMotorsForward(event);
				break;
			case RIGHT:
				log.info("move right");
				log.info("ulsensor is " + sensorListener.get("ulsensor").isSampling());
				event.getControl().getMotors().get("right").stop();
				event.getControl().getMotors().get("left").setSpeed(100);
				event.getControl().getMotors().get("left").forward();
				newEvent.setDirection(MoveDirection.FORWARD);
				updateMotor(newEvent);
				break;
			case LEFT:
				log.info("move left");
				log.info("ulsensor is " + sensorListener.get("ulsensor").isSampling());
				event.getControl().getMotors().get("left").stop();
				event.getControl().getMotors().get("right").setSpeed(100);
				event.getControl().getMotors().get("right").forward();
				newEvent = new MotorEvent(event.getListener());
				newEvent.setDirection(MoveDirection.FORWARD);
				updateMotor(newEvent);
				break;
			default:
				break;
			}

		}
	}

	private void activateMotorsForward(final Event event) {
		for (IMotor m : event.getControl().getMotors().values()) {
			m.setSpeed(30);
			m.forward();
		}

	}

	private void executeSampling() {
		for (final String key : sensorListener.keySet()) {
			log.info("execution of sampling sensor " + key + " is " + sensorListener.get(key).isSampling());

			if (!sensorListener.get(key).isSampling() && !pool.isShutdown()) {
				log.info("execute " + key);
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
