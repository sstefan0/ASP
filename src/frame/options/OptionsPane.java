package frame.options;

import java.awt.Color;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;

import listeners.CustomActionListener;

public class OptionsPane extends JPanel {

	private static final long serialVersionUID = 1L;

	private JToggleButton startPointButton = null;
	private JToggleButton endPointButton = null;
	private JButton findPathButton = null;
	private JButton resetButton = null;
	private JToggleButton setWallsButton = null;
	private ActionListener actionListener = null;
	private ButtonGroup buttonGroup = null;
	
	public OptionsPane() {
		
		actionListener = new CustomActionListener();
		
		startPointButton = new JToggleButton("Set Starting Point");
		endPointButton = new JToggleButton("Set Ending point");
		findPathButton = new JButton("Find Path");
		resetButton = new JButton("Reset");
		setWallsButton = new JToggleButton("Add Walls");
		setWallsButton.setSelected(true);
		
		startPointButton.setActionCommand("startPoint");
		endPointButton.setActionCommand("endPoint");
		setWallsButton.setActionCommand("setWalls");
		findPathButton.setActionCommand("findPath");
		resetButton.setActionCommand("reset");
		
		resetButton.addActionListener(actionListener);
		findPathButton.addActionListener(actionListener);
		
		resetButton.setForeground(Color.red);
		findPathButton.setBackground(Color.green);
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(startPointButton);
		buttonGroup.add(endPointButton);
		buttonGroup.add(setWallsButton);
		
		add(setWallsButton);
		add(startPointButton);
		add(endPointButton);
		add(new JSeparator(SwingConstants.VERTICAL));
		add(findPathButton);
		add(resetButton);
	}
	
	public String getActiveButton() {
		ButtonModel selectedButtonModel = (ButtonModel) buttonGroup.getSelection();
		String selectedButton = selectedButtonModel.getActionCommand();
		return selectedButton;
	}
	public void resetButtons() {
		setWallsButton.setSelected(true);
	}
}
