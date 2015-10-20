package start;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Font;
import java.io.File;

import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;

import universalMethods.Utility;
import videoPlayer.MainFrame;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * Class contains implementation and graphical user interface code for the starting frame.
 */
@SuppressWarnings("serial")
public class StartFrame extends JFrame {

	private JPanel contentPane;
	
	private JTextField txtVideoPath;
	private String videoPath;
	boolean isVideo = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		// Create the necessary directories for this program
		File audio = new File("./MP3Files");
		audio.mkdirs();
		
		File video = new File("./VideoFiles");
		video.mkdirs();
		
		File project = new File("./Projects");
		project.mkdirs();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 200, 500, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		final JFrame thisFrame = this;
		
		JLabel lblChooseYourVideo = new JLabel("Choose Your Video");
		lblChooseYourVideo.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblChooseYourVideo.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblChooseYourVideo, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		
		txtVideoPath = new JTextField();
		txtVideoPath.setText("/VideoFiles/");
		panel_1.add(txtVideoPath);
		txtVideoPath.setColumns(30);
		
		JButton btnBrowse = new JButton("Browse");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// Allows user to choose their own video file to play
				JFileChooser videoChooser = new JFileChooser(System.getProperty("user.dir") + "/VideoFiles/");
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Video Files (*.avi) or (*.mp4)", "avi", "mp4");
				videoChooser.setFileFilter(filter);
				int okReturnVal = videoChooser.showOpenDialog(getParent());
				if(okReturnVal == JFileChooser.APPROVE_OPTION) {
					videoPath = videoChooser.getSelectedFile().getPath();
				  	txtVideoPath.setText(videoPath);
				}
				
			}
		});
		panel_1.add(btnBrowse);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		
		final JCheckBox chckbxDefaultVid = new JCheckBox("Use Bunny Video"); // Tick if user wants to use the big buck bunny video
		panel_2.add(chckbxDefaultVid);
		
		JPanel themeChooser = new JPanel();
		themeChooser.setLayout(new BoxLayout(themeChooser, BoxLayout.X_AXIS));
		panel.add(themeChooser);
		
		JLabel lblTheme = new JLabel("Theme:");
		themeChooser.add(lblTheme);
		
		final JCheckBox chckbxPink = new JCheckBox("Pink");
		final JCheckBox chckbxBlue = new JCheckBox("Blue");
		chckbxPink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxBlue.isSelected()) {
					chckbxBlue.setSelected(false);
				} else {
					chckbxBlue.setSelected(true);
				}
			}
		});
		chckbxPink.setForeground(Color.pink);
		chckbxPink.setSelected(true);
		themeChooser.add(chckbxPink);
		
		chckbxBlue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(chckbxPink.isSelected()) {
					chckbxPink.setSelected(false);
				} else {
					chckbxPink.setSelected(true);
				}
			}
		});
		chckbxBlue.setForeground(Color.cyan);
		themeChooser.add(chckbxBlue);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Ok");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color theme;
				if(chckbxPink.isSelected()) {
					theme = Color.pink;
				} else {
					theme = Color.cyan;
				}
				
				if(chckbxDefaultVid.isSelected()) { // If the user chooses to use the bunny video
					thisFrame.dispose();
					new MainFrame("./VideoFiles/bunny.avi", theme);
				} else if(Utility.isVideo(videoPath)) { // If the user has chosen a video
					thisFrame.dispose();
					new MainFrame(videoPath, theme);	
				} else { // If the user has not chosen a video
					// Display an error dialog
					JOptionPane.showMessageDialog(thisFrame, "The file you have chosen is not a video, please try again.");
				}

			}
		});
		panel_4.add(btnNewButton);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				thisFrame.dispose();
			}
		});
		panel_4.add(btnCancel);
	}

}