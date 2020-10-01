package at.projectlinz.hardware;

import org.apache.log4j.Logger;

import ev3dev.actuators.lego.motors.Motor;


public class BigMotor implements IMotor {

	private char port;
	private int speed;
	

	private static Logger log = Logger.getLogger(BigMotor.class);

	public BigMotor(char port, int speed) {
		this.port = port;
		this.speed = speed;
	}
	
	public void setSpeed(int speed) {
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
		switch (this.port) {
		case 'A':
			log.info("motor A stop");
			Motor.A.stop();
			break;
		case 'B':
			log.info("motor B stop");
			Motor.B.stop();
			break;
		case 'C':
			log.info("motor C stop");
			Motor.C.stop();
			break;
		case 'D':
			log.info("motor D stop");
			Motor.D.stop();
			break;
		default:
			break;
		}
	}

	@Override
	public void backward() {
		switch (this.port) {
		case 'A':
			log.info("motor A move backward");
			Motor.A.setSpeed(speed);
			Motor.A.backward();
			break;
		case 'B':
			log.info("motor B move backward");
			Motor.B.setSpeed(speed);
			Motor.B.backward();
			break;
		case 'C':
			log.info("motor C move backward");
			Motor.C.setSpeed(speed);
			Motor.C.backward();
			break;
		case 'D':
			log.info("motor D move backward");
			Motor.D.setSpeed(speed);
			Motor.D.backward();
			break;
		default:
			break;
		}

	}

}
