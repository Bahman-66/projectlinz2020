package at.projectlinz.app;

import org.apache.log4j.Logger;

import com.github.oxo42.stateless4j.StateMachine;


import at.projectlinz.statemachine.RobotStateMachineConfig;
import at.projectlinz.statemachine.RobotStateMachineConfig.State;
import at.projectlinz.statemachine.RobotStateMachineConfig.Trigger;


public class App {

	private static Logger log = Logger.getLogger(App.class);

	public static void main(String[] args) {
		log.info("start app!");
		try {
			
			StateMachine<State , Trigger> fsm = RobotStateMachineConfig.getRobotStateMachine();
			fsm.fireInitialTransition();
			fsm.fire(Trigger.FORWARD);
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("ends app!");
	}

}
