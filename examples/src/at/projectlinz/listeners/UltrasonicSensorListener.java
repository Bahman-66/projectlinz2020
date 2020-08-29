package at.projectlinz.listeners;


import org.apache.log4j.Logger;

import at.projectlinz.events.Control;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class UltrasonicSensorListener implements SensorListener {
	private Logger log = Logger.getLogger(UltrasonicSensorListener.class);
	private float threshold = 0.1f;
	private Control control = null;
	private EV3UltrasonicSensor ulSensor;

	public UltrasonicSensorListener(Object sensor) {
		setSensor(sensor);
	}

	@Override
	public void onSensorSampling() {
		float[] sample = new float[ulSensor.sampleSize()];
		ulSensor.fetchSample(sample, 0);
		while (sample[0] > threshold && !this.control.getExecuter().isShutdown()) {
			ulSensor.fetchSample(sample, 0);
		}
		log.info("ulSesnsor stop at " + sample[0]);
		this.control.getExecuter().shutdown();
	}

	@Override
	public void registerControl(Control event) {
		this.control = event;
	}

	@Override
	public void setSensor(Object sensor) {
		this.ulSensor = (EV3UltrasonicSensor) sensor;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

}
