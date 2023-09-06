package listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import frame.ContentPane;
import frame.GraphCell;
import frame.options.OptionsPane;

public class GridCellMouseListener implements MouseListener{

	@Override
	public void mouseClicked(MouseEvent e) {
		GraphCell source = (GraphCell)e.getSource();
		ContentPane contentPane = (ContentPane) source.getParent().getParent();
		OptionsPane optionsPane = contentPane.getOptionsPane();
		
		switch(optionsPane.getActiveButton()) {
		case "setWalls":
			source.changeState();
			break;
		case "startPoint":
			source.setStartingPoint();
			optionsPane.resetButtons();
			break;
		case "endPoint":
			source.setEndingPoint();
			optionsPane.resetButtons();
			break;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
