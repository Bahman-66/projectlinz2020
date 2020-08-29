package at.projectlinz.controls;

import java.util.concurrent.ExecutorService;

import at.projectlinz.hardware.IMotor;

public class Control {

	private ExecutorService e;
	private IMotor[] m;

	public Control(IMotor[] motors, ExecutorService service) {
		this.m = motors;
		this.e = service;
	}

	public ExecutorService getExecuter() {
		return e;
	}

	public IMotor[] getMotors() {
		return m;
	}

}
