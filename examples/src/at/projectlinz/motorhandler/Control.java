package at.projectlinz.motorhandler;

import java.util.concurrent.ExecutorService;

import at.projectlinz.hardware.IMotor;

public class Control {

	private ExecutorService e;
	private IMotor[] m;

	public Control(IMotor[] motors, ExecutorService service) {
		this.m = motors;
		this.e = service;
	}

	public void shutdown() {
		for (IMotor motor : m) {
			motor.stop();
		}
		if (!this.e.isShutdown())
			this.e.shutdown();
	}

}
