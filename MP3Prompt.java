package videoPlayer;
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

import universalMethods.Utility;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class that contains user interface and implementation that prompts the user to name their mp3 and video 
 */
public class MP3Prompt extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Create the dialog.
	 */
	public MP3Prompt(final String comment) {
		setBounds(350, 250, 450, 300);
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
        lblMpName.setBounds(90, 130, 110, 20);
        contentPanel.add(lblMpName);
        
        final JTextField textField = new JTextField();
        textField.setBounds(170, 130, 150, 20);
        contentPanel.add(textField);
        textField.setColumns(10);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = textField.getText();
				String[] words = name.split(" ");
				// Check that name is not blank and/or more than 1 word
				if(name.length() == 0) {
					JOptionPane.showMessageDialog(thisDialog, "You didn't enter anything.", "Empty name", JOptionPane.ERROR_MESSAGE);
				} else if (words.length >1) {
					JOptionPane.showMessageDialog(thisDialog, "You cannot have spaces in a file name.", "Invalid name", JOptionPane.ERROR_MESSAGE);
				} else {
					// Check if name already exists
					File newMP3 = new File("MP3Files/" + name + ".mp3");
					if(newMP3.exists()) {
						// Display an error dialog
						JOptionPane.showMessageDialog(thisDialog, "The name you have chosen already exists, please choose another.", "Duplicate name", JOptionPane.ERROR_MESSAGE);
					} else {
						// Create the MP3 file from the commentary
						Utility.saveAsMp3(comment, name);
						JOptionPane.showMessageDialog(thisDialog, name + ".mp3 has been created in MP3Files");
					
						thisDialog.dispose();
					}
				}
			}
		});
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
		
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
