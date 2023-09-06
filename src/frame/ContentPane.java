package frame;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import frame.grid.Grid;
import frame.options.OptionsPane;

public class ContentPane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private OptionsPane optionsPane = null;
	private Grid grid = null;

	public ContentPane() {
		setLayout(new BorderLayout());
		optionsPane = new OptionsPane();
		grid = new Grid();
		add(grid, BorderLayout.CENTER);
		add(optionsPane, BorderLayout.SOUTH);
	}
	
	public OptionsPane getOptionsPane() {
		return optionsPane;
	}
	
	public Grid getGrid() {
		return grid;
	}
}
