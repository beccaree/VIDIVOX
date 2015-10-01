package videoPlayer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import universalMethods.Utility;

import java.awt.GridLayout;

/**
 * @author Rebecca Lee (Isabel Zhuang - prototype)
 * This class contains all the user interface implementation and functionality of the video player.
 */
public class MainFrame extends JFrame {

	private JPanel contentPane;
	private String currentVideoPath;

	/**
	 * Create the frame.
	 */
	public MainFrame(String videoPath) {
		setTitle("VIDIVOX - Video/Audio Overlay Platform");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1000, 650);
		currentVideoPath = videoPath;
		
		// Top menu bar implementation -------------------------------------------------->
		JMenuBar menuBar = new MyMenuBar(this);
		setJMenuBar(menuBar);
		
		// Video player implementation -------------------------------------------------->
		JPanel videoPane = new VideoPane(videoPath, this);
		
		// Audio editing implementation ------------------------------------------------->
		JPanel audioPane = new AudioPane(this);
				
				
		// Adding the two different panels to the two sides of the split pane ---------------->
		JSplitPane splitPane = new JSplitPane();
		setContentPane(splitPane);
		splitPane.setResizeWeight(0.9); // Resizes the frames in a 8:2 ratio
		splitPane.setLeftComponent(videoPane);
		splitPane.setRightComponent(audioPane);
		splitPane.setDividerLocation(700 + splitPane.getInsets().left);
				
		this.setVisible(true);
		
		VideoPane.video.playMedia(currentVideoPath); // Play the video
		VideoPane.video.setVolume(50); // Set initial volume to 50 (same as JSlider default value)
				
		final int[] vidLength = {0}; // Initialize as array so final value can be changed
		while(vidLength[0] == 0) {
			vidLength[0] = (int)((VideoPane.video.getLength())/1000);
		}
				
		VideoPane.setMaxBar(vidLength[0]);
	}
}
