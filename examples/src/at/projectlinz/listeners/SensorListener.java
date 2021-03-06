package at.projectlinz.listeners;

import org.apache.log4j.Logger;

import at.projectlinz.controls.Control;

public abstract class SensorListener implements ISensorListener{
	@SuppressWarnings("unused")
	private Logger log = Logger.getLogger(SensorListener.class);
	private Control control = null;
	private boolean isSampling = false;
	
	public SensorListener(Object sensor) {
		setSensor(sensor);
	}

	@Override
	public void setControl(Control control) {
		this.control = control;
	}
	
	@Override
	public Control getControl() {
		return control;
	}

	@Override
	public boolean isSampling() {
		return isSampling;
	}

	@Override
	public void setSampling(boolean isSampling) {
		this.isSampling = isSampling;
	}

}
