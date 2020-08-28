package at.projectlinz.hardware;

import org.apache.log4j.Logger;

import at.projectlinz.motorhandler.Control;
import at.projectlinz.motorhandler.MotorHandler;
import lejos.hardware.sensor.EV3TouchSensor;

public class TouchSensorListenner implements SensorListener {

	private Logger log = Logger.getLogger(MotorHandler.class.toString());
	private Control control = null;
	private EV3TouchSensor touth;

	public TouchSensorListenner(Object sensor) {
		setSensor(sensor);
	}

	@Override
	public void onSensorSampling() {

		int sampleSize = touth.sampleSize();
		float[] sample = new float[sampleSize];
		log.info("value before sampling: " + sample[0]);
		while (sample[0] == 0f) {
			touth.fetchSample(sample, 0);
		}
		log.info("value after sampling: " + sample[0]);
		this.control.shutdown();
	}

	@Override
	public void registerRobotControl(Control control) {
		this.control = control;

	}

	@Override
	public void setSensor(Object sensor) {
		this.touth = (EV3TouchSensor) sensor;
	}

}
