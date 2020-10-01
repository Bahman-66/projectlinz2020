package at.projectlinz.listeners;

import org.apache.log4j.Logger;

import at.projectlinz.listeners.events.SensorEvent;
import ev3dev.sensors.ev3.EV3TouchSensor;

public class TouchSensorListenner extends SensorListener {

	private Logger log = Logger.getLogger(TouchSensorListenner.class);
	private EV3TouchSensor touth;

	public TouchSensorListenner(Object sensor) {
		super(sensor);
	}

	@Override
	public void onSensorSampling() {

		if (getControl() == null) {
			throw new IllegalArgumentException("no controler is registered!");
		}

		if (touth == null) {
			throw new IllegalArgumentException("no sensor is seted!");
		}

		int sampleSize = touth.sampleSize();
		float[] sample = new float[sampleSize];
		log.info("value before sampling: " + sample[0]);
		while (sample[0] == 0f) {
			setSampling(true);
			touth.fetchSample(sample, 0);
		}
		setSampling(false);
		log.info("value after sampling: " + sample[0]);
		if (sample[0] == 1f) {
			getControl().sendEvent(new SensorEvent(TouchSensorListenner.this));
		}

	}

	@Override
	public void setSensor(Object sensor) {
		this.touth = (EV3TouchSensor) sensor;
	}

}
