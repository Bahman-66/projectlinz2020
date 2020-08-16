package at.projectlinz.app;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.BigMotor;
import at.projectlinz.motorhandler.MotorHandler;

public class App {

	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		log.info("start app!");
		MotorHandler.handle(new BigMotor('B', 500), new BigMotor('A', 200));
	}

}
