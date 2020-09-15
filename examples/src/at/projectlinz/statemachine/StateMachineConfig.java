package at.projectlinz.statemachine;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;
import org.springframework.statemachine.uml.UmlStateMachineModelFactory;

@Configurable
@EnableStateMachine
public class StateMachineConfig extends StateMachineConfigurerAdapter<String, String> {


	@Override
	public void configure(StateMachineModelConfigurer<String, String> model) throws Exception {
		model.withModel().factory(modelFactory());
	}

	@Bean
	public StateMachineModelFactory<String, String> modelFactory() {
		UmlStateMachineModelFactory factory = new UmlStateMachineModelFactory(
				"file:..\\statemachine\\RobotBehaviorModel.uml");
		
		return factory;
	}
}