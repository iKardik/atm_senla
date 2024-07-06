package atm;

public class UnauthorizatedCommand implements Command {
	private ATM atm;
	
	public UnauthorizatedCommand(ATM atm) {
		this.atm = atm;
	}
	
	public void execute() {
		atm.unathorized();
	}
}