package areeb.trees;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import areeb.trees.rbt.RBNode;
import areeb.trees.rbt.RedBlackTree;

public class TreePanel extends JPanel {

	private static final long serialVersionUID = 1250L;

	private RedBlackTree<?> tree;
	private int radius = 20;
	private int yOffset = 50;
	private Color textColor = new Color(230, 230, 230);

	/* Search the node */
	private Comparable<?> toSearch;

	public TreePanel(RedBlackTree<?> tree) {
		this.tree = tree;
	}

	public void setSearch(Comparable<?> c) {
		toSearch = c;
	}

	@Override
	protected void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);

		if (tree.isEmpty())
			return;

		Graphics2D graphics2d = (Graphics2D) graphics;
		graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		paintTree(graphics2d, (RBNode<?>) tree.getRoot(), getWidth() / 2, 30,
				getGap());

	}

	private void paintTree(Graphics2D g, RBNode<?> root, int x, int y,
			int xOffset) {

		/* Update the view if unbound layout */
		if (x < 0)
			setPreferredSize(new Dimension(2 * getWidth(), getHeight()));

		if (toSearch != null && RBNode.compare(root, toSearch) == 0)
			drawHalo(g, x, y);

		drawNode(g, root, x, y);

		if (root.getLeft() != null) {
			join(g, x - xOffset, y + yOffset, x, y);
			paintTree(g, (RBNode<?>) root.getLeft(), x - xOffset, y + yOffset,
					xOffset / 2);
		}

		if (root.getRight() != null) {
			join(g, x + xOffset, y + yOffset, x, y);
			paintTree(g, (RBNode<?>) root.getRight(), x + xOffset, y + yOffset,
					xOffset / 2);
		}
	}

	private void drawHalo(Graphics2D g, int x, int y) {
		g.setColor(new Color(160, 100, 250));
		radius += 5;
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		radius -= 5;
	}

	private void drawNode(Graphics2D g, RBNode<?> node, int x, int y) {
		g.setColor(node.getActualColor());
		g.fillOval(x - radius, y - radius, 2 * radius, 2 * radius);
		g.setColor(textColor);

		String text = node.toString();
		drawCentreText(g, text, x, y);
		g.setColor(Color.GRAY);
	}

	private void drawCentreText(Graphics2D g, String text, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		double t_width = fm.getStringBounds(text, g).getWidth();
		g.drawString(text, (int) (x - t_width / 2),
				(int) (y + fm.getMaxAscent() / 2));
	}

	private void join(Graphics2D g, int x1, int y1, int x2, int y2) {
		double hypot = Math.hypot(yOffset, x2 - x1);
		int x11 = (int) (x1 + radius * (x2 - x1) / hypot);
		int y11 = (int) (y1 - radius * yOffset / hypot);
		int x21 = (int) (x2 - radius * (x2 - x1) / hypot);
		int y21 = (int) (y2 + radius * yOffset / hypot);
		g.drawLine(x11, y11, x21, y21);
	}

	private int getGap() {
		int depth = tree.getDepth();
		int multiplier = 30;
		float exponent = (float) 1.4;

		if (depth > 6) {
			multiplier += depth * 3;
			exponent += .1;
		}

		return (int) Math.pow(depth, exponent) * multiplier;
	}

}
