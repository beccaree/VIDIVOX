package info;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class AudioInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AudioInfo() {
		setTitle("Help - Audio Functions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 250, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel title_panel = new JPanel();
		title_panel.setBackground(Color.LIGHT_GRAY);
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
		final JTextArea textArea = new JTextArea("\nThis button lets a computer voice speak out the commentary that you have entered above it.\n This can be stopped with the 'Stop' button at any time.");
		
		JButton btnSkipb = new JButton("Speak");
		btnSkipb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSubtitle.setText("Speaking with festival");
				textArea.setText("\nThis button lets a computer voice speak out the commentary that you have entered above it.\n This can be stopped with the 'Stop' button at any time.");
			}
		});
		buttons_panel.add(btnSkipb);
		
		JButton btnMergeAudioHere = new JButton("Stop");
		btnMergeAudioHere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Stopping the festival voice");
				textArea.setText("\nThis button stops the computer voice that is talking, it does nothing if no voices are speaking.");
			}
		});
		buttons_panel.add(btnMergeAudioHere);
		
		JButton btnPlay = new JButton("Save As MP3");
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Saving your text as an MP3 (audio) file");
				textArea.setText("\nThis button prompts you for a name for your new audio file, and saves it in a folder named MP3Files.");
			}
		});
		buttons_panel.add(btnPlay);
		
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
		explain_panel.add(textArea, BorderLayout.CENTER);
	}
}
