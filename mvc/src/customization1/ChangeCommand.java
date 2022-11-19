package customization1;

import framework.*;

public class ChangeCommand extends Command {
	public ChangeCommand(StopLight model) {
		super(model);
	}
	
	public void execute() throws AppException {
		super.execute();
		StopLight model = (StopLight)theModel;
		model.changeState();
	}
}
