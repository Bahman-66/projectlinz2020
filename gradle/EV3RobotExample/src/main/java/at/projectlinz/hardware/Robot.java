package at.projectlinz.hardware;

import java.util.HashMap;
import java.util.Map;

import at.projectlinz.hardware.BigMotor.MotorPos;
import at.projectlinz.listeners.SensorListener.Sensor;

public class Robot {
	Map<Sensor, Object> sensors;
	Map<MotorPos, IMotor> motors;

	public Robot() {
		sensors = new HashMap<>();
		motors = new HashMap<>();
	}

	public void addSensors(Sensor key, Object obj) {
		sensors.put(key, obj);
	}

	public Object getSensor(Sensor key) {
		return sensors.get(key);
	}

	public void addMotors(MotorPos key, IMotor motor) {
		motors.put(key, motor);
	}

	public IMotor getMotor(MotorPos key) {
		return motors.get(key);
	}

	public int numberOfMotors() {
		return motors.size();
	}

	public Map<MotorPos, IMotor> getMotors() {
		return motors;
	}

}
