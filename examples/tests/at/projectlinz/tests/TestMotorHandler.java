package at.projectlinz.tests;


import org.junit.Test;

import at.projectlinz.motorhandler.MotorHandler;

public class TestMotorHandler {

	MotorHandler handler = new MotorHandler();
	
	@Test
	public void testMotorToBeActivatedAndStopedAfterFiveSecond() {

		try {
			handler.handle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
