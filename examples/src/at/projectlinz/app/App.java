package at.projectlinz.app;

import org.apache.log4j.Logger;

import at.projectlinz.hardware.BigMotor;
import at.projectlinz.hardware.TouchSensorListener;
import at.projectlinz.motorhandler.MotorHandler;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;

public class App {

	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		log.info("start app!");
		MotorHandler.setTouchSensorListener(new TouchSensorListener() {

			EV3TouchSensor t = new EV3TouchSensor(SensorPort.S1);

			@Override
			public void onFetchSampleTouchSensor() {
				int sampleSize = t.sampleSize();
				float[] sample = new float[sampleSize];
				log.info("value before sampling: "+ sample[0]);
				while (sample[0] == 0f) {
					t.fetchSample(sample, 0);
				}
				log.info("value after sampling: "+ sample[0]);

			}
		});

		MotorHandler.handle(new BigMotor('B', 500), new BigMotor('A', 500));
		log.info("ends app!");
	}

}
