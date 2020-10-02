package at.projectlinz.listeners.events;

import at.projectlinz.controls.Control;
import at.projectlinz.listeners.ISensorListener;
import at.projectlinz.statemachine.RobotStateMachineConfig.Trigger;

public abstract class Event implements IEvent {
	private int type;
	private Object source;

	public Event(Object source ,Trigger t) {
		this.source = source;
		this.type = t.label;
	}

	public int getType() {
		return type;
	}

	@Override
	public Object getSource() {
		return this.source;
	}

	public Control getControl() {
		if (getSource() instanceof ISensorListener)
			return ((ISensorListener) getSource()).getControl();

		if (getSource() instanceof Control)
			return ((Control) getSource());

		return null;
	}

	public ISensorListener getListener() {
		if (getSource() instanceof ISensorListener)
			return ((ISensorListener) getSource());

		return null;
	}

}
