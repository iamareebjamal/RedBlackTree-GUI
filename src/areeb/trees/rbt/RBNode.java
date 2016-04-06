package areeb.trees.rbt;

import java.awt.Color;

public class RBNode<T extends Comparable<T>> {
	/* Denoting colors with boolean value false - red, true - black */
	public static boolean RED = false;
	public static boolean BLACK = true;

	private boolean color = RED;
	private RBNode<T> left;
	private RBNode<T> right;
	private RBNode<T> parent;
	private T data;

	/* Simple Node Methods */

	public RBNode() {
	}

	public RBNode(T data) {
		this.data = data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public void removeFromParent() {
		if (getParent() == null)
			return;

		// Remove current node's links from the parent
		if (parent.getLeft() == this)
			parent.setLeft(null);
		else if (parent.getRight() == this)
			parent.setRight(null);

		this.parent = null;
	}

	public void setLeft(RBNode<T> child) {

		// Re-arranging the parents is required
		if (getLeft() != null)
			getLeft().setParent(null);

		if (child != null) {
			child.removeFromParent();
			child.setParent(this);
		}

		this.left = child;
	}

	public void setRight(RBNode<T> child) {

		// Re-arranging the parents is required
		if (getRight() != null) {
			getRight().setParent(null);
		}

		if (child != null) {
			child.removeFromParent();
			child.setParent(this);
		}

		this.right = child;
	}

	public T getData() {
		return data;
	}

	public RBNode<T> getLeft() {
		return left;
	}

	public static RBNode<?> getLeft(RBNode<?> node) {
		return node == null ? null : node.getLeft();
	}

	public RBNode<T> getRight() {
		return right;
	}

	public static RBNode<?> getRight(RBNode<?> node) {
		return node == null ? null : node.getRight();
	}

	public boolean hasLeft() {
		return left != null;
	}

	public boolean hasRight() {
		return right != null;
	}

	@Override
	public String toString() {
		return data.toString();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static int compare(RBNode<?> node, Comparable<?> b) {
		return ((Comparable) node.getData()).compareTo(b);
	}

	/* Red Black Node Functions */

	public boolean isRed() {
		return getColor() == RED;
	}

	public boolean isBlack() {
		return !isRed();
	}

	public static boolean isRed(RBNode<?> node) {
		return getColor(node) == RED;
	}

	public static boolean isBlack(RBNode<?> node) {
		return !isRed(node);
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	public static void setColor(RBNode<?> node, boolean color) {
		if (node == null)
			return;
		node.setColor(color);
	}

	public boolean getColor() {
		return color;
	}

	public static boolean getColor(RBNode<?> node) {
		// As null node is considered to be black
		return node == null ? BLACK : node.getColor();
	}

	public Color getActualColor() {
		if (isRed())
			return new Color(250, 70, 70);
		else
			return new Color(70, 70, 70);

	}

	public void toggleColor() {
		color = !color;
	}

	public static void toggleColor(RBNode<?> node) {
		if (node == null)
			return;

		node.setColor(!node.getColor());
	}

	public void setParent(RBNode<T> parent) {
		this.parent = parent;
	}

	public RBNode<T> getParent() {
		return parent;
	}

	public static RBNode<?> getParent(RBNode<?> node) {
		return (node == null) ? null : node.getParent();
	}

	public RBNode<T> getGrandparent() {
		RBNode<T> parent = getParent();
		return (parent == null) ? null : parent.getParent();
	}

	public static RBNode<?> getGrandparent(RBNode<?> node) {
		return (node == null) ? null : node.getGrandparent();
	}

	public RBNode<T> getSibling() {
		RBNode<T> parent = getParent();
		if (parent != null) { // No sibling of root node
			if (this == parent.getRight())
				return (RBNode<T>) parent.getLeft();
			else
				return (RBNode<T>) parent.getRight();
		}
		return null;
	}

	public static RBNode<?> getSibling(RBNode<?> node) {
		return (node == null) ? null : node.getSibling();
	}

	public RBNode<T> getUncle() {
		RBNode<T> parent = getParent();
		if (parent != null) { // No uncle of root
			return parent.getSibling();
		}
		return null;
	}

	public static RBNode<?> getUncle(RBNode<?> node) {
		return (node == null) ? null : node.getUncle();
	}

}
