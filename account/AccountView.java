
import javax.swing.*;
import java.awt.event.*;
import java.util.*;


public class AccountView extends View {

	private JLabel balanceLabel = new JLabel("       0.00");
	private JTextField amountField = new JTextField(10);

	public AccountView(Account a) {
		super(a);
		title = "Account Viewer";
		add(new JLabel("balance = $"));
		add(balanceLabel);
		add(new JLabel("deposit = $"));
		add(amountField);
		amountField.addActionListener(this);
	}

	public void update(Observable subject, Object msg) {
		balanceLabel.setText("" + ((Account)model).getBalance());
		repaint();
	}

	public void actionPerformed(ActionEvent ae) {
		double d = new Double(amountField.getText());
		Command cmmd = new DepositCommand((Account)model, d);
		cp.execute(cmmd);
		amountField.setText("");
	}

	public static void main(String[] args) {
		Account acct = new Account();
		AccountView ui = new AccountView(acct);
		ui.display();
	}

}