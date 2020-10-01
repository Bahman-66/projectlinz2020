package at.projectlinz.statemachine;

import org.apache.log4j.Logger;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;



public class RobotStateMachineConfig {
	
	private static Logger log = Logger.getLogger(RobotStateMachineConfig.class);

	
	public enum State{
		IDLE,
		MOVING,
		NAVIGATING,
		RESETING,
		STOPING
	}
	
	public enum Trigger{
		FORWARD,
		BLOCK,
		STOP,
		RESET
	}
	
	public static  StateMachine<State, Trigger> getRobotStateMachine() {
		StateMachineConfig<State, Trigger> config = new StateMachineConfig<>();
		Command doIdle = new Command() {
			
			@Override
			public void execute() {
				log.info("do IDLE");
				System.out.println("do IDLE");
			}
		};
		
		Command move = new Command() {
			
			@Override
			public void execute() {
				log.info("do MOVE");
				System.out.println("do MOVE");
			}
		}; 
		
		Command sample = new Command() {
			
			@Override
			public void execute() {
				log.info("do SAMPLE");
				System.out.println("do SAMPLE");
			}
		};
		
		
		config.configure(State.IDLE)
			.onEntry(createAction(doIdle))
			.onExit(createAction(move))
			.permit(Trigger.FORWARD, State.MOVING,createAction(sample));
		
		return new StateMachine<>(State.IDLE, config);
	}
	
	
	private static Action createAction(final Command command) {
		return new Action() {
			
			@Override
			public void doIt() {
				command.execute();
				
			}
		};
	}

}
