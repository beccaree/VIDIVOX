package videoPlayer.BGTasks;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import universalMethods.Utility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class that contains user interface and implementation that prompts the user to name their mp3 and video 
 */
@SuppressWarnings("serial")
public class MP3Prompt extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public MP3Prompt(final String comment, final double speed) {
		setBounds(350, 250, 450, 220);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		final JDialog thisDialog = this;
		
		contentPanel.setLayout(null);
        
        JLabel lblNameYour = new JLabel("Name your MP3 file");
        lblNameYour.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNameYour.setHorizontalAlignment(SwingConstants.CENTER);
        lblNameYour.setBounds(75, 44, 275, 40);
        contentPanel.add(lblNameYour);
        
        JLabel lblMpName = new JLabel("Name:");
        lblMpName.setBounds(90, 110, 110, 20);
        contentPanel.add(lblMpName);
        
        final JTextField textField = new JTextField("myComment" + Utility.fileNumber("./MP3Files"));
        textField.setBounds(170, 110, 150, 20);
        contentPanel.add(textField);
        textField.setColumns(10);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		final JButton okButton = new JButton("Make MP3");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				// Check that name is not blank or contains invalid characters, name can only have a-z,A-Z,_,- or 0-9
				if(name.length() == 0) {
					JOptionPane.showMessageDialog(thisDialog, "You didn't enter anything.", "Empty name", JOptionPane.ERROR_MESSAGE);
				} else if(!Utility.isAlphaNumeric(name)) {
					JOptionPane.showMessageDialog(thisDialog, "File name can only contain alphanumeric characters. (a-z,A-Z,0-9)", "Invalid name", JOptionPane.ERROR_MESSAGE);
				} else {
					// Check if name already exists
					File newMP3 = new File("MP3Files/" + name + ".mp3");
					if(newMP3.exists()) {
						// Display an error dialog
						JOptionPane.showMessageDialog(thisDialog, "The name you have chosen already exists, please choose another.", "Duplicate name", JOptionPane.ERROR_MESSAGE);
					} else {
						// Create the MP3 file from the commentary
						Utility.saveAsMp3(comment, name, speed);
						JOptionPane.showMessageDialog(thisDialog, name + ".mp3 has been created in MP3Files");
					
						thisDialog.dispose();
					}
				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void insertUpdate(DocumentEvent e) {
				// Make sure textField is none empty
				if(textField.getText().length() > 0) {
					okButton.setEnabled(true);
				} else {
					okButton.setEnabled(false);
				}
			}
			public void removeUpdate(DocumentEvent e) {
				// Make sure textField is none empty
				if(textField.getText().length() > 0) {
					okButton.setEnabled(true);
				} else {
					okButton.setEnabled(false);
				}
			}
			public void changedUpdate(DocumentEvent e) {
			}
		});
		
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
