package at.projectlinz.app;

import org.apache.log4j.Logger;


import at.projectlinz.hardware.BigMotor;
import at.projectlinz.hardware.Robot;
import at.projectlinz.motorhandler.MotorHandler;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class App {

	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		log.info("start app!");
		try {
			MotorHandler handler = new MotorHandler();
			EV3UltrasonicSensor ulSensor = new EV3UltrasonicSensor(SensorPort.S2);
			EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S1);
			BigMotor leftMotor = new BigMotor('A', 50);
			BigMotor rightMotor = new BigMotor('B', 50);
			Robot r = new Robot();
			r.addSensors("ulsensor", ulSensor);
			r.addSensors("touchsensor", touchSensor);
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
