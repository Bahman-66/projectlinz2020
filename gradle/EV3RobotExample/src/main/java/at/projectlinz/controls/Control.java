package at.projectlinz.controls;

import java.util.Map;

import at.projectlinz.hardware.IMotor;
import at.projectlinz.listeners.events.Event;
import at.projectlinz.motorhandler.MotorHandler;

public class Control {

	private MotorHandler handler;

	public Control(MotorHandler motorHandler) {
		this.handler = motorHandler;
	}

	public Map<String, IMotor> getMotors() {
		return this.handler.getRobot().getMotors();
	}

	public void sendEvent(Event event) {
		try {
			handler.updateMotor(event);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sampling() {
		handler.executeSampling();
	}

	public void moveForward() {
		handler.activateMotorsForward();
	}

	public void navigate() {
		handler.navigate();
		
	}

	public void stop() {
		handler.stopMotor();
		
	}
}
