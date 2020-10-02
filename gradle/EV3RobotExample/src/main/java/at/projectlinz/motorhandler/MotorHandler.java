package at.projectlinz.motorhandler;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.github.oxo42.stateless4j.StateMachine;

import at.projectlinz.controls.Control;
import at.projectlinz.hardware.BigMotor.MotorPos;
import at.projectlinz.hardware.IMotor;
import at.projectlinz.hardware.Robot;
import at.projectlinz.listeners.ISensorListener;
import at.projectlinz.listeners.SensorListener.Sensor;
import at.projectlinz.listeners.events.Event;
import at.projectlinz.statemachine.RobotStateMachineConfig.State;
import at.projectlinz.statemachine.RobotStateMachineConfig.Trigger;

public class MotorHandler {
	private static Logger log = Logger.getLogger(MotorHandler.class.toString());

	private Robot robot;
	private final Control control;
	private StateMachine<State, Trigger> fsm;
	private ExecutorService pool = Executors.newScheduledThreadPool(2);
	private Map<Sensor, ISensorListener> sensorListener = new HashMap<>();

	public MotorHandler() {
		control = new Control(this);
	}

	public void addSensorListener(Sensor key, final ISensorListener listener) {
		log.info("sensor listener seted");
		sensorListener.put(key, listener);
	}

	public void registerControl() {
		for (ISensorListener l : sensorListener.values()) {
			l.setControl(control);
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
		case 4:
			fsm.fire(Trigger.READY);
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
		for (final Sensor key : sensorListener.keySet()) {
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
		this.fsm.configuration().enableEntryActionOfInitialState();
		this.fsm.fire(Trigger.FORWARD);
	}

	public void navigate() {
		log.info("do NAVIGATE");
		getControl().getMotors().get(MotorPos.RIGTH_MOTOR).setSpeed(200);
		getControl().getMotors().get(MotorPos.RIGTH_MOTOR).forward();
		try {
			Thread.sleep(2000);

			getControl().getMotors().get(MotorPos.RIGTH_MOTOR).stop();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void stopMotor() {
		doStopMotor();
	}

	private void doStopMotor() {
		for (IMotor m : getControl().getMotors().values()) {
			m.stop();
		}
	}

}
