package at.projectlinz.listeners.events;

import at.projectlinz.statemachine.RobotStateMachineConfig.Trigger;

public class MotorEvent extends Event {

	private MoveDirection direction;

	public MotorEvent(Object source, Trigger t) {
		super(source, t);
	}

	public MoveDirection getDirection() {
		return direction;
	}

	public void setDirection(MoveDirection direction) {
		this.direction = direction;
	}

}
