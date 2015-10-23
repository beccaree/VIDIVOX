package videoPlayer.components.actionListeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import universalMethods.Utility;
import videoPlayer.MainFrame;
import videoPlayer.components.AudioPane;
import videoPlayer.components.MyMenuBar;
import videoPlayer.components.VideoPane;

/**
 * @author Rebecca Lee 
 * Contains action performed method for when saving a project is clicked from the file menu
 */
public class SaveProjectListener implements ActionListener {
	
	private JFrame parent;
	private MyMenuBar menu;

	public SaveProjectListener(JFrame parent, MyMenuBar menu) {
		this.parent = parent;
		this.menu = menu;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		// Save current project
		JFileChooser projectSaver = new JFileChooser(System.getProperty("user.dir") + "/Projects/");
		// Extension that I made up to distinguish from normal text files => ViDivox Project
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Vidivox Project Files (*.vdp)", "vdp");
		projectSaver.setFileFilter(filter);
		File newProject = new File("./Projects/vidi_project" + Utility.fileNumber("./Projects") + ".vdp");
		projectSaver.setSelectedFile(newProject);
		int okReturnVal = projectSaver.showDialog(parent, "Save Project");
		if(okReturnVal == JFileChooser.APPROVE_OPTION) {
			BufferedWriter bw;
			try {
				
				String newProjectPath = projectSaver.getSelectedFile().getPath();
				if(!Utility.isProject(newProjectPath)) {
					newProjectPath += ".vdp";
				}
				bw = new BufferedWriter(new FileWriter(new File(newProjectPath)));
				// Write current video
				bw.write(VideoPane.getCurrentVideoPath());
				// Write current theme
				bw.write("\n" + Integer.toString((MainFrame.getCurrentTheme()).getRGB()));
				// Write skip interval
				bw.write("\n" + menu.getSkipInt());
				// Write the text in the text area
				bw.write("\n" + AudioPane.getTextArea());
				
				JOptionPane.showMessageDialog(parent, newProjectPath.substring(newProjectPath.lastIndexOf('/')+1, newProjectPath.length()) + " has been saved to the Projects folder");
				
				bw.close();
				
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(parent, "There was an error saving the file.", "Something went wrong", JOptionPane.ERROR_MESSAGE);
			}
		}
		
	}
}
