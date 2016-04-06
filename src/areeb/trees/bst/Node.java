package areeb.trees.bst;

public class Node<T extends Comparable<T>> {

	private T data;
	private Node<T> left;
	private Node<T> right;

	public Node() {
	}

	public Node(T data) {
		this.data = data;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getLeft() {
		return left;
	}

	public Node<T> getRight() {
		return right;
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

}