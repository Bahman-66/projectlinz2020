package at.projectlinz.tests;


import org.junit.Test;

import at.projectlinz.hardware.SensorListener;
import at.projectlinz.mockdata.MockMotor;
import at.projectlinz.motorhandler.Control;
import at.projectlinz.motorhandler.MotorHandler;

public class TestMotorHandler {

	@Test
	public void testMotorToBeActivatedAndStopedAfterFiveSecond() {

		try {
			MotorHandler.addSensorListener(new SensorListener() {

				@Override
				public void onSensorSampling() {
					System.out.println("touched");

				}

				@Override
				public void registerRobotControl(Control service) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void setSensor(Object sensor) {
					// TODO Auto-generated method stub
					
				}

				

			});
			MotorHandler.handle(new MockMotor());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
