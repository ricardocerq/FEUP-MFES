package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;

public class StrategoWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	JPanel buttonPanel = new JPanel();
	JButton btn1;
	JButton btn3;
	JButton btn2;

	Model model;
	StrategoPanel view;
	public static StrategoControl strategoCtrl;

	public StrategoWindow() {
		// Use OS look and feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}

		setTitle("Stratego");

		// Allow Overture to do a controlled shutdown
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		this.model = new Model();
		
		// add buttons
		btn1 = new JButton("Button 1");
		btn2 = new JButton("Button 2");
		btn3 = new JButton("Button 3");

		setMinimumSize(new Dimension(600, 400));

		init();
		setVisible(true);

		setPreferredSize(new Dimension(700, 700));
		pack();

		setLocationRelativeTo(null);
		
		
	}

	public void init() {
		try {
			view = new StrategoPanel(model, strategoCtrl);
		} catch (IOException ex) {
			Logger.getLogger(StrategoWindow.class.getName()).log(Level.SEVERE, null, ex);
		}

		attachListenersToComponents();
		layOutComponents();

		// Connect model and view

		model.addObserver(view);
	}

	private void attachListenersToComponents() {
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		btn3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	}

	private void layOutComponents() {
		getContentPane().setLayout(new BorderLayout());
		buttonPanel.setBackground(Color.WHITE);
		getContentPane().add(BorderLayout.SOUTH, buttonPanel);
		buttonPanel.add(btn1);
		buttonPanel.add(btn2);
		buttonPanel.add(btn3);
		getContentPane().add(BorderLayout.CENTER, view);

		this.setVisible(true);
	}

	public void DisableButtons() {
		btn1.setEnabled(false);
		btn2.setEnabled(false);
		btn3.setEnabled(false);
	}

	public void EnableButtons() {
		btn1.setEnabled(true);
		btn2.setEnabled(true);
		btn3.setEnabled(true);
	}

	public Model getModel() {
		return model;
	}

	@Override
	public void dispose() {
		strategoCtrl.finish();
	}
}
