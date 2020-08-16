package at.projectlinz.hardware;

import org.apache.log4j.Logger;

import lejos.hardware.motor.Motor;

public class BigMotor implements IMotor {

	private char port;
	private static Logger log = Logger.getLogger(BigMotor.class);

	public BigMotor(char p) {
		this.port = p;
	}

	@Override
	public void forward() {
		switch (this.port) {
		case 'A':
			log.info("motor A move forward");
			Motor.A.forward();
			break;
		case 'C':
			log.info("motor C move forward");
			Motor.C.forward();
			break;
		default:
			break;
		}
	}

	@Override
	public void stop() {
		log.info("stop motor");
	}

}
