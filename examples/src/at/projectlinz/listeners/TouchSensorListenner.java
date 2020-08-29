package at.projectlinz.listeners;

import org.apache.log4j.Logger;

import at.projectlinz.controls.Control;
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
		while (sample[0] == 0f && !control.getExecuter().isShutdown()) {
			touth.fetchSample(sample, 0);
		}
		log.info("value after sampling: " + sample[0]);
		this.control.getExecuter().shutdown();
	}

	@Override
	public void setSensor(Object sensor) {
		this.touth = (EV3TouchSensor) sensor;
	}

	@Override
	public void registerControl(Control control) {
		this.control = control;
	}

}
