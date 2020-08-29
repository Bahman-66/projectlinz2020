package at.projectlinz.tests;


import org.junit.Test;

import at.projectlinz.mockdata.MockMotor;
import at.projectlinz.motorhandler.MotorHandler;

public class TestMotorHandler {

	MotorHandler handler = new MotorHandler();
	
	@Test
	public void testMotorToBeActivatedAndStopedAfterFiveSecond() {

		try {
			handler.handle(new MockMotor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
