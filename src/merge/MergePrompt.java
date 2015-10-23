package merge;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import universalMethods.Utility;
import videoPlayer.components.VideoPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author Rebecca Lee
 * Class asks users for a name for the merged video, which video to merge, and at what time. After all that info has been obtained,
 * the merge button will become enabled and the video will merge.
 */
@SuppressWarnings("serial")
public class MergePrompt extends JDialog {

	private String audioPath;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public MergePrompt(final long time) {
		setTitle("Merge an Audio");
		setBounds(350, 250, 485, 270);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		final JDialog thisDialog = this;
		final JButton btnMerge = new JButton("Merge");
		
		JLabel lblMergeSettings = new JLabel("Merge Settings");
		lblMergeSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblMergeSettings.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblMergeSettings.setBounds(35, 10, 414, 25);
		contentPanel.add(lblMergeSettings);
		
		JLabel lblVideoName = new JLabel("New Video Name:");
		lblVideoName.setHorizontalAlignment(SwingConstants.RIGHT);
		lblVideoName.setBounds(50, 41, 200, 50);
		contentPanel.add(lblVideoName);
		
		// This JTextField initializes with a name myVideo with number corresponding to the number of files already at destination folder 
		final JTextField txtName = new JTextField("myVideo" + Utility.fileNumber("./VideoFiles"));
		txtName.setBounds(255, 58, 100, 20);
		contentPanel.add(txtName);
		txtName.setColumns(15);
		
		JLabel lblVideoToBe = new JLabel("The Audio will be merged with the current video.");
		lblVideoToBe.setHorizontalAlignment(SwingConstants.CENTER);
		lblVideoToBe.setBounds(20, 82, 420, 50);
		contentPanel.add(lblVideoToBe);
		
		JLabel lblMpToBe = new JLabel("Audio to be merged:");
		lblMpToBe.setBounds(20, 128, 150, 50);
		contentPanel.add(lblMpToBe);
		
		final JTextField txtAudioPath = new JTextField();
		txtAudioPath.setBounds(174, 143, 182, 20);
		contentPanel.add(txtAudioPath);
		txtAudioPath.setColumns(10);
		
		JButton btnAudioBrowse = new JButton("Browse");
		btnAudioBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Browse button for audio file.
				JFileChooser audioChooser = new JFileChooser(System.getProperty("user.dir") + "/MP3Files/");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio Files (*.mp3)", "mp3");
				audioChooser.setFileFilter(filter);
				int okReturnVal = audioChooser.showDialog(getParent(), "Choose Audio");
				if(okReturnVal == JFileChooser.APPROVE_OPTION) {
					audioPath = audioChooser.getSelectedFile().getPath();
				  	txtAudioPath.setText(audioPath);
				}
			}
		});
		btnAudioBrowse.setBounds(366, 140, 89, 23);
		contentPanel.add(btnAudioBrowse);
		
		// Below are components for the time at which the audio is going to be merged
		JLabel lblMergeTime = new JLabel("Merge at:");
		lblMergeTime.setBounds(134, 184, 89, 23);
		contentPanel.add(lblMergeTime);
		
		JLabel lblColon = new JLabel(":");
		lblColon.setBounds(252, 184, 20, 23);
		contentPanel.add(lblColon);
		
		int secs = (int)(time/1000) % 60; // Initialize the GUI with the moment in the in the video
		int mins = (int)((time/1000)-secs)/60;
		
		SpinnerModel model1 = new SpinnerNumberModel(mins, 0, 59, 1);
		final JSpinner minuteSpinner = new JSpinner();
		minuteSpinner.setBounds(209, 186, 40, 20);
		minuteSpinner.setModel(model1);
		contentPanel.add(minuteSpinner);
		
		SpinnerModel model2 = new SpinnerNumberModel(secs, 0, 59, 1);
		final JSpinner secondSpinner = new JSpinner();
		secondSpinner.setBounds(259, 186, 40, 20);
		secondSpinner.setModel(model2);
		contentPanel.add(secondSpinner);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				// Before merging check that there are no user errors.
				String name = txtName.getText();
				audioPath = txtAudioPath.getText();
				
				if(!Utility.isAlphaNumeric(name)) { // If the video name contains invalid characters
					JOptionPane.showMessageDialog(thisDialog, "File name can only contain alphanumeric characters. (a-z,A-Z,0-9)", "Invalid name", JOptionPane.ERROR_MESSAGE);
				} else if(!Utility.isMp3(audioPath)) { // The file chosen is not an audio file.
					JOptionPane.showMessageDialog(thisDialog, "The file you have chosen is not an audio file.", "Invalid audio file", JOptionPane.ERROR_MESSAGE);
				} else {
					int totalSeconds = (int)minuteSpinner.getValue() * 60 + (int)secondSpinner.getValue();
					// Check that the video name doesn't already exist
					File newVideo = new File("VideoFiles/" + name + ".avi");
					if(newVideo.exists()) {
						// Display an error dialog
						JOptionPane.showMessageDialog(thisDialog, "The name you have chosen already exists, please choose another.", "Duplicate name", JOptionPane.ERROR_MESSAGE);
					} else  if(VideoPane.getVideoLength() < totalSeconds) { 
						// Time chosen exceeds the video length
						JOptionPane.showMessageDialog(thisDialog, "Your chosen time exceeds video length, audio cannot be merged.", "Invalid time", JOptionPane.ERROR_MESSAGE);
					} else {
						// Merge the videos with current settings
						WaitProcessBar wait = new WaitProcessBar();
						wait.setVisible(true);
						BgMerge merger = new BgMerge(name, VideoPane.getCurrentVideoPath(), audioPath, totalSeconds*1000, wait);
						merger.execute();
						thisDialog.dispose();
					}
				}
				
			}
		});
		btnMerge.setActionCommand("OK");
		btnMerge.setEnabled(false);
		buttonPane.add(btnMerge);
		getRootPane().setDefaultButton(btnMerge);
		
		DocumentListener MyDocListener = new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				// Make sure both lines are none empty to enable and disable the merge function
				if(txtName.getText().length() > 0 && txtAudioPath.getText().length() > 0) {
					btnMerge.setEnabled(true);
				} else {
					btnMerge.setEnabled(false);
				}
			}
			public void removeUpdate(DocumentEvent e) {
				// Make sure both lines are none empty
				if(txtName.getText().length() > 0 && txtAudioPath.getText().length() > 0) {
					btnMerge.setEnabled(true);
				} else {
					btnMerge.setEnabled(false);
				}
			}
			public void changedUpdate(DocumentEvent e) {
			}
		};
		
		txtName.getDocument().addDocumentListener(MyDocListener);
		txtAudioPath.getDocument().addDocumentListener(MyDocListener);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisDialog.dispose();
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);
	}
}
