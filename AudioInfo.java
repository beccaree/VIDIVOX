package info;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

public class AudioInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AudioInfo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel title_panel = new JPanel();
		contentPane.add(title_panel, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("How To Manipulate Audio");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title_panel.add(lblTitle);
		
		JPanel bottom = new JPanel();
		contentPane.add(bottom, BorderLayout.CENTER);
		bottom.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons_panel = new JPanel();
		buttons_panel.setBackground(Color.DARK_GRAY);
		bottom.add(buttons_panel, BorderLayout.NORTH);
		buttons_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		final JLabel lblSubtitle = new JLabel("Speaking with festival");
		final JTextArea textArea = new JTextArea("fest ex");
		
		JButton btnSkipb = new JButton("Speak");
		btnSkipb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSubtitle.setText("Speaking with festival");
				//change explanation
			}
		});
		buttons_panel.add(btnSkipb);
		
		JButton btnMergeAudioHere = new JButton("Stop");
		btnMergeAudioHere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Stopping the festival voice");
				//change explanation
			}
		});
		buttons_panel.add(btnMergeAudioHere);
		
		JButton btnPlay = new JButton("Save As MP3");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Saving your text as an MP3 (audio) file");
				//change explanation
			}
		});
		buttons_panel.add(btnPlay);
		
		JPanel explain_panel = new JPanel();
		explain_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		bottom.add(explain_panel);
		explain_panel.setLayout(new BorderLayout(0, 0));

		lblSubtitle.setFont(new Font("Tahoma", Font.BOLD, 11));
		explain_panel.add(lblSubtitle, BorderLayout.NORTH);
		
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		explain_panel.add(textArea, BorderLayout.CENTER);
	}
}
