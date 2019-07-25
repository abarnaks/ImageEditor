package ie;

import java.awt.EventQueue;
import java.awt.Font;

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
import javax.swing.JCheckBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ie.Editor.ColorComboItem;

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
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Panel;
import javax.swing.JComboBox;

public class Editor implements ChangeListener{

	private JFrame frmImageeditor;
	
	private static BufferedImage image;
	
	private static JPanel panelUser = new JPanel();
	
	private static Font f;
	private static int style; //Font style -- bold, italic, etc
	private static int numSize; //font size
	private static String[] fonts;
	

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
		JScrollPane tp = new JScrollPane(tabbedPane);
		splitPane.setLeftComponent(tp);
		
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
		tabbedPane.add(bi, "Edit Lighting");
		
		JPanel col = new JPanel();
		//layout??
		col.add(Colour());
		col.repaint();
		col.revalidate();
		tabbedPane.add(col, "Add Colour");
		
		JPanel txt = new JPanel();
		txt.add(Text());
		txt.repaint();
		txt.revalidate();
		tabbedPane.add(txt, "Add Text");
		
		JPanel ori = new JPanel();
		ori.add(Orient());
		ori.repaint();
		ori.revalidate();
		tabbedPane.add(ori, "Edit Orientation");
		
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
		mntmRotateLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (image != null) {
					image = rotate90(image, "left");
					panelUser.repaint();
				} else {
					//System.out.print("could not rotate");
					JOptionPane.showMessageDialog(null, "Action could not be completed.", "Action", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnTransform.add(mntmRotateLeft);
		
		JMenuItem mntmRotateRight = new JMenuItem("Rotate 90\u00B0 right");
		mntmRotateRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (image != null) {
					image = rotate90(image, "right");
					panelUser.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Action could not be completed.", "Action", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnTransform.add(mntmRotateRight);
		
		JMenuItem mntmFlipHorizontal = new JMenuItem("Flip horizontal");
		mntmFlipHorizontal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(image != null) {
					image = flipHorizontal(image);
					panelUser.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Action could not be completed.", "Action", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		mnTransform.add(mntmFlipHorizontal);
		
		JMenuItem mntmFlipVertical = new JMenuItem("Flip vertical");
		mntmFlipVertical.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(image != null) {
					image = flipVertical(image);
					panelUser.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Action could not be completed.", "Action", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
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
				if (img.getName().endsWith(".png") || img.getName().endsWith(".PNG") || img.getName().endsWith(".jpg") || img.getName().endsWith(".JPG")|| img.getName().endsWith(".raw")) {
					//load image onto interface	
					panelUser.add(new LoadImageApp(img));  
					panelUser.repaint();
					panelUser.revalidate();
				} else {
					JOptionPane.showMessageDialog(null, "Invalid type of image file", "Image File", JOptionPane.WARNING_MESSAGE);
				}
		}
	}
	
	//function to load image
	public class LoadImageApp extends Component {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
		bPanel.setLayout(new GridLayout(10,1));
		
		JLabel blabel = new JLabel("Brightness", JLabel.CENTER);
		bPanel.add(blabel);
		
		JSlider brightValue = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		
		brightValue.addChangeListener((ChangeListener) this);	//use this event to edit the picture
			//figure out what to do with changelistener
		
		brightValue.setMajorTickSpacing(10);
		brightValue.setMinorTickSpacing(1);
		brightValue.setPaintTicks(true);
		brightValue.setPaintLabels(true);
		
		bPanel.add(brightValue);
		bPanel.add(new JLabel("\n\n"));
		
		JLabel clabel = new JLabel("Contrast", JLabel.CENTER);
		bPanel.add(clabel);
		
		JSlider contrast = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
		
		contrast.addChangeListener((ChangeListener) this);	//use this event to edit the picture
			//figure out what to do with changelistener
		
		contrast.setMajorTickSpacing(10);
		contrast.setMinorTickSpacing(1);
		contrast.setPaintTicks(true);
		contrast.setPaintLabels(true);
		
		bPanel.add(contrast);
		
		return bPanel;
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public JPanel Colour() {
		
		JPanel colPanel = new JPanel();
		colPanel.setLayout(new GridLayout(4,1));
		
		JLabel col = new JLabel("Choose colour...");
		colPanel.add(col);
		
		JLabel grad = new JLabel("Gradient...");
		colPanel.add(grad);
		
		return colPanel;
	}
	
	
	public JPanel Text() {
		
		JPanel tPanel = new JPanel();
		tPanel.setLayout(new GridLayout(25,1));
		
		JLabel addt = new JLabel("Add text to image");
		tPanel.add(addt);
		
		JTextField textToAdd = new JTextField(15);
		tPanel.add(textToAdd);
		
		tPanel.add(new JLabel("\n"));
		
		JLabel addn = new JLabel("Enter text size");
		tPanel.add(addn);
		
		JTextField size = new JTextField(3);
		tPanel.add(size);
		
		tPanel.add(new JLabel("\n"));
		
		JLabel selF = new JLabel("Select Font");
		tPanel.add(selF);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		fonts = ge.getAvailableFontFamilyNames();
		JComboBox<Object> fcombo = new JComboBox<Object>(fonts);
		tPanel.add(fcombo);
		
		//tPanel.add(new JLabel("\n"));
		
		JPanel s = new JPanel();
		
		JCheckBox bold = new JCheckBox("Bold");
		s.add(bold);
		
		s.add(new JLabel("\t\t\t\t"));
		
		JCheckBox italic = new JCheckBox("Italic");
		s.add(italic);
		
		tPanel.add(s);
		
		tPanel.add(new JLabel("\n"));
		
		JLabel selCol = new JLabel("Select Colour");
		tPanel.add(selCol);
		
		JComboBox<Object> colours = new JComboBox<Object>();
		colours.addItem(new ColorComboItem("Black", Color.BLACK));
		colours.addItem(new ColorComboItem("White", Color.WHITE));
		colours.addItem(new ColorComboItem("Blue", Color.BLUE));
		colours.addItem(new ColorComboItem("Cyan", Color.CYAN));
		colours.addItem(new ColorComboItem("Green", Color.GREEN));
		colours.addItem(new ColorComboItem("Magenta", Color.MAGENTA));
		colours.addItem(new ColorComboItem("Orange", Color.ORANGE));
		
		tPanel.add(colours);
		
		tPanel.add(new JLabel("\n"));
		
		JLabel selPos = new JLabel("Select Position");
		tPanel.add(selPos);
		
		//need to implement the positions
		String[] position = {"Top", "Top Left", "Top Right", "Bottom", "Bottom Left", "Bottom Right", "Center", "Left", "Right"}; 
		JComboBox<Object> comboBox = new JComboBox<Object>(position);
		tPanel.add(comboBox);
		
		JButton add = new JButton("Add Text to Image");
		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (bold.isSelected()) {
					style = Font.BOLD;
				} else if (italic.isSelected()) {
					style = Font.ITALIC;
				} else if (bold.isSelected() && italic.isSelected()) {
					style = Font.BOLD | Font.ITALIC;
				} else {
					style = Font.PLAIN;
				}

				numSize = Integer.parseInt(size.getText());
				
				f = new Font(fcombo.getSelectedItem().toString(), style, numSize);
				
				if (image != null) {
					Graphics2D gg = image.createGraphics();
					Object item = colours.getSelectedItem();
					gg.setColor(((ColorComboItem) item).getValue());
					gg.setFont(f);
					gg.drawString(textToAdd.getText(), 40, 50);
					panelUser.repaint();
				} else {
					JOptionPane.showMessageDialog(null, "Action could not be completed. Please open an image to edit.", "Image", JOptionPane.WARNING_MESSAGE);
				}	
			}
		});
		tPanel.add(new JLabel("\n\n"));
		tPanel.add(add);

		return tPanel;
		
	}
	
	public class ColorComboItem {
	    private String key;
	    private Color value;

	    public ColorComboItem(String key, Color value) {
	        this.key = key;
	        this.value = value;
	    }

	    @Override
	    public String toString() {
	        return key;
	    }

	    public String getKey() {
	        return key;
	    }

	    public Color getValue() {
	        return value;
	    }
	}
	
	
	//change orientation of image
	public JPanel Orient() {
		
		JPanel oPanel = new JPanel();
		oPanel.setLayout(new GridLayout(12,1));
		
		oPanel.add(new JLabel("\n\n"));
		
		Image left = new ImageIcon(this.getClass().getResource("/rotate_left.png")).getImage();
		JButton rotClk = new JButton(" Rotate 90\u00B0 left");
		rotClk.setIcon(new ImageIcon(left));
		rotClk.setHorizontalAlignment(SwingConstants.LEFT);
		oPanel.add(rotClk);
		oPanel.add(new JLabel("\n\n"));
		
		Image right = new ImageIcon(this.getClass().getResource("/rotate_right.png")).getImage();
		JButton rotCck = new JButton("Rotate 90\u00B0 right");
		rotCck.setIcon(new ImageIcon(right));
		rotCck.setHorizontalAlignment(SwingConstants.LEFT);
		oPanel.add(rotCck);
		oPanel.add(new JLabel("\n\n"));
		
		Image vert = new ImageIcon(this.getClass().getResource("/vertFlip.png")).getImage();
		JButton flVert = new JButton("Flip Vertically");
		flVert.setIcon(new ImageIcon(vert));
		flVert.setHorizontalAlignment(SwingConstants.LEFT);
		oPanel.add(flVert);
		oPanel.add(new JLabel("\n\n"));
		
		Image hori = new ImageIcon(this.getClass().getResource("/horFlip.png")).getImage();
		JButton flHor = new JButton("Flip Horizontally");
		flHor.setIcon(new ImageIcon(hori));
		flHor.setHorizontalAlignment(SwingConstants.LEFT);
		oPanel.add(flHor);
		oPanel.add(new JLabel("\n\n"));
		
		return oPanel;
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
	
	//menu bar functions
	
	//rotate 90 degrees
	public static BufferedImage rotate90(BufferedImage src, String dir) {
	    int width = src.getWidth();
	    int height = src.getHeight();

	    BufferedImage image = new BufferedImage(height, width, src.getType());
	    Graphics2D graphics2D = image.createGraphics();
	    
	    if (dir == "right") {
		    
		    graphics2D.translate((height - width) / 2, (height - width) / 2);
		    graphics2D.rotate(Math.PI / 2, height / 2, width / 2);
		    
		    
	    } else if (dir == "left") {
	    	
	    	graphics2D.translate((width - height) / 2, (width - height) / 2);
	        graphics2D.rotate(3*Math.PI/2, height / 2, width / 2);
		    //graphics2D.drawRenderedImage(src, null);
	    }
	    graphics2D.drawRenderedImage(src, null);
	    return image;
	}
	
	//flip horizontally
	public static BufferedImage flipHorizontal(BufferedImage src) {
		int width = src.getWidth();
	    int height = src.getHeight();
	
	    BufferedImage imageH = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = imageH.createGraphics();
	    
	    graphics2D.drawImage(src, 0, 0, null);
	    graphics2D.dispose();
	    
	    AffineTransform hor = AffineTransform.getScaleInstance(1, -1);
	    hor.translate(0, -src.getHeight());
	    AffineTransformOp op = new AffineTransformOp(hor, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
	    imageH = op.filter(imageH, null);
	    
	    return imageH;
	}
	
	//flip vertically
		public static BufferedImage flipVertical(BufferedImage src) {
			int width = src.getWidth();
		    int height = src.getHeight();
		
		    BufferedImage imageV = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
		    Graphics2D graphics2D = imageV.createGraphics();
		    
		    graphics2D.drawImage(src, 0, 0, null);
		    graphics2D.dispose();
		    
		    AffineTransform hor = AffineTransform.getScaleInstance(-1, 1);
		    hor.translate(-src.getWidth(), 0);
		    AffineTransformOp op = new AffineTransformOp(hor, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		    imageV = op.filter(imageV, null);
		    
		    return imageV;
		}
}
