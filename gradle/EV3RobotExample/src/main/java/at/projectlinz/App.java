package at.projectlinz;

import org.apache.log4j.Logger;

import com.github.oxo42.stateless4j.StateMachine;

import at.projectlinz.hardware.BigMotor;
import at.projectlinz.hardware.BigMotor.MotorPos;
import at.projectlinz.hardware.Robot;
import at.projectlinz.listeners.TouchSensorListenner;
import at.projectlinz.listeners.UltrasonicSensorListener;
import at.projectlinz.listeners.SensorListener.Sensor;
import at.projectlinz.motorhandler.MotorHandler;
import at.projectlinz.statemachine.RobotStateMachineConfig;
import at.projectlinz.statemachine.RobotStateMachineConfig.State;
import at.projectlinz.statemachine.RobotStateMachineConfig.Trigger;
import ev3dev.sensors.ev3.EV3TouchSensor;
import ev3dev.sensors.ev3.EV3UltrasonicSensor;
import lejos.hardware.port.SensorPort;

public class App {

	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		log.info("start app!");
		try {
			MotorHandler handler = new MotorHandler();
			EV3UltrasonicSensor ulSensor = new EV3UltrasonicSensor(SensorPort.S2);
			EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
			StateMachine<State, Trigger> fsm = RobotStateMachineConfig.getRobotStateMachine(handler.getControl());
			BigMotor leftMotor = new BigMotor('A', 50);
			BigMotor rightMotor = new BigMotor('B', 50);
			Robot r = new Robot();
			r.addSensors(Sensor.ULTRASONIC, ulSensor);
			r.addSensors(Sensor.TOUCH, touchSensor);
			r.addMotors(MotorPos.LEFT_MOTOR, leftMotor);
			r.addMotors(MotorPos.RIGTH_MOTOR, rightMotor);
			handler.setRobot(r);
			handler.addSensorListener(Sensor.ULTRASONIC, new UltrasonicSensorListener(ulSensor));
			handler.addSensorListener(Sensor.TOUCH, new TouchSensorListenner(touchSensor));
			handler.registerControl();
			handler.setStateMachine(fsm);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("ends app!");
	}

}
