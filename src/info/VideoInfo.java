package info;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class VideoInfo extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VideoInfo() {
		setTitle("Help - Video Functions");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(350, 250, 450, 300);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel title_panel = new JPanel();
		title_panel.setBackground(Color.LIGHT_GRAY);
		contentPane.add(title_panel, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("How To Video Control");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 20));
		title_panel.add(lblTitle);
		
		JPanel bottom = new JPanel();
		contentPane.add(bottom, BorderLayout.CENTER);
		bottom.setLayout(new BorderLayout(0, 0));
		
		JPanel buttons_panel = new JPanel();
		buttons_panel.setBackground(Color.DARK_GRAY);
		bottom.add(buttons_panel, BorderLayout.NORTH);
		buttons_panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		final JLabel lblSubtitle = new JLabel("Playing and pausing the video");
		final JTextArea textArea = new JTextArea("\nThis button allows you to play and pause the video, you are only allowed to merge at certain points when the video is paused.");
		
		JButton btnSkipb = new JButton();
		btnSkipb.setIcon(new ImageIcon(getClass().getResource("/buttons/skipb.png")));
		btnSkipb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblSubtitle.setText("Skipping back in intervals");
				textArea.setText("\nThis button allows you to skip backwards in the video, you can choose to set the interval at which it skips at by going to Edit>Set Skip Interval to...");
			}
		});
		buttons_panel.add(btnSkipb);
		
		JButton btnRewind = new JButton();
		btnRewind.setIcon(new ImageIcon(getClass().getResource("/buttons/rewind.png")));
		btnRewind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Rewinding the video");
				textArea.setText("\nThis button allows you to rewind the video, once this is clicked, it will only stop when the play button is clicked, or if the video reaches the beginning.");
			}
		});
		buttons_panel.add(btnRewind);
		
		JButton btnPlay = new JButton();
		btnPlay.setIcon(new ImageIcon(getClass().getResource("/buttons/play.png")));
		btnPlay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Playing and pausing the video");
				textArea.setText("\nThis button allows you to play and pause the video, you are only allowed to merge at certain points when the video is paused.");
			}
		});
		buttons_panel.add(btnPlay);
		
		JButton btnForward = new JButton();
		btnForward.setIcon(new ImageIcon(getClass().getResource("/buttons/forward.png")));
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Fast Forwarding the video");
				textArea.setText("\nThis button allows you to fast forward the video, once this is clicked, it will only stop when the play button is clicked, or if the video reaches the end.");
			}
		});
		buttons_panel.add(btnForward);
		
		JButton btnSkipf = new JButton();
		btnSkipf.setIcon(new ImageIcon(getClass().getResource("/buttons/skipf.png")));
		btnSkipf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Skipping Forwards in intervals");
				textArea.setText("\nThis button allows you to skip forwards in the video, you can choose to set the interval at which it skips at by going to Edit>Set Skip Interval to...");
			}
		});
		buttons_panel.add(btnSkipf);
		
		JButton btnSound = new JButton();
		btnSound.setIcon(new ImageIcon(getClass().getResource("/buttons/unmute.png")));
		btnSound.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblSubtitle.setText("Sound controls of the video");
				textArea.setText("\nThis button allows you to mute and unmute the video that is currently playing.\n Next to this is a slider which controls the volume of video.");
			}
		});
		buttons_panel.add(btnSound);
		
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
