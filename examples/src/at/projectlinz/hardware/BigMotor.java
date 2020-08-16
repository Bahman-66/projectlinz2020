package at.projectlinz.hardware;

import org.apache.log4j.Logger;

import lejos.hardware.motor.Motor;

public class BigMotor implements IMotor {

	private char port;
	private int speed;
	private static Logger log = Logger.getLogger(BigMotor.class);

	public BigMotor(char port, int speed) {
		this.port = port;
		this.speed = speed;
	}

	@Override
	public void forward() {
		switch (this.port) {
		case 'A':
			log.info("motor A move forward");
			Motor.A.setSpeed(speed);
			Motor.A.forward();
			break;
		case 'B':
			log.info("motor B move forward");
			Motor.B.setSpeed(speed);
			Motor.B.forward();
			break;
		case 'C':
			log.info("motor C move forward");
			Motor.C.setSpeed(speed);
			Motor.C.forward();
			break;
		case 'D':
			log.info("motor D move forward");
			Motor.D.setSpeed(speed);
			Motor.D.forward();
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
