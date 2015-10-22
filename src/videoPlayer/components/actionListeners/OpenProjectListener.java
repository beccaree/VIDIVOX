package videoPlayer.components.actionListeners;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import universalMethods.Utility;
import videoPlayer.components.AudioPane;
import videoPlayer.components.MyMenuBar;

/**
 * @author Rebecca Lee 
 * Contains action performed method for when opening a project is clicked from the file menu
 */
public class OpenProjectListener implements ActionListener {

	private JFrame parent;
	private MyMenuBar menu;
	
	public OpenProjectListener(JFrame parent, MyMenuBar menu) {
		this.parent = parent;
		this.menu = menu;
	}

	public void actionPerformed(ActionEvent e) {
		// Open a saved project
		JFileChooser projectChooser = new JFileChooser(System.getProperty("user.dir") + "/Projects/");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Vidivox Project Files (*.vdp)", "vdp");
		projectChooser.setFileFilter(filter);
		int okReturnVal = projectChooser.showOpenDialog(parent);
		if(okReturnVal == JFileChooser.APPROVE_OPTION) {
			String filePath = projectChooser.getSelectedFile().getPath();
		  	
			if (Utility.isProject(filePath)) {
				try {
					FileReader fr = new FileReader(filePath);
					BufferedReader br = new BufferedReader(fr);

					String videoPath = br.readLine();
					// Set current video path to saved path
					menu.updateVideo(videoPath);
					
					Color theme = new Color(Integer.parseInt(br.readLine()));
					// Set current theme to saved theme
					menu.updateTheme(theme);
					
					int skipInterval = Integer.parseInt(br.readLine());
					// Set current skip interval
					menu.updateSkipInt(skipInterval);
					
					String comment = br.readLine();
					// Set text area to comment saved
					AudioPane.writeToTextArea(comment);

					br.close();

				} catch (FileNotFoundException e1) {
					JOptionPane.showMessageDialog(parent, "The file you chose cannot be found, check that it exists.", "File not found", JOptionPane.ERROR_MESSAGE);
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(parent, "The file you have chosen is in the wrong format, please check that it is a VIDIVOX project file (.vdp)", "Invalid file", JOptionPane.ERROR_MESSAGE);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(parent, "The project file you have chosen could not be loaded.", "Invalid file", JOptionPane.ERROR_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(parent, "The file you have chosen is in the wrong format, please check that it is a VIDIVOX project file (.vdp)", "Invalid file", JOptionPane.ERROR_MESSAGE);
			}
			
		}
	}

}
