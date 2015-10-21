package videoPlayer.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import videoPlayer.BGTasks.AudioTools;
import videoPlayer.BGTasks.BgFestival;
import videoPlayer.BGTasks.MP3Prompt;
import videoPlayer.components.actionListeners.StopListener;

import merge.MergePrompt;

@SuppressWarnings("serial")
public class AudioPane extends JPanel {
	
	private ArrayList<Integer> killPID = new ArrayList<Integer>();
	private double talkSpeed = 1.0;
	
	private static JTextArea txtrCommentary;
	
	protected static JButton btnMergeAt;
	private static JButton btnStop;
	private static JButton btnSpeak;
	private static JLabel lblEnterYourCommentary;
	private static JLabel lblPauseFirst;
	private static JLabel lblsaveFirst;
	private static JLabel lblSpeed;
	
	public AudioPane(final JFrame parent, Color theme) {
		setMinimumSize(new Dimension(300, 500));
		setBackground(Color.DARK_GRAY);
		setLayout(new GridLayout(0, 1, 0, 0));

		lblEnterYourCommentary = new JLabel("Enter Commentary here: (max 40 words)"); // Label to tell user what the text area is for
		lblEnterYourCommentary.setVerticalAlignment(SwingConstants.BOTTOM);
		lblEnterYourCommentary.setHorizontalAlignment(SwingConstants.LEFT);
		lblEnterYourCommentary.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblEnterYourCommentary.setForeground(theme);
		add(lblEnterYourCommentary);
				
		txtrCommentary = new JTextArea(); // TextArea for user to enter their commentary
		txtrCommentary.setRows(5);
		txtrCommentary.setLineWrap(true);
		txtrCommentary.setWrapStyleWord(true);
		add(txtrCommentary);
		
		final JSlider speedChooser = new JSlider(0, 20, 10);
		speedChooser.addChangeListener(new ChangeListener() {
		     @Override
		     public void stateChanged(ChangeEvent e) {
		     	// Change the volume of the video to the value obtained from the slider
		    	 talkSpeed = (double)speedChooser.getValue() / 10;
		    	 if(talkSpeed == 0.0) {
		    		 talkSpeed = 0.1;
		    	 }
		     }
		});
		speedChooser.setEnabled(false);
		
		JPanel audio_options = new JPanel();
		audio_options.setBackground(Color.DARK_GRAY);
		add(audio_options);
		
		btnSpeak = new JButton("Speak");
		btnSpeak.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Speak commentary to the user through festival text-to-speech
				killPID.removeAll(killPID);
				AudioTools.createFestivalScheme(txtrCommentary.getText(), talkSpeed, false);
				BgFestival bg = new BgFestival(killPID);
				bg.execute();
				btnStop.setEnabled(true);
				btnSpeak.setEnabled(false);
			}
		});
		btnSpeak.setEnabled(false);
		audio_options.add(btnSpeak);
		
		btnStop = new JButton("Stop");
		btnStop.addActionListener(new StopListener(killPID));
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
	   				JDialog mp3Name = new MP3Prompt(commentary, talkSpeed);
	   				mp3Name.setVisible(true);
	   			} else {
	   				JOptionPane.showMessageDialog(parent, "Please only have between 1 and 40 words. Please try again.", "Too many words", JOptionPane.ERROR_MESSAGE);
	   			}
			}
		});
		btnSaveAs.setEnabled(false);
		audio_options.add(btnSaveAs);
		
		lblSpeed = new JLabel("Talking Speed:");
		lblSpeed.setForeground(theme);
		audio_options.add(lblSpeed);
		
		speedChooser.setBackground(Color.DARK_GRAY);
		speedChooser.setPaintLabels(true);
		
		// https://docs.oracle.com/javase/tutorial/uiswing/components/slider.html
		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		JLabel lblFast = new JLabel("Fast");
		lblFast.setForeground(Color.white);
		labelTable.put( 0, lblFast);
		JLabel lblNormal = new JLabel("Normal");
		lblNormal.setForeground(Color.white);
		labelTable.put( 10, lblNormal);
		JLabel lblSlow = new JLabel("Slow");
		lblSlow.setForeground(Color.white);
		labelTable.put( 20, lblSlow);
		speedChooser.setLabelTable(labelTable);
		
		speedChooser.setPaintTicks(true);
		speedChooser.setMajorTickSpacing(10);
		speedChooser.setMajorTickSpacing(1);

		audio_options.add(speedChooser);
		
		txtrCommentary.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				if(txtrCommentary.getText().length() > 0) {
					btnSpeak.setEnabled(true);
					btnSaveAs.setEnabled(true);
					speedChooser.setEnabled(true);
				} else {
					btnSpeak.setEnabled(false);
					btnSaveAs.setEnabled(false);
					speedChooser.setEnabled(false);
				}
			}
			public void removeUpdate(DocumentEvent e) {
				if(txtrCommentary.getText().length() > 0) {
					btnSpeak.setEnabled(true);
					btnSaveAs.setEnabled(true);
					speedChooser.setEnabled(true);
				} else {
					btnSpeak.setEnabled(false);
					btnSaveAs.setEnabled(false);
					speedChooser.setEnabled(false);
				}
			}
			public void changedUpdate(DocumentEvent e) {
			}
		});
		
		JPanel merge_Panel = new JPanel();
		merge_Panel.setBackground(Color.DARK_GRAY);
		add(merge_Panel);
		merge_Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblsaveFirst = new JLabel("Save commentary before merging");
		lblsaveFirst.setForeground(theme);
		merge_Panel.add(lblsaveFirst);
		
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
		
		lblPauseFirst = new JLabel("Please pause video at desired position");
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
	
	public static void setTheme(Color c) {
		// Changes the theme (color) of the progress bar
		lblEnterYourCommentary.setForeground(c);
		lblPauseFirst.setForeground(c);
		lblsaveFirst.setForeground(c);
		lblSpeed.setForeground(c);
	}

	public static void disableCancel() {
		// Disables the cancel button when the festival process is done
		btnStop.setEnabled(false);
		btnSpeak.setEnabled(true);
	}
	
	public static String getTextArea() {
		return txtrCommentary.getText();
	}
	
	public static void writeToTextArea(String comment) {
		txtrCommentary.setText(comment);
	}
}
