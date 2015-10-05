package videoPlayer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import universalMethods.Utility;

public class AudioPane extends JPanel {
	
	private ArrayList<Integer> killPID = new ArrayList<Integer>();
	
	public AudioPane(final JFrame parent) {
		setMinimumSize(new Dimension(300, 500));
		setBackground(Color.DARK_GRAY);
		setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblEnterYourCommentary = new JLabel("Enter Commentary here:"); // Label to tell user what the text area is for
		lblEnterYourCommentary.setVerticalAlignment(SwingConstants.BOTTOM);
		lblEnterYourCommentary.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterYourCommentary.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblEnterYourCommentary.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEnterYourCommentary.setForeground(Color.pink);
		add(lblEnterYourCommentary);
				
		final JTextArea txtrCommentary = new JTextArea(); // TextArea for user to enter their commentary
		txtrCommentary.setRows(5);
		txtrCommentary.setText("(max 40 words)");
		txtrCommentary.setLineWrap(true);
		add(txtrCommentary);
		
		JPanel audio_options = new JPanel();
		audio_options.setBackground(Color.DARK_GRAY);
		add(audio_options);
		
		JButton btnSpeak = new JButton("Speak");
		btnSpeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Speak commentary to the user through festival text-to-speech
				String input = txtrCommentary.getText();
				BgFestival bg = new BgFestival(input, killPID);
				bg.execute();
				killPID.removeAll(killPID);		
			}
		});
		audio_options.add(btnSpeak);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Kill the festival process (Stop speaking)
				if (!killPID.isEmpty()) {
					if (killPID.get(0) != 0) {
						int festID = killPID.get(0)+4;
						String cmd = "kill " + festID;
						ProcessBuilder builder = new ProcessBuilder("/bin/bash", "-c", cmd);
						try {
							builder.start();
							killPID.set(0, 0);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		audio_options.add(btnStop);
		
		// Save input in text area as .wav file and convert it to an .mp3
		JButton btnSaveAs = new JButton("Save as MP3");
		btnSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Check that the number of words in the commentary is greater than 0 but no more than 40
				String commentary = txtrCommentary.getText();
	   			String[] words = commentary.split(" ");
	   			
	   			if (words.length > 0 && words.length <= 40) {
	   				// Prompt user for what they want to name their mp3 file
	   				JDialog mp3Name = new MP3Prompt(commentary);
	   				mp3Name.setVisible(true);
	   			} else {
	   				JOptionPane.showMessageDialog(parent, "Please only have between 1 and 40 words. Please try again.", "Too many words", JOptionPane.ERROR_MESSAGE);
	   			}
			}
		});
		audio_options.add(btnSaveAs);
		
		JPanel merge_Panel = new JPanel();
		merge_Panel.setBackground(Color.DARK_GRAY);
		add(merge_Panel);
		merge_Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Let user select an .mp3 file to merge into the beginning of current video
		JButton btnMergeBegin = new JButton("Merge a MP3 at Beginning");
		btnMergeBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		merge_Panel.add(btnMergeBegin);
		
		// Let user select an .mp3 file to merge into any point of current video
		JButton btnMergeAnywhere = new JButton("Merge a MP3 at...");
		btnMergeAnywhere.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			}
		});
		merge_Panel.add(btnMergeAnywhere);
	}
}
