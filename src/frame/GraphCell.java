package frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import frame.grid.Grid;
import helper.ErrorHandler;
import listeners.GridCellMouseListener;

public class GraphCell extends JPanel {

	private static final long serialVersionUID = 1L;
	public boolean isWall;
	private int x;
	private int y;
	private JLabel label = null;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	public GraphCell(int i, int j) {
		setLayout(new BorderLayout());
		x = i;
		y = j;
		isWall = false;
		setMaximumSize(new Dimension(15, 15));
		setMinimumSize(new Dimension(15, 15));
		setPreferredSize(new Dimension(15, 15));
		setSize(new Dimension(15, 15));
		setBackground(Color.white);
		setBorder(BorderFactory.createLineBorder(Color.black));
		addMouseListener(new GridCellMouseListener());
		label = new JLabel("", SwingConstants.CENTER);
		label.setFont(new Font("Arial", Font.BOLD, 12));
		add(label);
	}

	public void changeState() {
		isWall = !isWall;
		Grid grid = (Grid) getParent();
		if(isWall) {
			setBackground(Color.black);
			GraphCell startingPoint = grid.getStartingPoint();
			GraphCell endingPoint = grid.getEndPoint();
			
			if(startingPoint != null && startingPoint.x == x && startingPoint.y == y) grid.setStartingPoint(-1, -1);
			else if(endingPoint != null && endingPoint.x == x && endingPoint.y == y) grid.setEndingPoint(-1, -1);
		}
		else
			setBackground(Color.white);
	}
	public void setStartingPoint() {
		Grid grid = (Grid) getParent();
		
		try {
			if(isWall) {
				throw new Exception("Can't start from a wall!");
			}
			
			if(grid.getEndPoint() == this) {
				throw new Exception("Can't start and finish on the same cell!");
			}
			
			setLabelIcon(new ImageIcon(toolkit.getImage("image/racing-car.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
			
			GraphCell oldStartingPoint = grid.getStartingPoint();
			if(oldStartingPoint != null && oldStartingPoint != this) {
				oldStartingPoint.setLabelIcon(null);
			}
			
			grid.setStartingPoint(x, y);
		} catch (Exception e) {
			ErrorHandler.displayErrorMessage(e.getMessage());
		}
	}
	
	public void setEndingPoint() {
		Grid grid = (Grid) getParent();
		try {
			if(isWall) {
				throw new Exception("Can't set finish on a wall!");
			}
			
			if(grid.getStartingPoint() == this) {
				throw new Exception("Can't start and finish on the same cell!");
			}
		
			setLabelIcon(new ImageIcon(toolkit.getImage("image/checkered-flag.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
			
			GraphCell oldEndPoint = grid.getEndPoint();
			if(oldEndPoint != null && oldEndPoint != this) {
				oldEndPoint.setLabelIcon(null);
			}
			
			grid.setEndingPoint(x, y);
		} catch (Exception e) {
			ErrorHandler.displayErrorMessage(e.getMessage());
		}
	}
	
	public int getNodeNumber() {
		Grid grid = (Grid) getParent();
		return this.x * grid.getGrid().length + y;
	}
	
	public void setLabelText(String text) {
		label.setText(text);
	}
	
	public void setLabelIcon(ImageIcon icon) {
		label.setIcon(icon);
	}
	
	public ImageIcon getLabelIcon() {
		return (ImageIcon)label.getIcon();
	}
}
