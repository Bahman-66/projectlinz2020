package at.projectlinz.tests;


import org.junit.Test;

import at.projectlinz.mockdata.MockMotor;
import at.projectlinz.motorhandler.MotorHandler;

public class TestMotorHandler {

	@Test
	public void testMotorToBeActivatedAndStopedAfterFiveSecond() {

		try {
			MotorHandler.handle(new MockMotor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
