package atm;

import java.util.Scanner;

public class Menu {
	
	public void runMenu(Scanner scanner) {
		Bank bank = new Bank();
		ATM atm = new ATM(bank);
		RemoteControl remoteControl = new RemoteControl();
		Scanner inCommand = scanner;
		
		
		AuthorizationCommand authorizationCommand = new AuthorizationCommand(atm);
		CheckBalanceCommand checkBalanceCommand = new CheckBalanceCommand(atm);
		TakeCashCommand takeCashCommand = new TakeCashCommand(atm);
		PutCashCommand putCashCommand = new PutCashCommand(atm);
		UnauthorizatedCommand unauthorizatedCommand = new UnauthorizatedCommand(atm);
		
		String menuCommand = "";
		
		while(true) {
			atm.listOfCommands();
			menuCommand = inCommand.nextLine();
			if("q".equals(menuCommand)) {
				remoteControl.setCommand(unauthorizatedCommand);
				remoteControl.pressButton();
				System.out.println("Работа завершена. Заберите Вашу карту.");
				System.out.println("--------------------------------------\n");
				break;
			} else if ("1".equals(menuCommand)) {
				remoteControl.setCommand(checkBalanceCommand);
				remoteControl.pressButton();
			} else if ("2".equals(menuCommand)) {
				remoteControl.setCommand(takeCashCommand);
				remoteControl.pressButton();
			} else if ("3".equals(menuCommand)) {
				remoteControl.setCommand(putCashCommand);
				remoteControl.pressButton();
			} else if ("4".equals(menuCommand)){
				remoteControl.setCommand(authorizationCommand);
				remoteControl.pressButton();
			} else {
				System.out.println("Неизвестная команда");
			}
		}		
	}
}
