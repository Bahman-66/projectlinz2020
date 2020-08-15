package at.projectlinz.mockdata;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.Motor;

public class MockMotor implements Motor {

	private static Logger log = Logger.getLogger(MockMotor.class);

	@Override
	public void forward() {
		log.info("move forward");
	}

	@Override
	public void stop() {
		log.info("stop motor");
	}

}
