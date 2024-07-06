package atm;

public class AuthorizationCommand implements Command {
	private ATM atm;
	
	public AuthorizationCommand(ATM atm) {
		this.atm = atm;
	}
	
	public void execute() {
		atm.authorizate();
	}
}
