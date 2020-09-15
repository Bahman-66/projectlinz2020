package at.projectlinz.statemachine;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.listener.StateMachineListener;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

@Configuration
public class RobotStateMachine {
	

	StateMachine<String, String> stateMachine;
	
	public StateMachine<String, String> getStateMachine() {
		return stateMachine;
	}

	public void setStateMachine(StateMachine<String, String> stateMachine) {
		this.stateMachine = stateMachine;
	}

	public void signalStateMachine() {
		stateMachine.sendEvent(Events.Forward.toString());
	}
	
	@Bean
	public Action<String, String> forwardMotor(){
		return new Action<String,String>(){

			@Override
			public void execute(StateContext<String, String> context) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	@Bean
	public Action<String, String> stopMotor(){
		return new Action<String,String>(){

			@Override
			public void execute(StateContext<String, String> context) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	@Bean
	public Action<String, String> orientRobot(){
		return new Action<String,String>(){

			@Override
			public void execute(StateContext<String, String> context) {
				// TODO Auto-generated method stub
				
			}
			
		};
	}
	
	@Bean
    public StateMachineListener<String, String> listener() {
        return new StateMachineListenerAdapter<String, String>() {
            @Override
            public void stateChanged(State<String, String> from, State<String, String> to) {
                System.out.println("State change to " + to.getId());

            }
        };
    }
}
