package atm;

public class PutCashCommand implements Command {
	private ATM atm;
	
	public PutCashCommand(ATM atm) {
		this.atm = atm;
	}
	
	public void execute() {
		atm.putCashForAuthorizedUser();
	}
}