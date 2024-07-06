package atm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class ATM {
	
	private Bank bank;
	private Scanner scanner = new Scanner(System.in);
	private long cashInATM;
	private boolean isUserAuthorized;
	
	public ATM(Bank bank) {
		this.bank = bank;
		this.initializeATM();
		this.isUserAuthorized = false;
	}
	
	private void checkBalance() {
		System.out.printf("\nБаланс Вашего счета: %s руб.\n", this.bank.getAccountBalance());
	}
	
	public void checkBalanceForAuthorizedUser() {
		if (this.isUserAuthorized) {
			this.checkBalance();
		} else {
			System.out.println("Отказано. Авторизируйтесь.");
		}
	}
	
	private void takeCash() {
		try {
			System.out.println("Введите сумму для снятия:");
			int cash = Integer.parseInt(scanner.nextLine());
			if (cash > this.cashInATM) {
				System.out.println("Недостаточно средств в банкомате.");
			} else if (cash > 0) {
				 byte takeCash = this.bank.takeAccountCash(cash);
				 if(takeCash == -1) {
					 this.cashInATM -= cash;
					 this.writeCashInATMToFile();
					 System.out.printf("Заберите деньги: %s руб.\n", cash);
				 } else if(takeCash == -2) {
					 System.out.println("Недостаточно средств на счете.");
				 } else {
					 System.out.println("Неизвестная ошибка.");
				 }
			} else {
				System.out.println("Cумма не может быть отрицательной или равной нолю.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Не верный ввод суммы.");
		}
	}
	
	public void takeCashForAuthorizedUser() {
		if (this.isUserAuthorized) {
			this.takeCash();
		} else {
			System.out.println("Отказано. Авторизируйтесь.");
		}
	}
	
	private void putCash() {
		try {
			System.out.println("Введите сумму пополнения (максимальная сумма 1 000 000):");
			int cash = Integer.parseInt(scanner.nextLine());
			if(cash > 0) {
				int putCash = this.bank.putCashToAccount(cash);
				if(putCash == -1) {
					this.cashInATM += cash;
					this.writeCashInATMToFile();
					System.out.printf("Средства в размере %s руб зачислены на счет.\n", cash);
				} else if (putCash == -2) {
					System.out.println("Сумма превышает лимит пополнения. Средства не зачислены.");
				}
			} else {
				System.out.println("Cумма не может быть отрицательной или равной нолю.");
			}
		} catch (NumberFormatException e) {
			System.out.println("Не верный ввод суммы.");
		}
	}
	
	public void putCashForAuthorizedUser() {
		if (this.isUserAuthorized) {
			this.putCash();
		} else {
			System.out.println("Отказано. Авторизируйтесь.");
		}
	}
	
	public void authorizate() {
		if (!this.isUserAuthorized) {
			System.out.println("Введите номер банковской карты в формате \"ХХХХ-ХХХХ-ХХХХ-ХХХХ\"");
			byte status = this.bank.checkAccount(scanner.nextLine());
			if(status == -1) {
				System.out.println("Введите ПИН-код (q для отмены)");
				byte attempt = 3;
				while(attempt > 0) {
					String pin = scanner.nextLine();
					if (pin.equals("q")) {
						break;
					}
					status = this.bank.checkPin(pin);
					if(status == -1) {
						this.isUserAuthorized = true;
						break;
					} else if (status == -2) {
						System.out.println("Неверный ПИН-код.");
					} else if (status == -3) {
						System.out.println("Произошла блокировка карты.");
					} else {
						System.out.println("Неизвестная ошибка.");
					}
					attempt -= 1;
				}
			} else if (status == -2) {
				System.out.println("Карта заблокирована.");
			} else if (status == -3) {
				System.out.println("Номер карты не найден.");
			} else {
				System.out.println("Неизвестная ошибка.");
			}
		} else {
			System.out.println("Вы уже авторизированы!");
		}
	}
	
	public void unathorized() {
		this.isUserAuthorized = false;
		System.out.println("Вы успешно вышли из системы.");
	}
	
	public void listOfCommands() {
		System.out.println("--------------------------------");
		if (this.isUserAuthorized) {
			System.out.println("Вы авторизированы.");
		} else {
			System.out.println("Вы не авторизированы.");
		}
		System.out.println("Команды для совершения операций: \n" + 
		                   "1 - проверить баланс;\n" + 
				           "2 - снять наличные;\n" + 
		                   "3 - пополнить счет;\n" + 
				           "4 - авторизироваться;\n" + 
		                   "q - завершить работу;");
		System.out.println("--------------------------------\n");
	}
	
	private Path getPathToATMData() {
		return Paths.get("atm.txt");
	}
	
	private void initializeATM() {
		try (BufferedReader reader = Files.newBufferedReader(this.getPathToATMData())) {
			String line;
			while ((line = reader.readLine()) != null) {
				this.cashInATM = Long.parseLong(line);
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Банкомат не работает (отсутсвуют наличные денежные средства).");
		} catch (NoSuchFileException e) {
			System.out.println("Банкомат не работает (сведения о средствах отсутсвуют).");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeCashInATMToFile() {
		try (BufferedWriter writter = Files.newBufferedWriter(getPathToATMData())) {
			writter.write(Long.toString(this.cashInATM));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
