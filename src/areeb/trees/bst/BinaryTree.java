package areeb.trees.bst;

public class BinaryTree<T extends Comparable<T>> {
	protected Node<T> root;
	protected int size = 0;

	public Node<T> getRoot() {
		return root;
	}

	public void insert(T item) {
		if (isEmpty()) {
			root = new Node<T>(item);
		} else {
			insert(root, item);
		}
		size++;
	}

	private void insert(Node<T> node, T item) {

		if (node.getData().compareTo(item) >= 0) {

			if (node.hasLeft())
				insert(node.getLeft(), item);
			else
				node.setLeft(new Node<T>(item));

		} else {

			if (node.hasRight())
				insert(node.getRight(), item);
			else
				node.setRight(new Node<T>(item));
		}
	}

	public boolean isEmpty() {
		return root == null;
	}

	public int getSize() {
		return size;
	}

	public void inOrder() {
		inOrder(root);
	}

	private void inOrder(Node<T> node) {
		if (node != null) {
			inOrder(node.getLeft());
			System.out.print(node + " ");
			inOrder(node.getRight());
		}
	}

	public boolean contains(T data) {
		return contains(root, data);
	}

	private boolean contains(Node<T> root, T data) {
		if (root == null)
			return false;

		if (root.getData().compareTo(data) > 0)
			return contains(root.getLeft(), data);
		else if (root.getData().compareTo(data) < 0)
			return contains(root.getRight(), data);
		else
			return true;

	}

	public int getDepth() {
		return getDepth(root);
	}

	private int getDepth(Node<T> node) {
		if (node != null) {
			int left_depth = getDepth(node.getLeft());
			int right_depth = getDepth(node.getRight());

			return (left_depth > right_depth) ? left_depth + 1
					: right_depth + 1;
		}
		return 0;
	}

	private Node<T> deleteNode(T data, Node<T> root) {
		if (root == null)
			return root;
		else if (data.compareTo(root.getData()) < 0)
			root.setLeft(deleteNode(data, root.getLeft()));
		else if (data.compareTo(root.getData()) > 0)
			root.setRight(deleteNode(data, root.getRight()));
		else if (root.getRight() != null && root.getLeft() != null) {
			root.setData(getSuccessor(root.getRight()).getData());
			root.setRight(deleteNode(root.getData(), root.getRight()));
		} else
			root = (root.getLeft() != null) ? root.getLeft() : root.getRight();
		return root;
	}

	private Node<T> getSuccessor(Node<T> root) {
		if (root == null)
			return null;
		else if (root.getLeft() == null)
			return root;
		return getSuccessor(root.getLeft());
	}

	public void deleteNode(T data) {
		root = deleteNode(data, this.root);
	}

}
