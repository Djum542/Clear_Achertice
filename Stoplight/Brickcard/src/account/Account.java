package account;

import java.io.*;

import MVC.Memento;
import MVC.Model;

import java.awt.*;

public class Account extends Model {

	private double balance = 0.0;

	public double getBalance() { return balance; }

	public void deposit(double amt) throws Exception {
		if (amt <= 0) {
			throw new Exception("amount must be positive");
		}
		balance += amt;
		unsavedChanges = true;
		setChanged();
		notifyObservers();
	}

public abstract	class AccountMemento implements Memento {
		private double balance = 0.0;
		public double getBalance() { return balance; }
		public AccountMemento(double bal) {
			balance = bal;
		}
	}

	public Memento makeMemento() {
		return new AccountMemento(balance) {
			
		};
	}

	public void accept(Memento m) {
		balance = ((AccountMemento)m).getBalance();
	}

	


}