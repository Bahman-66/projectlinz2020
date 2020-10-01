package at.projectlinz.motorhandler;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.github.oxo42.stateless4j.StateMachine;

import at.projectlinz.controls.Control;
import at.projectlinz.hardware.IMotor;
import at.projectlinz.hardware.Robot;
import at.projectlinz.listeners.ISensorListener;
import at.projectlinz.listeners.TouchSensorListenner;
import at.projectlinz.listeners.UltrasonicSensorListener;
import at.projectlinz.listeners.events.Event;
import at.projectlinz.statemachine.RobotStateMachineConfig.State;
import at.projectlinz.statemachine.RobotStateMachineConfig.Trigger;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;

public class MotorHandler {
	
	private final Control control;
	private static Logger log = Logger.getLogger(MotorHandler.class.toString());
	private ExecutorService pool = Executors.newScheduledThreadPool(2);
	private Map<String, ISensorListener> sensorListener = new HashMap<>();
	private Robot robot;
	private StateMachine<State, Trigger> fsm;
	
	public MotorHandler() {
		control = new Control(this);
	}

	public void handle() {
		if (robot.numberOfMotors() == 0) {
			throw new IllegalArgumentException("no motors is given to handle!");
		}
		try {

			addSensorListener("ulsensor",
					new UltrasonicSensorListener((EV3UltrasonicSensor) robot.getSensor("ulsensor")));
			addSensorListener("touchsensor", new TouchSensorListenner((EV3TouchSensor) robot.getSensor("touchsensor")));
			registerControl(control);
			fsm.fire(Trigger.FORWARD);
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
		// event blocked either ultrasonic or touch
		switch (event.getType()) {
		case 0:
			fsm.fire(Trigger.FORWARD);
			break;
		case 1:
			fsm.fire(Trigger.BLOCK);
			break;
		case 2:
			fsm.fire(Trigger.STOP);
			break;
		case 3:
			fsm.fire(Trigger.RESET);
			break;
		default:
			break;
		}

	}

	public void activateMotorsForward() {
		for (IMotor m : getControl().getMotors().values()) {
			m.setSpeed(30);
			m.forward();
		}

	}

	public void executeSampling() {
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

	public void setStateMachine(StateMachine<State, Trigger> robotStateMachine) {
		this.fsm = robotStateMachine;
	}

	public void navigate() {
		log.info("do NAVIGATE");
		
	}

	public void stopMotor() {
		for (IMotor m : getControl().getMotors().values()) {
			m.stop();
		}
	}

}
