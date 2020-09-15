package at.projectlinz;


import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.statemachine.StateMachine;

import at.projectlinz.hardware.BigMotor;
import at.projectlinz.hardware.Robot;
import at.projectlinz.hardware.Sensor;
import at.projectlinz.motorhandler.MotorHandler;
import at.projectlinz.statemachine.RobotStateMachine;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static Logger log = Logger.getLogger(Application.class);

	@Autowired
	StateMachine<String, String> stateMachine;
	
	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("start app!");
		RobotStateMachine rsm = new RobotStateMachine();
		rsm.setStateMachine(stateMachine);
		rsm.getStateMachine().addStateListener(rsm.listener());
		rsm.getStateMachine().start();
		rsm.signalStateMachine();
		
		try {
			MotorHandler handler = new MotorHandler();
			EV3UltrasonicSensor ulSensor = new EV3UltrasonicSensor(SensorPort.S2);
			EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
			BigMotor leftMotor = new BigMotor('A', 50);
			BigMotor rightMotor = new BigMotor('B', 50);
			Robot r = new Robot();
			r.addSensors(Sensor.ULTRASONIC, ulSensor);
			r.addSensors(Sensor.TOUCHSENSOR, touchSensor);
			r.addMotors("left", leftMotor);
			r.addMotors("right", rightMotor);
			handler.setRobot(r);

			handler.handle();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("ends app!");

	}

}
