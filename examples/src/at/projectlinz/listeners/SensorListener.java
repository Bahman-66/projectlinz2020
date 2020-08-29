package at.projectlinz.listeners;

import at.projectlinz.events.Control;

public interface SensorListener {

	public void onSensorSampling();

	public void setSensor(Object sensor);

	public void registerControl(Control event);

}
