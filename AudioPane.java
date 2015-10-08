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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import merge.MergePrompt;

import universalMethods.Utility;

public class AudioPane extends JPanel {
	
	private ArrayList<Integer> killPID = new ArrayList<Integer>();
	
	protected static JButton btnMergeAt;
	
	public AudioPane(final JFrame parent, Color theme) {
		setMinimumSize(new Dimension(300, 500));
		setBackground(Color.DARK_GRAY);
		setLayout(new GridLayout(0, 1, 0, 0));

		JLabel lblEnterYourCommentary = new JLabel("Enter Commentary here: (max 40 words)"); // Label to tell user what the text area is for
		lblEnterYourCommentary.setVerticalAlignment(SwingConstants.BOTTOM);
		lblEnterYourCommentary.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterYourCommentary.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblEnterYourCommentary.setForeground(theme);
		add(lblEnterYourCommentary);
				
		final JTextArea txtrCommentary = new JTextArea(); // TextArea for user to enter their commentary
		txtrCommentary.setRows(5);
		txtrCommentary.setLineWrap(true);
		txtrCommentary.setWrapStyleWord(true);
		add(txtrCommentary);
		
		JPanel audio_options = new JPanel();
		audio_options.setBackground(Color.DARK_GRAY);
		add(audio_options);
		
		final JButton btnSpeak = new JButton("Speak");
		btnSpeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Speak commentary to the user through festival text-to-speech
				String input = txtrCommentary.getText();
				BgFestival bg = new BgFestival(input, killPID);
				bg.execute();
				killPID.removeAll(killPID);		
			}
		});
		btnSpeak.setEnabled(false);
		audio_options.add(btnSpeak);
		
		final JButton btnStop = new JButton("Stop");
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
		btnStop.setEnabled(false);
		audio_options.add(btnStop);
		
		// Save input in text area as .wav file and convert it to an .mp3
		final JButton btnSaveAs = new JButton("Save as MP3");
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
		btnSaveAs.setEnabled(false);
		audio_options.add(btnSaveAs);
		
		txtrCommentary.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				if(txtrCommentary.getText().length() > 0) {
					btnSpeak.setEnabled(true);
					btnStop.setEnabled(true);
					btnSaveAs.setEnabled(true);
				} else {
					btnSpeak.setEnabled(false);
					btnStop.setEnabled(false);
					btnSaveAs.setEnabled(false);
				}
			}
			public void removeUpdate(DocumentEvent e) {
				if(txtrCommentary.getText().length() > 0) {
					btnSpeak.setEnabled(true);
					btnStop.setEnabled(true);
					btnSaveAs.setEnabled(true);
				} else {
					btnSpeak.setEnabled(false);
					btnStop.setEnabled(false);
					btnSaveAs.setEnabled(false);
				}
			}
			public void changedUpdate(DocumentEvent e) {
			}
		});
		
		JPanel merge_Panel = new JPanel();
		merge_Panel.setBackground(Color.DARK_GRAY);
		add(merge_Panel);
		merge_Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		// Let user select an .mp3 file to merge into the beginning of current video
		JButton btnMergeBegin = new JButton("Merge Audio at Beginning");
		btnMergeBegin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Simply merge the video at the beginning
				JDialog merge = new MergePrompt(0);
				merge.setVisible(true);
			}
		});
		merge_Panel.add(btnMergeBegin);
		
		JLabel lblPauseFirst = new JLabel("Please pause video at desired position");
		lblPauseFirst.setForeground(theme);
		merge_Panel.add(lblPauseFirst);
		
		// Let user select an .mp3 file to merge into any point of current video
		btnMergeAt = new JButton("Merge Audio here...");
		btnMergeAt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// This button should only be enabled if the video is paused
				// Merge video at position video is paused at
				JDialog merge = new MergePrompt(VideoPane.video.getTime());
				merge.setVisible(true);
			}
		});
		btnMergeAt.setEnabled(false);
		merge_Panel.add(btnMergeAt);
	}
}
