package at.projectlinz.listeners;

import org.apache.log4j.Logger;

import at.projectlinz.listeners.events.MotorEvent;
import at.projectlinz.listeners.events.MoveDirection;
import lejos.hardware.sensor.EV3UltrasonicSensor;

public class UltrasonicSensorListener extends SensorListener {
	private Logger log = Logger.getLogger(UltrasonicSensorListener.class);
	private float threshold = 0.15f;
	private EV3UltrasonicSensor ulSensor;
	private MoveDirection lastMove = MoveDirection.FORWARD;

	public UltrasonicSensorListener(Object sensor) {
		super(sensor);
	}

	@Override
	public void onSensorSampling() {
		if (getControl() == null) {
			throw new IllegalArgumentException("no controler is registered!");
		}

		if (ulSensor == null) {
			throw new IllegalArgumentException("no sensor is seted!");
		}

		float[] sample = new float[ulSensor.sampleSize()];
		ulSensor.fetchSample(sample, 0);
		log.info("ulSesnsor start at " + sample[0]);
		while (sample[0] > threshold) {
			setSampling(true);
			ulSensor.fetchSample(sample, 0);
		}
		setSampling(false);
		log.info("ulSesnsor stop at " + sample[0]);
		if (sample[0] < threshold) {
			dispatchEvent();
		}
		log.info("done");

	}

	@Override
	public void setSensor(Object sensor) {
		this.ulSensor = (EV3UltrasonicSensor) sensor;
	}

	public void setThreshold(float threshold) {
		this.threshold = threshold;
	}

	private void dispatchEvent() {
		MotorEvent event = new MotorEvent(this);
		switch (lastMove) {
		case FORWARD:
			event.setDirection(MoveDirection.RIGHT);
			lastMove = MoveDirection.RIGHT;
			getControl().sendEvent(event);
			break;
		case RIGHT:
			event.setDirection(MoveDirection.LEFT);
			lastMove = MoveDirection.LEFT;
			getControl().sendEvent(event);
			break;
		case LEFT:
			event.setDirection(MoveDirection.FORWARD);
			lastMove = MoveDirection.FORWARD;
			getControl().sendEvent(event);
			break;

		default:
			break;
		}

	}

}
