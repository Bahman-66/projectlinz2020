package at.projectlinz.mockdata;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.IMotor;

public class MockMotor implements IMotor {

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
