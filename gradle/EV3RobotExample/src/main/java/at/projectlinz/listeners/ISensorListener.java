package at.projectlinz.listeners;

import at.projectlinz.controls.Control;

public interface ISensorListener {

	public void onSensorSampling();

	public void setSensor(Object sensor);

	void setControl(Control control);

	public Control getControl();

	public boolean isSampling();

	void setSampling(boolean isSampling);

	public String getSampleValue();

}
