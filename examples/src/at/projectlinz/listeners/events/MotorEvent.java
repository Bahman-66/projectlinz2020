package at.projectlinz.listeners.events;

public class MotorEvent extends Event {
	
	private MoveDirection direction;

	public MotorEvent(Object source) {
		super(source);
	}

	public MoveDirection getDirection() {
		return direction;
	}

	public void setDirection(MoveDirection direction) {
		this.direction = direction;
	}	

}
