package merge;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JProgressBar;

public class WaitProcessBar extends JFrame {

	private JPanel contentPane;
	protected JProgressBar progressBar; // Allow the process to update the progress bar

	/**
	 * Create the frame.
	 */
	public WaitProcessBar() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(350, 300, 500, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblPleaseWaitWhile = new JLabel("Please wait while your video is merging...");
		lblPleaseWaitWhile.setBounds(10, 24, 365, 19);
		contentPane.add(lblPleaseWaitWhile);
		lblPleaseWaitWhile.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		progressBar = new JProgressBar();
		progressBar.setBounds(10, 43, 464, 31);
		contentPane.add(progressBar);
	}

}
