package listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import frame.ContentPane;
import frame.grid.Grid;

public class CustomActionListener implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		ContentPane contentPane = (ContentPane) source.getParent().getParent();
		Grid grid = contentPane.getGrid();
		
		switch(e.getActionCommand()) {
		case "reset":
			grid.resetGrid();
			break;
		case "findPath":
			grid.dijkstra();
			break;
		}
	}

}
