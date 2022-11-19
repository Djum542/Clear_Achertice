package account;
import MVC.Command;

class DepositCommand extends Command {
	private double amount;
	public DepositCommand(Account a, double amt) {
		super(a);
		amount = amt;
		undoable = true;
	}
	public void execute() throws Exception {
		((Account)model).deposit(amount);
		super.execute();
	}
}
