package at.projectlinz.statemachine;

import org.apache.log4j.Logger;

import com.github.oxo42.stateless4j.StateMachine;
import com.github.oxo42.stateless4j.StateMachineConfig;
import com.github.oxo42.stateless4j.delegates.Action;

import at.projectlinz.controls.Control;

public class RobotStateMachineConfig {

	private static Logger log = Logger.getLogger(RobotStateMachineConfig.class);
	private static Control control;

	public enum State {
		IDLE, MOVING, NAVIGATING, RESETING, STOPING
	}

	public enum Trigger {
		FORWARD(0), BLOCK(1), STOP(2), RESET(3);

		public final int label;

		private Trigger(int i) {
			this.label = i;
		}
	}

	public static StateMachine<State, Trigger> getRobotStateMachine(Control c) {
		control = c;
		StateMachineConfig<State, Trigger> config = new StateMachineConfig<>();
		Command doIdle = new Command() {

			@Override
			public void execute() {
				log.info("do IDLE");
			}
		};

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
				control.stop();
			}
		};
		
		Command navigate = new Command() {

			@Override
			public void execute() {
				log.info("do Stop");
				control.navigate();
			}
		};

		config.enableEntryActionOfInitialState();

		config.configure(State.IDLE)
				.onEntry(createAction(doIdle))
				.onExit(createAction(move))
				.permit(Trigger.FORWARD,State.MOVING, createAction(sample));
		
		config.configure(State.MOVING)
			.onExit(createAction(stop))
			.permit(Trigger.BLOCK, State.NAVIGATING,createAction(navigate));

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
