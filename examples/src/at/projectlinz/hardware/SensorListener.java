package at.projectlinz.hardware;

import at.projectlinz.motorhandler.Control;

public interface SensorListener {

	public void onSensorSampling();

	public void registerRobotControl(Control control);

	public void setSensor(Object sensor);

}
