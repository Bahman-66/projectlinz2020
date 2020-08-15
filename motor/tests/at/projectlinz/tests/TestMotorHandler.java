package at.projectlinz.tests;

import javax.naming.directory.InvalidAttributeValueException;

import org.junit.Test;

import at.projectlinz.mockdata.MockMotor;
import at.projectlinz.motorhandler.MotorHandler;

public class TestMotorHandler {

	@Test
	public void testMotorToBeActivatedAndStopedAfterFiveSecond() {

		try {
			MotorHandler.handel(new MockMotor());
		} catch (InvalidAttributeValueException e) {
			e.printStackTrace();
		}
	}

}
