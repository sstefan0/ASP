package frame;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public MainFrame() {
		
		setSize(new Dimension(865, 800));
		setMinimumSize(new Dimension(865, 800));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("ASP projekat");
		setLocationRelativeTo(null);
		setIconImage(toolkit.getImage("icon/maze.png"));
		
		add(new ContentPane());
		
		setVisible(true);
	}
}
