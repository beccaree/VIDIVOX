package info;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class MergingInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public MergingInfo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 250, 450, 300);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel title_panel = new JPanel();
		title_panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(title_panel, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("How To Merge");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title_panel.add(lblTitle);
		
		JPanel bottom = new JPanel();
		contentPane.add(bottom, BorderLayout.CENTER);
		bottom.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons_panel = new JPanel();
		buttons_panel.setBackground(Color.DARK_GRAY);
		bottom.add(buttons_panel, BorderLayout.NORTH);
		buttons_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		final JLabel lblSubtitle = new JLabel("Merging audio at the beginning");
		// Explanations begin with \n for foramtting reasons (looks better)
		final JTextArea textArea = new JTextArea("\nSimply click this button, and fill in all options for the 'Merge' button to become enabled.\n"
				+ "This will merge your chosen audio file at the beginning of the video, the merge functions of this program will automatically overlap the original auido of the video.\n"
				+ "This video is then saved in a folder named VideoFiles.");
		
		JButton btnMergeAudioAt = new JButton("Merge Audio at Beginning");
		btnMergeAudioAt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSubtitle.setText("Merging audio at the beginning");
				textArea.setText("\nSimply click this button, and fill in all options for the 'Merge' button to become enabled.\n"
						+ "This will merge your chosen audio file at the beginning of the video, the merge functions of this program will automatically overlap the original auido of the video.\n"
						+ "This video is then saved in a folder named VideoFiles.");
			}
		});
		buttons_panel.add(btnMergeAudioAt);
		
		JButton btnMergeAudioHere = new JButton("Merge Audio here...");
		btnMergeAudioHere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Merging audio at any point");
				textArea.setText("\nFor this button to become enabled, the video that is currently playing must be paused.\n"
						+ "Once the video is paused, the merge function will merge the chosen audio at the point where the video is paused at, "
						+ "so please ensure that the video is paused at your desired position.\n"
						+ "This video is then saved in a folder named VideoFiles.");
			}
		});
		buttons_panel.add(btnMergeAudioHere);
		
		JPanel explain_panel = new JPanel();
		explain_panel.setBackground(Color.LIGHT_GRAY);
		explain_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottom.add(explain_panel);
		explain_panel.setLayout(new BorderLayout(0, 0));
		
		explain_panel.add(lblSubtitle, BorderLayout.NORTH);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		textArea.setBackground(Color.LIGHT_GRAY);
		textArea.setForeground(Color.BLACK);
		explain_panel.add(textArea);
	}

}
