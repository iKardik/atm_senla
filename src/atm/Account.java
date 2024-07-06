package atm;

public class Account {

	private String cardNumber;
	private String pin;
	private long balance;
	private boolean isBlocked;
	private byte cntOfFailEnterPin;
	private long lastBlockTime;
	
	public Account(String accountString) {
		String[] accountData = accountString.split(" ");
		this.cardNumber = accountData[0];
		this.pin = accountData[1];
		this.balance = Long.parseLong(accountData[2]);
		this.isBlocked = Boolean.parseBoolean(accountData[3]);
		this.cntOfFailEnterPin = Byte.parseByte(accountData[4]);
		this.lastBlockTime = Long.parseLong(accountData[5]);
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public long getBalance() {
		return balance;
	}

	public void setBalance(long balance) {
		this.balance = balance;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public byte getCntOfFailEnterPin() {
		return cntOfFailEnterPin;
	}

	public void setCntOfFailEnterPin(byte cntOfFailEnterPin) {
		this.cntOfFailEnterPin = cntOfFailEnterPin;
	}

	public long getLastBlockTime() {
		return lastBlockTime;
	}

	public void setLastBlockTime(long lastBlockTime) {
		this.lastBlockTime = lastBlockTime;
	}
	
	public String toString() {
		return String.join(" ",
			this.getCardNumber(),
			this.getPin(),
			Long.toString(this.getBalance()),
			Boolean.toString(this.isBlocked()),
			Byte.toString(this.getCntOfFailEnterPin()),
			Long.toString(this.getLastBlockTime())
		);
	}
}
