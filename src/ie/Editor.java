package ie;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Window.Type;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.Image;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Canvas;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Component;

public class Editor {

	private JFrame frmImageeditor;

	private static String lastOpenDir = null;
	
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
		
		JToolBar toolBar = new JToolBar();
		panel.add(toolBar, BorderLayout.NORTH);
		
		Image openIcon = new ImageIcon(this.getClass().getResource("/open.PNG")).getImage();
		JButton btnOpen = new JButton();
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lastOpenDir == null) {
					JFileChooser fc = new JFileChooser();
					//set filter for images only
					int selFile = fc.showOpenDialog(null);
					if (selFile == JFileChooser.APPROVE_OPTION) {
						File img = fc.getSelectedFile();
						lastOpenDir = img.getParent();
						
						//load image onto interface
					}
					
				} else if (lastOpenDir != null) {
					JFileChooser fc = new JFileChooser();
					//set filter for images only
					int selFile = fc.showOpenDialog(null);
					if(selFile == JFileChooser.APPROVE_OPTION) {
						File img = fc.getSelectedFile();
						lastOpenDir = img.getParent();
						
						//load image onto interface
						
					}
				}
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
		
		JPanel panelWork = new JPanel();
		panel.add(panelWork, BorderLayout.CENTER);
		GridBagLayout gbl_panelWork = new GridBagLayout();
		gbl_panelWork.columnWidths = new int[]{0, 0, 0};
		gbl_panelWork.rowHeights = new int[]{0, 0};
		gbl_panelWork.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_panelWork.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		panelWork.setLayout(gbl_panelWork);
		
		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		panelWork.add(scrollPane, gbc_scrollPane);
		
		JPanel panelTool = new JPanel();
		panelTool.setAlignmentX(Component.RIGHT_ALIGNMENT);
		scrollPane.setViewportView(panelTool);
		GridBagLayout gbl_panelTool = new GridBagLayout();
		gbl_panelTool.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelTool.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panelTool.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE, 0.0};
		gbl_panelTool.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panelTool.setLayout(gbl_panelTool);
		
		JLabel lblSpace = new JLabel("      ");
		GridBagConstraints gbc_lblSpace = new GridBagConstraints();
		gbc_lblSpace.insets = new Insets(0, 0, 5, 5);
		gbc_lblSpace.gridx = 2;
		gbc_lblSpace.gridy = 0;
		panelTool.add(lblSpace, gbc_lblSpace);
		
		JButton btnCrop = new JButton("Crop Image");
		btnCrop.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnCrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel label = new JLabel("      ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 2;
		gbc_label.gridy = 1;
		panelTool.add(label, gbc_label);
		GridBagConstraints gbc_btnCrop = new GridBagConstraints();
		gbc_btnCrop.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnCrop.gridwidth = 6;
		gbc_btnCrop.insets = new Insets(0, 0, 5, 5);
		gbc_btnCrop.gridx = 1;
		gbc_btnCrop.gridy = 2;
		panelTool.add(btnCrop, gbc_btnCrop);
		
		JButton btnBrightness = new JButton("Edit Brightness");
		btnBrightness.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnBrightness = new GridBagConstraints();
		gbc_btnBrightness.gridwidth = 6;
		gbc_btnBrightness.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBrightness.insets = new Insets(0, 0, 5, 5);
		gbc_btnBrightness.gridx = 1;
		gbc_btnBrightness.gridy = 3;
		panelTool.add(btnBrightness, gbc_btnBrightness);
		
		JButton btnColour = new JButton("Edit Colour");
		btnColour.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnColour = new GridBagConstraints();
		gbc_btnColour.gridwidth = 6;
		gbc_btnColour.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnColour.insets = new Insets(0, 0, 5, 5);
		gbc_btnColour.gridx = 1;
		gbc_btnColour.gridy = 4;
		panelTool.add(btnColour, gbc_btnColour);
		
		JButton btnText = new JButton("Add Text");
		btnText.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnText = new GridBagConstraints();
		gbc_btnText.insets = new Insets(0, 0, 5, 5);
		gbc_btnText.gridwidth = 6;
		gbc_btnText.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnText.gridx = 1;
		gbc_btnText.gridy = 5;
		panelTool.add(btnText, gbc_btnText);
		
		JButton btnBlur = new JButton("Blur Image");
		btnBlur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBlur.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnBlur = new GridBagConstraints();
		gbc_btnBlur.insets = new Insets(0, 0, 5, 5);
		gbc_btnBlur.gridwidth = 6;
		gbc_btnBlur.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBlur.gridx = 1;
		gbc_btnBlur.gridy = 6;
		panelTool.add(btnBlur, gbc_btnBlur);
		
		JButton btnFilters = new JButton("Apply filters");
		btnFilters.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GridBagConstraints gbc_btnFilters = new GridBagConstraints();
		gbc_btnFilters.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnFilters.gridwidth = 6;
		gbc_btnFilters.insets = new Insets(0, 0, 0, 5);
		gbc_btnFilters.gridx = 1;
		gbc_btnFilters.gridy = 7;
		panelTool.add(btnFilters, gbc_btnFilters);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 0;
		panelWork.add(scrollPane_1, gbc_scrollPane_1);
		
		JPanel panelUser = new JPanel();
		scrollPane_1.setViewportView(panelUser);
		
		Canvas canvas = new Canvas();
		panelUser.add(canvas);
		
		JMenuBar menuBar = new JMenuBar();
		frmImageeditor.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lastOpenDir == null) {
					JFileChooser fc = new JFileChooser();
					//set filter for images only
					int selFile = fc.showOpenDialog(null);
					if (selFile == JFileChooser.APPROVE_OPTION) {
						File img = fc.getSelectedFile();
						lastOpenDir = img.getParent();
						
						//load image onto interface
					}
					
				} else if (lastOpenDir != null) {
					JFileChooser fc = new JFileChooser();
					//set filter for images only
					int selFile = fc.showOpenDialog(null);
					if(selFile == JFileChooser.APPROVE_OPTION) {
						File img = fc.getSelectedFile();
						lastOpenDir = img.getParent();
						
						//load image onto interface
						
					}
				}
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

}
