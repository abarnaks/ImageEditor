package ie;

import java.awt.*;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;

import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ie.Brush;
import ie.CustomUI;
import ie.Model;
import ie.DrawingAreaPanel;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.JFileChooser;

import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Panel;

public class Editor implements ChangeListener, ActionListener {

	private JFrame frmImageeditor;
	
	JPanel colorPlate;
	
	CustomUI customUI;
	
	int state;
	final int BRUSH = 0;
	
	DrawingAreaPanel canvas;
	
	JPanel canvasPanel;
	
	private static String lastOpenDir = null;
	
	final Color	 BACKGOUND_COLOR = new Color(212, 212, 212);//Color.BLACK;
	
	Brush brush		= new Brush();
	//Brush pencil	= new Brush();
	//Brush eraser	= new Brush();
	
	JButton inkColor, canvasColor;
	
	JButton black, white, dGray, gray, lGray, green, blue, cyan,
	magenta, orange, purple, red, yellow, brown;

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}	
	//do we need to make image panel static ??
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Editor window = new Editor();
					window.frmImageeditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Editor() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmImageeditor = new JFrame();
		frmImageeditor.setTitle("ImageEditor 1.0");
		frmImageeditor.setBounds(100, 100, 851, 489);
		frmImageeditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmImageeditor.getContentPane().setLayout(new BoxLayout(frmImageeditor.getContentPane(), BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmImageeditor.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		panel.add(splitPane, BorderLayout.CENTER);
		
		//toolbar starts off hidden - it shows up once you click on the divider
		splitPane.setDividerLocation(0.2);
		
		// the tools at the side
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		splitPane.setLeftComponent(tabbedPane);
		
		JPanel ci = new JPanel();
		ci.setLayout(new GridLayout(1,1));
		ci.add(Crop());
		ci.repaint();
		ci.revalidate();
		tabbedPane.add(ci, "Crop Image");
		
		JPanel bi = new JPanel();
		bi.setLayout(new GridLayout(1,1));
		bi.add(Bright());
		bi.repaint();
		bi.revalidate();
		tabbedPane.add(bi, "Edit Brightness");
		
		JPanel col = new JPanel();
		//layout??
		col.add(Colour());
		col.repaint();
		col.revalidate();
		tabbedPane.add(col, "Edit Colour");
		
		JPanel txt = new JPanel();
		txt.add(Text());
		txt.repaint();
		txt.revalidate();
		tabbedPane.add(txt, "Add Text");
		
		JPanel blur = new JPanel();
		//layout?
		blur.add(Blur());
		blur.repaint();
		blur.revalidate();
		tabbedPane.add(blur, "Blur image");
		
		JPanel filt = new JPanel();
		//layout?
		filt.add(Filter());
		filt.repaint();
		filt.revalidate();
		tabbedPane.add(filt, "Apply a filter");
		//alignment
		tabbedPane.setTabPlacement(JTabbedPane.LEFT);
		
		
		JPanel panelUser = new JPanel();
		panelUser.setLayout(new GridBagLayout());
		JScrollPane pu = new JScrollPane(panelUser);
		splitPane.setRightComponent(pu);
		
		//Toolbar with icons
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);
		
		Image openIcon = new ImageIcon(this.getClass().getResource("/open.PNG")).getImage();
		JButton btnOpen = new JButton();
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openImage(panelUser);
			}
		});
		btnOpen.setIcon(new ImageIcon(openIcon));
		toolBar.add(btnOpen);
		
		Image saveIcon = new ImageIcon(this.getClass().getResource("/save.PNG")).getImage();
		JButton btnSave = new JButton();
		btnSave.setIcon(new ImageIcon(saveIcon));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		toolBar.add(btnSave);
		
		Image undoIcon = new ImageIcon(this.getClass().getResource("/undo.png")).getImage();
		
		Image redoIcon = new ImageIcon(this.getClass().getResource("/redo.png")).getImage();
		JButton btnUndo = new JButton();
		btnUndo.setIcon(new ImageIcon(undoIcon));
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JSeparator separator = new JSeparator();
		separator.setMaximumSize(new Dimension(5, 20));
		separator.setOrientation(SwingConstants.VERTICAL);
		toolBar.add(separator);
		
		toolBar.add(btnUndo);
		JButton btnRedo = new JButton();
		btnRedo.setIcon(new ImageIcon(redoIcon));
		toolBar.add(btnRedo);
		

		// Top menu bar --- file menu etc
	
		JMenuBar menuBar = new JMenuBar();
		frmImageeditor.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openImage(panelUser);
			}	
				
		});
		mnNewMenu.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnNewMenu.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnNewMenu.add(mntmSaveAs);
		
		JMenuItem mntmExport = new JMenuItem("Export");
		mnNewMenu.add(mntmExport);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenuItem mntmUndo = new JMenuItem("Undo");
		mnEdit.add(mntmUndo);
		
		JMenuItem mntmRedo = new JMenuItem("Redo");
		mnEdit.add(mntmRedo);
		
		JMenuItem mntmCut = new JMenuItem("Cut");
		mnEdit.add(mntmCut);
		
		JMenuItem mntmCopy = new JMenuItem("Copy");
		mnEdit.add(mntmCopy);
		
		JMenuItem mntmPaste = new JMenuItem("Paste");
		mnEdit.add(mntmPaste);
		
		JMenu mnTransform = new JMenu("Transform");
		mnEdit.add(mnTransform);
		
		JMenuItem mntmRotateLeft = new JMenuItem("Rotate 90\u00B0 left");
		mnTransform.add(mntmRotateLeft);
		
		JMenuItem mntmRotateRight = new JMenuItem("Rotate 90\u00B0 right");
		mnTransform.add(mntmRotateRight);
		
		JMenuItem mntmFlipHorizontal = new JMenuItem("Flip horizontal");
		mnTransform.add(mntmFlipHorizontal);
		
		JMenuItem mntmFlipVertical = new JMenuItem("Flip vertical");
		mnTransform.add(mntmFlipVertical);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenuItem mntmZoomIn = new JMenuItem("Zoom in");
		mnView.add(mntmZoomIn);
		
		JMenuItem mntmZoomOut = new JMenuItem("Zoom out");
		mnView.add(mntmZoomOut);
		
		JMenuItem mntmMagnifier = new JMenuItem("Magnifier");
		mnView.add(mntmMagnifier);
		
		JMenu mnPerspective = new JMenu("Perspective");
		mnView.add(mnPerspective);
		
		JCheckBoxMenuItem chckbxmntmDefault = new JCheckBoxMenuItem("Default");
		mnPerspective.add(chckbxmntmDefault);
		
		JCheckBoxMenuItem chckbxmntmLayers = new JCheckBoxMenuItem("Layers");
		mnPerspective.add(chckbxmntmLayers);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		JMenuItem mntmEditorElementsHelp = new JMenuItem("Editor elements help");
		mnHelp.add(mntmEditorElementsHelp);
		
		JMenuItem mntmTutorials = new JMenuItem("Tutorials");
		mnHelp.add(mntmTutorials);
	}

	//method to open image
	public void openImage(JPanel panelUser) {
		//if(lastOpenDir == null) {
			JFileChooser fc = new JFileChooser();
			//set filter for images only
			
			int selFile = fc.showOpenDialog(null);
			if (selFile == JFileChooser.APPROVE_OPTION) {
				File img = fc.getSelectedFile();
				lastOpenDir = img.getParent();
				if (img.getName().endsWith(".png") || img.getName().endsWith(".PNG") || img.getName().endsWith(".jpg") || img.getName().endsWith(".JPG")|| img.getName().endsWith(".raw")) {
					//load image onto interface	
					panelUser.add(new LoadImageApp(img));  
					panelUser.repaint();
					panelUser.revalidate();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid type of image file", "Image File", JOptionPane.WARNING_MESSAGE);
				}

			//}
			
		/*} else if (lastOpenDir != null) {
			JFileChooser fc = new JFileChooser();
			//set filter for images only
			int selFile = fc.showOpenDialog(null);
			if(selFile == JFileChooser.APPROVE_OPTION) {
				File img = fc.getSelectedFile();
				lastOpenDir = img.getParent();
				
				//load image onto interface
				panelUser.add(new LoadImageApp(img), BorderLayout.CENTER);  
				panelUser.repaint();
				panelUser.revalidate();
			}*/
		}
	}
	
	//function to load image
	public class LoadImageApp extends Component {
		BufferedImage image;
		
		public void paint(Graphics g) {
			g.drawImage(image, 0, 0, null);
		}
		
		public LoadImageApp(File i) {
			try {
				image = ImageIO.read(i);
			} catch (IOException e) {
				
			}
		}
		
		public Dimension getPreferredSize() {
			if (image == null) {
				return new Dimension(100,100);
			} else {
				return new Dimension(image.getWidth(), image.getHeight());
			}
		}
	
	}
	
	
	//// sub panels for each tab 	
	public JPanel Crop() {
		
		JPanel cPanel = new JPanel();
		cPanel.setLayout(new GridLayout(9,1));
		
		JRadioButton rect = new JRadioButton("Rectangular form");
		cPanel.add(rect);
		
		JRadioButton free = new JRadioButton("Free form");
		cPanel.add(free);
		
		cPanel.repaint();
		cPanel.revalidate();
		return cPanel;
		
	}
	
	public JPanel Bright() {
		
		JPanel bPanel = new JPanel();
		bPanel.setLayout(new GridLayout(6,1));
		
		JLabel blabel = new JLabel("Brightness of Image", JLabel.CENTER);
		bPanel.add(blabel);
		
		JSlider brightValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		
		brightValue.addChangeListener((ChangeListener) this);	//use this event to edit the picture
			//figure out what to do with changelistener
		
		brightValue.setMajorTickSpacing(10);
		brightValue.setMinorTickSpacing(1);
		brightValue.setPaintTicks(true);
		brightValue.setPaintLabels(true);
		
		bPanel.add(brightValue);
		return bPanel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//major issue
	protected void updateInkColor(Color c){
		brush.setColor(c);
		//inkColor.setBkacground(c);
		customUI.setBrushColor(c);
	}
	
	protected void updateCanvasColor(Color c)
	{
	//	eraser.setColor(c);
		canvasColor.setBackground(c);
	}
	
	private JButton colorButton(final Color c) {
		JButton b = new JButton();		
		b.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateInkColor(c);
				
			}
		});
		Model.setComponentSize(b, 16, 16);
		b.setBackground(c);
		colorPlate.add(b);
		return b;
	}
	
	public JPanel Colour() {
		
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(4,1));
		
		JPanel newPanel = new JPanel();
		JScrollPane pu = new JScrollPane(colPanel);
		
		JLabel col = new JLabel("Choose colour...");
		colPanel.add(col);
		//everything about color goes here
		setState(BRUSH);
		
		canvas	 = new DrawingAreaPanel();
		
		inkColor = new JButton();//color of the ink being used
		canvasColor = new JButton();//color of the canvas where the ink is placed
		
		customUI = new CustomUI(BACKGOUND_COLOR, brush.getColor());
		//
		this.updateInkColor(Color.BLACK);
		brush.setSize(8.0f);
		
		//this.updateCanvasColor(Color.WHITE);
		//eraser.setSize(16.0f);
		
		colorPlate = new JPanel(new GridLayout(7, 2, 2, 2));
		colorPlate.setBackground(BACKGOUND_COLOR);
		//colorPlate.setBounds(8, 86, 64, 212);

		//WILL NEED THIS TOO
		inkColor.setBackground(brush.getColor());
		//inkColor.setBounds(8, 18, 64, 64);
		inkColor.addActionListener(this);
		Model.setComponentSize(inkColor, 64, 64);
		
		//inkColor.setUI(customUI);
		
		black	= colorButton(Color.BLACK);
		brown	= colorButton(new Color(139,69,19));

		dGray	= colorButton(Color.DARK_GRAY);
		orange	= colorButton(Color.ORANGE);

		gray	= colorButton(Color.GRAY);
		yellow	= colorButton(Color.YELLOW);

		lGray	= colorButton(Color.LIGHT_GRAY);
		green	= colorButton(Color.GREEN);

		white	= colorButton(Color.WHITE);
		cyan	= colorButton(Color.CYAN);

		magenta	= colorButton(Color.MAGENTA);
		blue	= colorButton(Color.BLUE);

		red		= colorButton(Color.RED);
		purple	= colorButton(new Color(138,43,226));//Color.PINK);

		
		JPanel colorPanel = new JPanel();
		colorPanel.setBorder(new TitledBorder(new EtchedBorder(), "Colors"));
		colorPanel.setBackground(BACKGOUND_COLOR);
		colorPanel.add(inkColor);
		colorPanel.add(colorPlate);		
		Model.setComponentSize(colorPanel, 80, 304);
		
		canvas.setLayout(new BorderLayout());
		//canvas.setBounds(0, 0, 1024, 768);

		//fix the panel
		//canvasPanel = new JPanel();
		//canvasPanel.setBackground(BACKGOUND_COLOR);
		//canvasPanel.add(canvas);
		//Model.setComponentSize(canvasPanel, 1024, 768);
		//
		
		JLabel grad = new JLabel("Gradient...");
		colPanel.add(grad);
		colPanel.add(colorPlate);
		
		colPanel.add(canvasPanel);
		newPanel.add(pu);
		
		return newPanel;
	}
	
	public JPanel Text() {
		
		JPanel tPanel = new JPanel();
		tPanel.setLayout(new GridLayout(5,1));
		
		JLabel addt = new JLabel("Add text to image");
		tPanel.add(addt);
		
		JTextField textToAdd = new JTextField(15);
		tPanel.add(textToAdd);
		
		return tPanel;
		
	}
	
	public JPanel Blur() {
		JPanel blPanel = new JPanel();
		blPanel.setLayout(new GridLayout(5,1));
		
		JLabel what = new JLabel("what to do here");
		blPanel.add(what);
		
		return blPanel;
	}
	
	public JPanel Filter() {
		JPanel fPanel = new JPanel();
		fPanel.setLayout(new GridLayout(5,1));
		
		JLabel what = new JLabel("what to do here");
		fPanel.add(what);
		
		return fPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
