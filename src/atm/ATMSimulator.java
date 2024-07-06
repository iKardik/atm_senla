package atm;

import java.util.Scanner;

public class ATMSimulator {
	
	public static void main(String[] args) {
		Menu menu = new Menu();
		String command = "";
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println("Желаете воспользоваться банкоматом? (для продолжения введите \"y\")");
				command = scanner.nextLine();
			if ("y".equals(command)) {
				menu.runMenu(scanner);
			} else {
				System.out.println("Работа с банкоматом завершена.");
				break;
			}	
			}
		}
	}
}