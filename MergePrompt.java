package merge;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

import universalMethods.Utility;
import videoPlayer.VideoPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

public class MergePrompt extends JDialog {

	private String audioPath;
	
	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public MergePrompt(final long time) {
		setBounds(350, 250, 485, 280);
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
		
		final JTextField txtName = new JTextField();
		txtName.setBounds(255, 58, 100, 20);
		contentPanel.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblVideoToBe = new JLabel("The Audio will be merged with the current video.");
		lblVideoToBe.setHorizontalAlignment(SwingConstants.CENTER);
		lblVideoToBe.setBounds(20, 97, 420, 50);
		contentPanel.add(lblVideoToBe);
		
		JLabel lblMpToBe = new JLabel("Audio to be merged:");
		lblMpToBe.setBounds(20, 148, 150, 50);
		contentPanel.add(lblMpToBe);
		
		final JTextField txtAudioPath = new JTextField();
		txtAudioPath.setBounds(174, 163, 182, 20);
		contentPanel.add(txtAudioPath);
		txtAudioPath.setColumns(10);
		
		JButton btnAudioBrowse = new JButton("Browse");
		btnAudioBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Browse button for audio file.
				JFileChooser videoChooser = new JFileChooser(System.getProperty("user.dir") + "/MP3Files/");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Audio Files (*.mp3)", "mp3");
				videoChooser.setFileFilter(filter);
				int okReturnVal = videoChooser.showOpenDialog(getParent());
				if(okReturnVal == JFileChooser.APPROVE_OPTION) {
					audioPath = videoChooser.getSelectedFile().getPath();
				  	txtAudioPath.setText(audioPath);
				}
			}
		});
		btnAudioBrowse.setBounds(366, 162, 89, 23);
		contentPanel.add(btnAudioBrowse);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// Before merging check that there are no user errors.
				String name = txtName.getText();
				audioPath = txtAudioPath.getText();
				String[] words = name.split(" ");
				
				if(words.length > 1) { // If the video name is longer than 1, name cannot have spaces.
					JOptionPane.showMessageDialog(thisDialog, "You cannot have spaces in a file name.", "Invalid name", JOptionPane.ERROR_MESSAGE);
				} else if(name.length() == 0) {
					JOptionPane.showMessageDialog(thisDialog, "You have not entered a file name.", "Empty name", JOptionPane.ERROR_MESSAGE);
				} else if(!Utility.isMp3(audioPath)) { // The file chosen is not an audio file.
					JOptionPane.showMessageDialog(thisDialog, "The file you have chosen is not an audio file.", "Invalid audio file", JOptionPane.ERROR_MESSAGE);
				} else {
					// Check that the video name doesn't already exist
					File newVideo = new File("VideoFiles/" + name + ".avi");
					if(newVideo.exists()) {
						// Display an error dialog
						JOptionPane.showMessageDialog(thisDialog, "The name you have chosen already exists, please choose another.", "Duplicate name", JOptionPane.ERROR_MESSAGE);
					} else {
						// Merge the videos with current settings
						WaitProcessBar wait = new WaitProcessBar();
						wait.setVisible(true);
						BgMerge merger = new BgMerge(name, VideoPane.getCurrentVideoPath(), audioPath, time, wait);
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
				// Make sure both lines are none empty
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
