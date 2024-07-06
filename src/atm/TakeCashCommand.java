package atm;

public class TakeCashCommand implements Command{
	private ATM atm;
	
	public TakeCashCommand(ATM atm) {
		this.atm = atm;
	}
	
	public void execute() {
		atm.takeCashForAuthorizedUser();
	}
}
