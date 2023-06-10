package gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

public class RelGridLayout implements LayoutManager {
	public static final int ALIGN_VERT = 0, ALIGN_HORIZ = 1;
	
	private record Cell(int row, int col) {}
	
	private Container parent;
	private double[] rowSizes, colSizes;
//	private Component[][] comps;
	private Dimension[][] absSizes;
	
	public RelGridLayout(double[] rowSizes, double[] colSizes) {
		this.rowSizes = rowSizes;
		this.colSizes = colSizes;
//		comps = new Component[rowSizes.length][colSizes.length];
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
//		var free = find(null);
//		if(free.isEmpty()) {
//			System.err.println("no more space for component " + name);
//			return;
//		}
//		
//		var cell = free.get();
//		
//		System.out.println("inserting component " + name + " at [" + row + ", " + col + "]");
//		

	}
	
	private void setComponentBounds(int index, Component comp) {
		int col = index % colSizes.length;
		int row = index / colSizes.length;
		if(row < 0 || absSizes.length <= row || col < 0 || absSizes[row].length < col) {
			System.err.println("no more space for " + comp);
			return;
		}
		var size = absSizes[row][col];
		comp.setSize(size);
		
		int x, y; x=y=0;
		for(int i = 0; i < col; i++) {
			x += absSizes[row][i].width;
		}
		for(int i = 0; i < row; i++) {
			y += absSizes[i][col].height;
		}
		comp.setLocation(x, y);
//		comps[row][col] = comp;
		
		System.out.println("resized component at " + x + ", " + y + " with " + size.width + "x" + size.height);
	}

	@Override
	public void removeLayoutComponent(Component comp) {
//		var cell = find(comp);
//		if(cell.isEmpty())
//			return;
//		var co = cell.get();
//		comps[co.row()][co.col()] = null;
		parent.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		int wm = 0, hm = 0;
		for(int i = 0; i < rowSizes.length; i++) {
			hm += absSizes[i][0].height;
		}
		for(int i = 0; i < colSizes.length; i++) {
			wm += absSizes[0][i].width;
		}
		return new Dimension(wm, hm);
	}

	@Override
	public void layoutContainer(Container parent) {
		this.parent = parent;
		
		absSizes = getAbsSizes(parent.getSize());
		
		System.out.println("set parent to be " + parent);
		
		for(int i = 0; i < parent.getComponentCount(); i++) {
			setComponentBounds(i, parent.getComponent(i));
		}
	}
	

	private Dimension[][] getAbsSizes(Dimension parent) {
		int rsl = rowSizes.length, csl = colSizes.length;
		int[] 	widths = new int[csl], 
				heights = new int[rsl];
		
		double rtotal = 0;
		for(int row = 0; row < rsl; row++)
			rtotal += rowSizes[row];
		for(int row = 0; row < rsl; row++)
			heights[row] = (int) Math.round(parent.getHeight() * rowSizes[row] / rtotal);
		
		double ctotal = 0;
		for(int col = 0; col < csl; col++)
			ctotal += colSizes[col];
		for(int col = 0; col < csl; col++)
			widths[col] = (int) Math.round(parent.getWidth() * colSizes[col] / ctotal);

		
		var dims = new Dimension[rsl][csl];
		for(int i = 0; i < rsl; i++) {
			for(int j = 0; j < csl; j++) {
				dims[i][j] = new Dimension(widths[j], heights[i]);
			}
		}
		
		return dims;
	}

//	private Optional<Cell> find(Component comp) {
//		for(int i = 0; i < comps.length; i++) {
//			for(int j = 0; j < comps[i].length; j++) {
//				if(Objects.equals(comps[i][j], comp))
//					return Optional.of(new Cell(i, j));
//			}
//		}
//		return Optional.empty();
//	}
}
