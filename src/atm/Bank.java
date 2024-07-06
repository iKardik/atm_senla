package atm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

public class Bank {

	final int MS_IN_DAY = 8400000;
	final int LIMIT_CASH = 1000000;

	private ArrayList<Account> accounts = new ArrayList<Account>();
	private Account bufferAccount;

	public Bank() {
		this.initializeAccounts();
	}

	private void initializeAccounts() {
		try (BufferedReader reader = Files.newBufferedReader(this.getPathToAccounts())) {
			String accountString;
			while ((accountString = reader.readLine()) != null) {
				this.accounts.add(new Account(accountString));
			}
		} catch (NoSuchFileException e) {
			System.out.println("Услуги банка не доступны (база клиентов отсутсвует).");
		} catch (ArrayIndexOutOfBoundsException e) {
			System.out.println("Услуги банка не доступны (база клиентов пуста).");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}	

	public byte checkAccount(String cardNumber) {
		for (Account account : this.accounts) {
			if (cardNumber.equals(account.getCardNumber())) {
				account = this.unlockAccount(account);
				if (!account.isBlocked()) {
					this.bufferAccount = account;
					return -1;
				}
				return -2;
			}
		}
		return -3;
	}

	public byte checkPin(String pin) {
		byte status = -1;
		if (this.bufferAccount.getPin().equals(pin)) {
			this.bufferAccount.setCntOfFailEnterPin((byte) 0);
			this.save();
			return status;
		}		
		this.bufferAccount.setCntOfFailEnterPin((byte) (this.bufferAccount.getCntOfFailEnterPin() + 1));
		status = -2;
		if (3 <= this.bufferAccount.getCntOfFailEnterPin()) {
			this.bufferAccount.setBlocked(true);
			this.bufferAccount.setLastBlockTime(this.currentTime());
			status = -3;
		}
		this.save();
		return status;
	}

	public long getAccountBalance() {
		return this.bufferAccount.getBalance();
	}

	public byte takeAccountCash(int cash) {
		long balance = this.bufferAccount.getBalance();
		if (cash <= balance) {
			this.bufferAccount.setBalance(balance - cash);
			this.save();
			return -1;
		}
		return -2;
	}

	public int putCashToAccount(int cash) {
		long balance = this.bufferAccount.getBalance();
		if (cash <= this.LIMIT_CASH) {
			this.bufferAccount.setBalance(balance + cash);
			this.save();
			return -1;
		}
		return -2;
	}
	
	private Account unlockAccount(Account account) {
		if ((this.currentTime() - account.getLastBlockTime()) >= this.MS_IN_DAY) {
			account.setBlocked(false);
			account.setCntOfFailEnterPin((byte) 0);
		}
		return account;
	}
	
	private void save() {
		this.saveTransaction();
		this.writeAccountsToFile();
	}
	
	private void saveTransaction() {
		int index = this.accounts.indexOf(bufferAccount);
		this.accounts.set(index, this.bufferAccount);
	}
	
	private void writeAccountsToFile() {
		try (BufferedWriter writter = Files.newBufferedWriter(getPathToAccounts())) {
			for (Account account : this.accounts) {
				writter.write(account.toString() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Path getPathToAccounts() {
		return Paths.get("accounts.txt");
	}
	
	private Long currentTime() {
		return new Date().getTime();
	}
}
