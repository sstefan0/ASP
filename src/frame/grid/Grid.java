package frame.grid;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import frame.GraphCell;
import helper.ErrorHandler;

public class Grid extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int startingPointX = -1;
	private int startingPointY = -1;
	private int endPointX = -1;
	private int endPointY = -1;
	private GraphCell[][] grid = null;
	private List<Integer> lastPath = null;
	
	public Grid() {
		setLayout(new GridLayout(36, 36));
		grid = new GraphCell[36][36];
		for(int i=0; i<36; i++) {
			for(int j=0; j<36; j++) {
				grid[i][j] = new GraphCell(i, j);
				add(grid[i][j]);
			}
		}
	}
	
	public GraphCell[][] getGrid(){
		return grid;
	}
	
	public void setStartingPoint(int i, int j) {
		if(i == -1 && j == -1 && startingPointX != -1 && startingPointY != -1) {
			grid[startingPointX][startingPointY].setLabelIcon(null);
		}
		
		startingPointX = i;
		startingPointY = j;
	}
	
	public void setEndingPoint(int i, int j) {
		if(i == -1 && j == -1 && endPointX != -1 && endPointY != -1) {
			grid[endPointX][endPointY].setLabelIcon(null);
		}
		
		endPointX = i;
		endPointY = j;
	}

	public GraphCell getStartingPoint() {
		if(startingPointX == -1 || startingPointY == -1) {
			return null;
		}
		
		return grid[startingPointX][startingPointY];
	}
	
	public GraphCell getEndPoint() {
		if(endPointX == -1 || endPointY == -1) {
			return null;
		}
		
		return grid[endPointX][endPointY];
	}
	
	public void resetGrid() {
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[i].length; j++) {
				if(grid[i][j].isWall) {
					grid[i][j].changeState();
				}
			}
		}
		
		setStartingPoint(-1, -1);
		setEndingPoint(-1, -1);
		if(lastPath != null) {
			clearLastPath();
		}
	}
	
	private int[][] formAdjacencyMatrix(){
		int[][] adjacencyMatrix = new int[grid.length*grid.length][grid.length*grid.length];
		for(int i=0; i<adjacencyMatrix.length; i++) {
			for(int j=0; j<adjacencyMatrix.length; j++) {
				if(i == j) adjacencyMatrix[i][j] = 0;
				else adjacencyMatrix[i][j] = Integer.MAX_VALUE;
			}
		}
		
		for(int i=0; i<grid.length; i++) {
			for(int j=0; j<grid[i].length; j++) {
				if(j+1 < grid.length && !grid[i][j+1].isWall) {
					adjacencyMatrix[i*grid[i].length + j][i*grid[i].length + j + 1] = 1;
				}
				if(i+1 < grid.length && !grid[i+1][j].isWall) {
					adjacencyMatrix[i*grid[i].length + j][(i+1)*grid[i].length + j] = 1;
				}
				if(j-1 >= 0 && !grid[i][j-1].isWall) {
					adjacencyMatrix[i*grid[i].length + j][i*grid[i].length + j -1] = 1;
				}
				if(i-1 >= 0 && !grid[i-1][j].isWall) {
					adjacencyMatrix[i*grid[i].length + j][(i-1)*grid[i].length + j] = 1;

				}
			}
		}
		
		return adjacencyMatrix;
	}

	public void dijkstra() {
		if(lastPath != null) {
			clearLastPath();
		}
		
		try {
			int[][] grid = formAdjacencyMatrix();

			
			int source = getStartingPoint().getNodeNumber();
			int end = getEndPoint().getNodeNumber();
			
			int[] t = new int[grid.length];
			int[] d = new int[grid.length];
			boolean[] visited = new boolean[grid.length];
			
			for(int i=0; i<visited.length; i++) {
				if(i == source) {
					visited[i] = true;
				}
				else {
					visited[i] = false;
				}
				
			}
			
			for(int i=0; i<grid.length; i++) {
				d[i] = grid[source][i];
				if(i != source && grid[source][i] < Integer.MAX_VALUE) {
					t[i] = source;
				}
				else {
					t[i] = Integer.MAX_VALUE;
				}
			}
			
			int n = d.length;
			
			while(n > 1) {
				int min = Integer.MAX_VALUE;
				int index = -1;
				for(int i=0; i<d.length; i++) {
					if(!visited[i] && d[i] <= min) {
						min = d[i];
						index = i;
					}
				}
				visited[index] = true;
				n--;
				
				for(int i=0; i<d.length; i++) {
					if(!visited[i] && (d[index] != Integer.MAX_VALUE && grid[index][i] != Integer.MAX_VALUE &&  d[index] + grid[index][i] < d[i])) {
						d[i] = d[index] + grid[index][i];
						t[i] = index;
					}
				}
				
			}
			
			lastPath = new ArrayList<Integer>();
			recreatePath(t, end);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			ErrorHandler.displayErrorMessage("Starting and ending points are mandatory!");
		}
	}
	
	private void recreatePath(int[] t, int target) {
		if(t[target] == this.grid[startingPointX][startingPointY].getNodeNumber()) {
			
			ImageIcon originalImage = new ImageIcon(Toolkit.getDefaultToolkit().getImage("image/racing-car.png").getScaledInstance(20, 20, Image.SCALE_SMOOTH));
			ImageIcon rotatedIcon = null;
			
			switch(target-t[target]){
			case -1:
				rotatedIcon = rotateImage(originalImage.getImage(), Math.PI);
				break;
			case 1:
				rotatedIcon = originalImage;
				break;
			case 36:
				rotatedIcon = rotateImage(originalImage.getImage(), Math.PI/2);
				break;
			case -36:
				rotatedIcon = rotateImage(originalImage.getImage(), -Math.PI/2);
				break;
			}
				
			grid[startingPointX][startingPointY].setLabelIcon(rotatedIcon);		
			return;
		}
		
		if(t[target] == Integer.MAX_VALUE) {
			JOptionPane.showMessageDialog(null, "Path not found", "Path not found", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		switch(target-t[target]){
		case -1:
			this.grid[t[target]/grid.length][t[target]%grid.length].setLabelText("˂");
			break;
		case 1:
			this.grid[t[target]/grid.length][t[target]%grid.length].setLabelText("˃");
			break;
		case 36:
			this.grid[t[target]/grid.length][t[target]%grid.length].setLabelText("˅");
			break;
		case -36:
			this.grid[t[target]/grid.length][t[target]%grid.length].setLabelText("˄"); 
			break;
		}
		
		this.grid[t[target]/grid.length][t[target]%grid.length].setBackground(new Color(122, 171, 245));
		this.grid[t[target]/grid.length][t[target]%grid.length].setBorder(BorderFactory.createSoftBevelBorder(BevelBorder.LOWERED));
		lastPath.add(t[target]);
		recreatePath(t, t[target]);
	}
	
	private void clearLastPath() {
		for(Integer node : lastPath) {
			this.grid[node/grid.length][node%grid.length].setLabelText("");
			this.grid[node/grid.length][node%grid.length].setBorder(BorderFactory.createLineBorder(Color.black));
			
			if(this.grid[node/grid.length][node%grid.length].isWall) {
				this.grid[node/grid.length][node%grid.length].setBackground(Color.black);
			}
			else {
				this.grid[node/grid.length][node%grid.length].setBackground(Color.white);
			}
			lastPath = null;
		}
	}
	
	private ImageIcon rotateImage(Image originalImage, double angle) {
		ImageIcon rotatedIcon = null;
		
		AffineTransform transform = new AffineTransform();
		transform.rotate(angle, originalImage.getWidth(null) / 2, originalImage.getHeight(null) / 2);
		
		Image rotatedImage = new BufferedImage(originalImage.getWidth(null), originalImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D) rotatedImage.getGraphics();
		g2d.setTransform(transform);
		g2d.drawImage(originalImage, 0, 0, null);
		g2d.dispose();
		
		rotatedIcon = new ImageIcon(rotatedImage);
		
        return rotatedIcon;
	}
}
