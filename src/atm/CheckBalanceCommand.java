package atm;

public class CheckBalanceCommand implements Command {
	private ATM atm;
	
	public CheckBalanceCommand(ATM atm) {
		this.atm = atm;
	}
	
	public void execute() {
		atm.checkBalanceForAuthorizedUser();
	}

}
