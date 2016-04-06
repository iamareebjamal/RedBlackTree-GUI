package areeb.trees.rbt;

import areeb.trees.rbt.RBNode;

public class RedBlackTree<T extends Comparable<T>> {
    protected RBNode<T> root;
    protected int size = 0;

    public RBNode<T> getRoot() {
        return this.root;
    }

    public void insert(T item) {
        if (this.isEmpty()) {
            this.root = new RBNode<T>(item);
        } else {
            this.insert(this.root, item);
        }
        this.root.setColor(RBNode.BLACK);
        ++this.size;
    }

    private void insert(RBNode<T> node, T item) {
        if (this.contains(item)) {
            return;
        }
        if (node.getData().compareTo(item) > 0) {
            if (node.hasLeft()) {
                this.insert(node.getLeft(), item);
            } else {
                RBNode<T> inserted = new RBNode<T>(item);
                node.setLeft(inserted);
                this.balanceAfterInsert(inserted);
            }
        } else if (node.hasRight()) {
            this.insert(node.getRight(), item);
        } else {
            RBNode<T> inserted = new RBNode<T>(item);
            node.setRight(inserted);
            this.balanceAfterInsert(inserted);
        }
    }

    public void remove(T data) {
        if (!this.contains(data)) {
            return;
        }
        
        RBNode<T> node = this.find(data);
        if (node.getLeft() != null && node.getRight() != null) {
            RBNode<T> successor = this.getSuccessor(node);
            node.setData(successor.getData());
            node = successor;
        }
        
        RBNode<T> pullUp = node.getLeft() == null ? node.getRight() : node.getLeft();
        if (pullUp != null) {
            if (node == this.root) {
                node.removeFromParent();
                this.root = node;
            } else if (RBNode.getLeft(node.getParent()) == node) {
                node.getParent().setLeft(pullUp);
            } else {
                node.getParent().setRight(pullUp);
            }
            if (RBNode.isBlack(node)) {
                this.balanceAfterDelete(pullUp);
            }
        } else if (node == this.root) {
            this.root = null;
        } else {
            if (RBNode.isBlack(node)) {
                this.balanceAfterDelete(node);
            }
            node.removeFromParent();
        }
    }

    public boolean isEmpty() {
        if (this.root == null) {
            return true;
        }
        return false;
    }

    public void clear() {
        this.root = null;
    }

    public int getSize() {
        return this.size;
    }

    public void inOrder() {
        this.inOrder(this.root);
    }

    private void inOrder(RBNode<T> node) {
        if (node != null) {
            this.inOrder(node.getLeft());
            System.out.print(node + " ");
            this.inOrder(node.getRight());
        }
    }

    public boolean contains(T data) {
        return this.contains(this.root, data);
    }

    private boolean contains(RBNode<T> root, T data) {
        if (root == null) {
            return false;
        }
        if (root.getData().compareTo(data) > 0) {
            return this.contains(root.getLeft(), data);
        }
        if (root.getData().compareTo(data) < 0) {
            return this.contains(root.getRight(), data);
        }
        return true;
    }

    public RBNode<T> find(T data) {
        return this.find(this.root, data);
    }

    private RBNode<T> find(RBNode<T> root, T data) {
        if (root == null) {
            return null;
        }
        if (root.getData().compareTo(data) > 0) {
            return this.find(root.getLeft(), data);
        }
        if (root.getData().compareTo(data) < 0) {
            return this.find(root.getRight(), data);
        }
        return root;
    }

    public int getDepth() {
        return this.getDepth(this.root);
    }

    private int getDepth(RBNode<T> node) {
        if (node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.getLeft());
            return left_depth > (right_depth = this.getDepth(node.getRight())) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    }

    private RBNode<T> getSuccessor(RBNode<T> root) {
        RBNode<T> leftTree = root.getLeft();
        if (leftTree != null) {
            while (leftTree.getRight() != null) {
                leftTree = leftTree.getRight();
            }
        }
        return leftTree;
    }

    private void balanceAfterInsert(RBNode<T> node) {
        if (node == null || node == this.root || RBNode.isBlack(node.getParent())) {
            return;
        }
        if (RBNode.isRed(node.getUncle())) {
            RBNode.toggleColor(node.getParent());
            RBNode.toggleColor(node.getUncle());
            RBNode.toggleColor(node.getGrandparent());
            this.balanceAfterInsert(node.getGrandparent());
        } else if (this.hasLeftParent(node)) {
            if (this.isRightChild(node)) {
                node = node.getParent();
                this.rotateLeft(node);
            }
            RBNode.setColor(node.getParent(), RBNode.BLACK);
            RBNode.setColor(node.getGrandparent(), RBNode.RED);
            this.rotateRight(node.getGrandparent());
        } else if (this.hasRightParent(node)) {
            if (this.isLeftChild(node)) {
                node = node.getParent();
                this.rotateRight(node);
            }
            RBNode.setColor(node.getParent(), RBNode.BLACK);
            RBNode.setColor(node.getGrandparent(), RBNode.RED);
            this.rotateLeft(node.getGrandparent());
        }
    }

    @SuppressWarnings("unchecked")
	private void balanceAfterDelete(RBNode<T> node) {
        while (node != this.root && node.isBlack()) {
            RBNode<T> sibling = node.getSibling();
            if (node == RBNode.getLeft(node.getParent())) {
                if (RBNode.isRed(sibling)) {
                    RBNode.setColor(sibling, RBNode.BLACK);
                    RBNode.setColor(node.getParent(), RBNode.RED);
                    this.rotateLeft(node.getParent());
                    sibling = (RBNode<T>) RBNode.getRight(node.getParent());
                }
                if (RBNode.isBlack(RBNode.getLeft(sibling)) && RBNode.isBlack(RBNode.getRight(sibling))) {
                    RBNode.setColor(sibling, RBNode.RED);
                    node = node.getParent();
                    continue;
                }
                if (RBNode.isBlack(RBNode.getRight(sibling))) {
                    RBNode.setColor(RBNode.getLeft(sibling), RBNode.BLACK);
                    RBNode.setColor(sibling, RBNode.RED);
                    this.rotateRight(sibling);
                    sibling = (RBNode<T>) RBNode.getRight(node.getParent());
                }
                RBNode.setColor(sibling, RBNode.getColor(node.getParent()));
                RBNode.setColor(node.getParent(), RBNode.BLACK);
                RBNode.setColor(RBNode.getRight(sibling), RBNode.BLACK);
                this.rotateLeft(node.getParent());
                node = this.root;
                continue;
            }
            if (RBNode.isRed(sibling)) {
                RBNode.setColor(sibling, RBNode.BLACK);
                RBNode.setColor(node.getParent(), RBNode.RED);
                this.rotateRight(node.getParent());
                sibling = (RBNode<T>) RBNode.getLeft(node.getParent());
            }
            if (RBNode.isBlack(RBNode.getLeft(sibling)) && RBNode.isBlack(RBNode.getRight(sibling))) {
                RBNode.setColor(sibling, RBNode.RED);
                node = node.getParent();
                continue;
            }
            if (RBNode.isBlack(RBNode.getLeft(sibling))) {
                RBNode.setColor(RBNode.getRight(sibling), RBNode.BLACK);
                RBNode.setColor(sibling, RBNode.RED);
                this.rotateLeft(sibling);
                sibling = (RBNode<T>) RBNode.getLeft(node.getParent());
            }
            RBNode.setColor(sibling, RBNode.getColor(node.getParent()));
            RBNode.setColor(node.getParent(), RBNode.BLACK);
            RBNode.setColor(RBNode.getLeft(sibling), RBNode.BLACK);
            this.rotateRight(node.getParent());
            node = this.root;
        }
        RBNode.setColor(node, RBNode.BLACK);
    }

    private void rotateRight(RBNode<T> node) {
        if (node.getLeft() == null) {
            return;
        }
        RBNode<T> leftTree = node.getLeft();
        node.setLeft(leftTree.getRight());
        if (node.getParent() == null) {
            this.root = leftTree;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(leftTree);
        } else {
            node.getParent().setRight(leftTree);
        }
        leftTree.setRight(node);
    }

    private void rotateLeft(RBNode<T> node) {
        if (node.getRight() == null) {
            return;
        }
        RBNode<T> rightTree = node.getRight();
        node.setRight(rightTree.getLeft());
        if (node.getParent() == null) {
            this.root = rightTree;
        } else if (node.getParent().getLeft() == node) {
            node.getParent().setLeft(rightTree);
        } else {
            node.getParent().setRight(rightTree);
        }
        rightTree.setLeft(node);
    }

    private boolean hasRightParent(RBNode<T> node) {
        if (RBNode.getRight(node.getGrandparent()) == node.getParent()) {
            return true;
        }
        return false;
    }

    private boolean hasLeftParent(RBNode<T> node) {
        if (RBNode.getLeft(node.getGrandparent()) == node.getParent()) {
            return true;
        }
        return false;
    }

    private boolean isRightChild(RBNode<T> node) {
        if (RBNode.getRight(node.getParent()) == node) {
            return true;
        }
        return false;
    }

    private boolean isLeftChild(RBNode<T> node) {
        if (RBNode.getLeft(node.getParent()) == node) {
            return true;
        }
        return false;
    }
}