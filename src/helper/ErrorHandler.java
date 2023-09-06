package helper;

import javax.swing.JOptionPane;

public class ErrorHandler {

	public static void displayErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Error!", JOptionPane.ERROR_MESSAGE);
	}
}
