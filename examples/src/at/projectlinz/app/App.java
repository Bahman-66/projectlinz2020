package at.projectlinz.app;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.BigMotor;
import at.projectlinz.hardware.TouchSensorListenner;
import at.projectlinz.hardware.UltrasonicSensorListener;
import at.projectlinz.motorhandler.MotorHandler;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class App {

	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		log.info("start app!");

		MotorHandler.addSensorListener(new UltrasonicSensorListener(new EV3UltrasonicSensor(SensorPort.S2)));
		MotorHandler.addSensorListener(new TouchSensorListenner(new EV3TouchSensor(SensorPort.S1)));

		MotorHandler.handle(new BigMotor('A', 50), new BigMotor('B', 50));

		log.info("ends app!");
	}

}
