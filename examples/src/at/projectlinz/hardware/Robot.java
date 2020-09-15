package at.projectlinz.hardware;

import java.util.HashMap;
import java.util.Map;

public class Robot {
	Map<Sensor, Object> sensors;
	Map<String, IMotor> motors;

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

	public void addMotors(String key, IMotor motor) {
		motors.put(key, motor);
	}

	public IMotor getMotor(String key) {
		return motors.get(key);
	}

	public int numberOfMotors() {
		return motors.size();
	}

	public Map<String, IMotor> getMotors() {
		return motors;
	}

}
