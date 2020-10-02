package at.projectlinz.statemachine;

import org.apache.log4j.Logger;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;

import at.projectlinz.controls.Control;
import at.projectlinz.listeners.events.MotorEvent;

public class RobotStateMachineConfig {

	private static Logger log = Logger.getLogger(RobotStateMachineConfig.class);
	private static Control control;

	public enum State {
		IDLE, MOVING, NAVIGATING, RESETING, STOPING
	}

	public enum Trigger {
		FORWARD(0), BLOCK(1), STOP(2), RESET(3) , READY(4);

		public final int label;

		private Trigger(int i) {
			this.label = i;
		}
	}

	public static StateMachine<State, Trigger> getRobotStateMachine(Control c) {
		control = c;
		StateMachineConfig<State, Trigger> config = new StateMachineConfig<>();
		
		Command move = new Command() {

			@Override
			public void execute() {
				log.info("do MOVE");
				control.moveForward();
			}
		};

		Command sample = new Command() {

			@Override
			public void execute() {
				log.info("do SAMPLE");
				control.sampling();
			}
		};
		
		Command stop = new Command() {

			@Override
			public void execute() {
				log.info("do Stop");
				control.stopMotor();
			}
		};
		
		Command navigate = new Command() {

			@Override
			public void execute() {
				log.info("do Navigate");
				control.navigate();
				MotorEvent e = new MotorEvent(null, Trigger.RESET);
				control.sendEvent(e);
			}
		};
		
		Command shutdown = new Command() {

			@Override
			public void execute() {
				log.info("do shutdown");
				System.exit(0);
			}
		};
		
		Command reset = new Command() {

			@Override
			public void execute() {
				log.info("do reset");
				control.stopMotor();
				MotorEvent e = new MotorEvent(this , Trigger.READY);
				control.sendEvent(e);
			}
		};
		
		Command newStart = new Command() {

			@Override
			public void execute() {
				log.info("do newStart");
				MotorEvent e = new MotorEvent(this , Trigger.FORWARD);
				control.sendEvent(e);
			}
		};
		
		config.disableEntryActionOfInitialState();
		
		config.configure(State.IDLE)
				.onEntry(createAction(newStart))
				.permit(Trigger.FORWARD,State.MOVING, createAction(sample));
		
		config.configure(State.MOVING)
			.onEntry( createAction(move))
			.onExit(createAction(stop))
			.permit(Trigger.BLOCK, State.NAVIGATING);
			
		
		config.configure(State.NAVIGATING)
		.onEntry(createAction(navigate))
		.permit(Trigger.RESET, State.RESETING);
		
		config.configure(State.RESETING)
		.onEntry(createAction(reset))
		.permit(Trigger.READY, State.IDLE );
		
		// STOP
		config.configure(State.MOVING)
		.onExit(createAction(stop))
		.permit(Trigger.STOP, State.STOPING,createAction(shutdown));
		
		config.configure(State.IDLE)
		.onExit(createAction(stop))
		.permit(Trigger.STOP, State.STOPING,createAction(shutdown));
		
		config.configure(State.NAVIGATING)
		.onExit(createAction(stop))
		.permit(Trigger.STOP, State.STOPING,createAction(shutdown));
		
		config.configure(State.RESETING)
		.onExit(createAction(stop))
		.permit(Trigger.STOP, State.STOPING,createAction(shutdown));
		

		return new StateMachine<State, Trigger>(State.IDLE, config);
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
