package at.projectlinz.hardware;


public interface IMotor {

	public void forward();

	public void stop();
	
	public void setSpeed(int speed);

	public void backward();

}
