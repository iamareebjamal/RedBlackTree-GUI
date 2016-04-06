package areeb.trees;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import areeb.trees.rbt.RedBlackTree;

public class TreeViewer extends JPanel {

	private static final long serialVersionUID = 1500L;

	private RedBlackTree<Integer> tree = new RedBlackTree<Integer>();
	private TreePanel treePanel = new TreePanel(tree);

	public TreeViewer() {
		treePanel.setBackground(new Color(235, 225, 240));
		initViews();
	}

	private void setMidPoint(JScrollPane scrollPane) {
		scrollPane.getViewport().setViewPosition(new Point(4100, 0));

	}

	private void setTopPanel() {
		JLabel info = new JLabel("Red Black Tree Viewer by iamareebjamal");
		info.setForeground(new Color(230, 220, 250));
		JPanel panel = new JPanel();
		panel.setBackground(new Color(130, 50, 250));
		panel.add(info);
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		add(panel, BorderLayout.NORTH);
	}

	private void setBottomPanel() {
		final JTextField mTextField = new JTextField(15);
		final JButton btn_ins = new JButton();
		setupButton(btn_ins, "add");
		final JButton btn_del = new JButton();
		setupButton(btn_del, "del");
		final JButton btn_clr = new JButton();
		setupButton(btn_clr, "clr");
		final JButton btn_info = new JButton();
		setupButton(btn_info, "search");

		JPanel panel = new JPanel();
		panel.add(btn_info);
		panel.add(btn_ins);
		panel.add(mTextField);
		panel.add(btn_del);
		panel.add(btn_clr);
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.SOUTH);

		btn_ins.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (mTextField.getText().equals(""))
					return;

				Integer toInsert = Integer.parseInt(mTextField.getText());
				if (tree.contains(toInsert)) {
					JOptionPane.showMessageDialog(null,
							"Element is already present in the tree");
				} else {
					tree.insert(toInsert);
					treePanel.repaint();
					mTextField.requestFocus();
					mTextField.selectAll();
				}

			}

		});

		btn_del.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (mTextField.getText().equals(""))
					return;

				Integer toDelete = Integer.parseInt(mTextField.getText());
				if (!tree.contains(toDelete)) {
					JOptionPane.showMessageDialog(null,
							"Element is not present in the tree");
				} else {
					tree.remove(toDelete);
					treePanel.repaint();
					mTextField.requestFocus();
					mTextField.selectAll();
				}

			}

		});

		btn_clr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				if (tree.isEmpty())
					JOptionPane
							.showMessageDialog(null, "Tree is already empty");
				else
					tree.clear();

				treePanel.setSearch(null);
				treePanel.repaint();
			}
		});

		btn_info.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {

				if (mTextField.getText().equals(""))
					return;

				Integer toSearch = Integer.parseInt(mTextField.getText());
				if (!tree.contains(toSearch)) {
					JOptionPane.showMessageDialog(null,
							"Element is not present in the tree");
				} else {
					treePanel.setSearch(toSearch);
					treePanel.repaint();
					mTextField.requestFocus();
					mTextField.selectAll();
				}
			}

		});

		mTextField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				btn_ins.doClick();
			}

		});

	}

	private void setScrollPane() {
		treePanel.setPreferredSize(new Dimension(9000, 4096));

		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(treePanel);
		scroll.setPreferredSize(new Dimension(750, 500));
		setMidPoint(scroll);
		add(scroll, BorderLayout.CENTER);
	}

	private void initViews() {
		super.setLayout(new BorderLayout());
		setScrollPane();
		setTopPanel();
		setBottomPanel();
	}

	private void setupButton(JButton button, String imgSrc) {
		try {
			Image icon = ImageIO.read(getClass().getResource(
					"/resources/" + imgSrc + ".png"));
			button.setIcon(new ImageIcon(icon));
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			button.setContentAreaFilled(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String... args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		JFrame j = new JFrame();
		j.setTitle("Tree Viewer");

		try {
			j.setIconImage(ImageIO.read(TreeViewer.class
					.getResource("/resources/ic_binary.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		j.add(new TreeViewer());
		j.pack();
		j.setVisible(true);
	}
}