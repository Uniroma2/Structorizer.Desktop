/*
    Turtlebox

    Copyright (C) 2009  Bob Fisch

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or any
    later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package lu.fisch.turtle;

/******************************************************************************************************
 *
 *      Author:         Robert Fisch
 *
 *      Description:    TurtleBox - a Turtle graphics window with an interface usable e.g. by Structorizer
 *                      but also (with an appropriate adapter) by arbitrary Java applications
 *
 ******************************************************************************************************
 *
 *      Revision List
 *
 *      Author          Date            Description
 *      ------          ----            -----------
 *      Kay Gürtzig     2015-12-10      Inheritance change for enhancement request #48
 *      Kay Gürtzig     2016-10-16      Enh. #272: exact internal position (double coordinates)
 *      Kay Gürtzig     2016-12-02      Enh. #302 Additional methods to set the pen and background color
 *      Kay Gürtzig     2017-06-29/30   Enh. #424: Inheritance extension to FunctionProvidingDiagramControl
 *                                      function map introduced, functions getX(), getY(), getOrientation() added
 *      Kay Gürtzig     2017-10-28      Enh. #443: interface FunctionProvidingDiagramControl now integrated,
 *                                      structure of function map modified, procedure map added, execution
 *                                      mechanism fundamentally revised
 *                                      Concurrency issue fixed (KGU#449).
 *      Kay Gürtzig     2018-01-16      Enh. #490: Class decomposed to allow a mere API use without realising the GUI
 *      Kay Gürtzig     2018-07-30      Enh. #576: New procedure clear() added to the API
 *      Kay Gürtzig     2018-10-12      Issue #622: Modification apparently helping to overcome drawing contention
 *      Kay Gürtzig     2019-03-02      Issue #366: New methods isFocused() and requestFocus() in analogy to Window
 *      Kay Gürtzig     2020-12-11      Enh. #704: Scrollbars, status bar, and popup menu added
 *                                      Enh. #443: Deprecated execute methods disabled
 *
 ******************************************************************************************************
 *
 *      Comment:
 *      To start the Turtleizer in an application, the following steps are recommended:
 *      	{@code TurtleBox turtleBox = new TurtleBox(<width>, <height>);}
 *			{@code turtleBox.setVisible(true);}
 *			{@code turtleBox.setAnimationDelay(0, true);}
 *      The API for employing applications is retrievable via {@link TurtleBox#getFunctionMap()} and
 *      {@link TurtleBox#getProcedureMap}.
 *
 ******************************************************************************************************///

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Vector;
//import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;

import lu.fisch.diagrcontrol.*;
import lu.fisch.graphics.Rect;
import lu.fisch.structorizer.parsers.CodeParser;
import lu.fisch.turtle.elements.Element;
import lu.fisch.turtle.elements.Line;
import lu.fisch.turtle.elements.Move;

/**
 * TurtleBox - a Turtle controller providing an interface usable e.g. by Structorizer
 * but also (with an appropriate adapter) by arbitrary Java applications<br/>.
 * On parameterized initialization or call of any of the offered routines a graphics
 * window with a Turtle is realized.
 * To start the Turtleizer in an application, the following steps are recommended:<br/>
 * {@code TurtleBox turtleBox = new TurtleBox(<width>, <height>);}<br/>
 * {@code turtleBox.setVisible(true);}<br/>
 * {@code turtleBox.setAnimationDelay(0, true);}<br/>
 * The API for employing applications is retrievable via {@link TurtleBox#getFunctionMap()}
 * and {@link TurtleBox#getProcedureMap()}.<br/>
 * In order just to retrieve the available API without bringing up the GUI a light-weight
 * instance is obtained by
 * {@code TurtleBox turtleBox = new TurtleBox();}
 * @author robertfisch
 * @author Kay Gürtzig
 */
// START KGU#97 2015-12-10: Inheritance change for enhancement request #48
//public class TurtleBox extends JFrame implements DiagramController
@SuppressWarnings("serial")
// START KGU#480 2018-01-16: Inheritance change for enh. #490
//public class TurtleBox extends JFrame implements DelayableDiagramController
public class TurtleBox implements DelayableDiagramController
// END KGU#480 2018-01-16
// END KGU#97 2015-12-10
{
//	// START KGU#417 2017-06-29: Enh. #424 Function capability map
//	private static final HashMap<String, Class<?>[]> definedFunctions = new HashMap<String, Class<?>[]>();
//	static {
//		definedFunctions.put("getx", new Class<?>[]{Double.class});
//		definedFunctions.put("gety", new Class<?>[]{Double.class});
//		definedFunctions.put("getorientation", new Class<?>[]{Double.class});
//	}
//	// END KGU#417 2017-06-29
	// START KGU#597 2018-10-12: Issue #622 Analysis of drawing contention on some Macbook
	//public static final Logger logger = Logger.getLogger(TurtleBox.class.getName());
	// END KGU#597 2018-10-12
	// START KGU#417/KGU#448 2017-10-28: Enh. #424, #443 Function capability map
	private static final HashMap<String, Method> definedProcedures = new HashMap<String, Method>();
	static {
		try {
			definedProcedures.put("forward#1", TurtleBox.class.getMethod("forward", new Class<?>[]{Double.class}));
			definedProcedures.put("forward#2", TurtleBox.class.getMethod("forward", new Class<?>[]{Double.class, Color.class}));
			definedProcedures.put("backward#1", TurtleBox.class.getMethod("backward", new Class<?>[]{Double.class}));
			definedProcedures.put("backward#2", TurtleBox.class.getMethod("backward", new Class<?>[]{Double.class, Color.class}));
			definedProcedures.put("fd#1", TurtleBox.class.getMethod("fd", new Class<?>[]{Integer.class}));
			definedProcedures.put("fd#2", TurtleBox.class.getMethod("fd", new Class<?>[]{Integer.class, Color.class}));
			definedProcedures.put("bk#1", TurtleBox.class.getMethod("bk", new Class<?>[]{Integer.class}));
			definedProcedures.put("bk#2", TurtleBox.class.getMethod("bk", new Class<?>[]{Integer.class, Color.class}));
			definedProcedures.put("left#1", TurtleBox.class.getMethod("left", new Class<?>[]{Double.class}));
			definedProcedures.put("right#1", TurtleBox.class.getMethod("right", new Class<?>[]{Double.class}));
			definedProcedures.put("rl#1", TurtleBox.class.getMethod("left", new Class<?>[]{Double.class}));
			definedProcedures.put("rr#1", TurtleBox.class.getMethod("right", new Class<?>[]{Double.class}));
			definedProcedures.put("gotoxy#2", TurtleBox.class.getMethod("gotoXY", new Class<?>[]{Integer.class, Integer.class}));
			definedProcedures.put("gotox#1", TurtleBox.class.getMethod("gotoX", new Class<?>[]{Integer.class}));
			definedProcedures.put("gotoy#1", TurtleBox.class.getMethod("gotoY", new Class<?>[]{Integer.class}));
			definedProcedures.put("penup#0", TurtleBox.class.getMethod("penUp", (Class<?>[])null));
			definedProcedures.put("pendown#0", TurtleBox.class.getMethod("penDown", (Class<?>[])null));
			definedProcedures.put("up#0", TurtleBox.class.getMethod("penUp", (Class<?>[])null));
			definedProcedures.put("down#0", TurtleBox.class.getMethod("penDown", (Class<?>[])null));
			definedProcedures.put("hideturtle#0", TurtleBox.class.getMethod("hideTurtle", (Class<?>[])null));
			definedProcedures.put("showturtle#0", TurtleBox.class.getMethod("showTurtle", (Class<?>[])null));
			definedProcedures.put("setpencolor#3", TurtleBox.class.getMethod("setPenColor", new Class<?>[]{Integer.class, Integer.class, Integer.class}));
			definedProcedures.put("setbackground#3", TurtleBox.class.getMethod("setBackgroundColor", new Class<?>[]{Integer.class, Integer.class, Integer.class}));
			// START KGU#566 2018-07-30: Enh. #576
			definedProcedures.put("clear#0", TurtleBox.class.getMethod("clear", (Class<?>[])null));
			// END KGU#566 2018-07-30
		} catch (NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
		}
	}
	private static final HashMap<String, Method> definedFunctions = new HashMap<String, Method>();
	static {
		try {
			definedFunctions.put("getx#0", TurtleBox.class.getMethod("getX", (Class<?>[])null));
			definedFunctions.put("gety#0", TurtleBox.class.getMethod("getY", (Class<?>[])null));
			definedFunctions.put("getorientation#0", TurtleBox.class.getMethod("getOrientation", (Class<?>[])null));
		} catch (NoSuchMethodException | SecurityException ex) {
			ex.printStackTrace();
		}
	}
	// END KGU#417/KGU#448 2017-10-28
	// START KGU#480 2018-01-16: Enh. #490 - frame now as attribute
	/** The GUI frame - while null, it hasn't been materialized (light-weight instance) */
	private JFrame frame = null;
	// END KGU#480 2018-01-16
    private final String TITLE = "Turtleizer";
    
    // START KGU#685 2020-12-11: Enh. #704
    /** Width and height margin for the drawn area (regarding scrollbars) */
    private static final int MARGIN = 20;
    // END KGU#685 2020-12-11

    private Point pos;
    // START KGU#282 2016-10-16: Enh. #272
    private double posX, posY;		// exact position
    // END KGU#282 2016-10-16
    private Point home;
    private double angle = -90;		// upwards (north-bound)
    private Image image = (new ImageIcon(this.getClass().getResource("turtle.png"))).getImage();
    private boolean isPenDown = true;
    // START KGU#303 2016-12-02: Enh. #302
    //private Color penColor = Color.BLACK;
    private Color defaultPenColor = Color.BLACK;
    private Color backgroundColor = Color.WHITE;
    private Color penColor = defaultPenColor;
    // END KGU#303 2016-12-02
    private boolean turtleHidden = false;
    private int delay = 10;
    private Vector<Element> elements = new Vector<Element>();
    private JPanel panel;
    // START KGU#685 2020-12-11: Enh. #704
    private JScrollPane scrollarea;
    private javax.swing.JPanel statusbar;
    protected javax.swing.JLabel statusTurtle;
    protected javax.swing.JLabel statusSize;
    protected javax.swing.JLabel statusViewport;
    protected javax.swing.JLabel statusZoom;
    protected javax.swing.JLabel statusSelection;
    private double zoomFactor = 1.0;
    protected javax.swing.JPopupMenu popupMenu;
    protected javax.swing.JMenuItem popupGotoCoord;
    protected javax.swing.JMenuItem popupGotoTurtle;
    protected javax.swing.JCheckBoxMenuItem popupShowTurtle;
    protected javax.swing.JMenuItem popupExportCSV;
    protected javax.swing.JMenu popupExportImage;
    protected javax.swing.JMenuItem popupExportPNG;
    protected javax.swing.JMenuItem popupExportSVG;
    protected javax.swing.JCheckBoxMenuItem popupShowStatus;
    protected javax.swing.JLabel lblOverwrite = new javax.swing.JLabel("File exists. Sure to overwrite?");
    private File currentDirectory = null;
    // END KGU#685 2020-12-11

    /**
     * This constructor does NOT realize a GUI, it just creates a light-weight instance
     * for API retrieval. A call of {@link #setVisible(boolean)} or the first use of an
     * API routine from {@link #definedProcedures} or {@link #definedFunctions} will establish
     * the graphics window, though.
     */
    public TurtleBox()
    {
        // START KGU#480 2018-01-16: Enh. #490 - This constructor no longer builds the GUI
        //init(300,300);
        home = new Point();
        reinit();
        // END KGU#480 2018-01-16
    }

    /**
     * Establishes a window frame with the graphics canvas for the turtle movements.
     * The turtle will be placed in the centre of he canvas.
     * @param width - the initial width of the frame in pixels
     * @param height - the initial height of the frame in pixels.
     */
    public TurtleBox(int width, int height)
    {
        init(width,height);
    }

    /* (non-Javadoc)
     * @see java.awt.Component#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {}
    
    /* (non-Javadoc)
     * @see java.awt.Component#getName()
     */
    @Override
    public String getName()
    {
    	return TITLE;
    }
    
    /**
     * Returns the contrary of the internal orientation of the turtle in degrees.
     * @return degrees (90° = North, positive sense = clockwise)
     * @see #getOrientation()
     */
    public double getAngle()
    {
        return 180+angle;
    }
    
    // START KGU#417 2017-06-29: Enh. #424
    /**
     * API function returning the "external" turtle orientation in degrees
     * in the range -180 .. 180 where<br/>
     * 0 is upwards/North (initial orientation),<br/>
     * positive sense is clockwise (right/East),
     * negative sense is counter-clockwise (left/West)
     * @return orientation in degrees.
     * @see #getAngle()
     */
    public double getOrientation() {
        double orient = angle + 90.0;
        while (orient > 180) { orient -= 360; }
        while (orient < -180) { orient += 360; }
        return orient == 0.0 ? orient : -orient;
    }
    // END KGU#417 2017-06-29
    
    // START #272 2016-10-16 (KGU)
    private void setPos(Point newPos)
    {
        pos = newPos;
        posX = pos.getX();
        posY = pos.getY();
    }
    
    private void setPos(double x, double y)
    {
        posX = x;
        posY = y;
        pos = new Point((int)Math.round(x), (int)Math.round(y));
    }
    // END #272 2016-10-16

    /**
     * Initialises this instance establishing the window with the graphics canvas
     * and places the Turtle in the centre.
     * @param width - initial width of the frame in pixels
     * @param height - initial height of the frame in pixels
     */
    private void init(int width, int height)
    {
        // START KGU#480 2018-01-16: Enh. #490 - care for the existence of a frame
        if (frame == null) {
            frame = new JFrame();
        }
        // END KGU#480 2018-01-16
        panel = new JPanel()
        {
            @Override
            public void paint(Graphics graphics)
            {
                Graphics2D g = (Graphics2D) graphics;
                // set anti-aliasing rendering
                ((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

                // clear background
                // START KGU#303 2016-12-02: Enh. #302
                //g.setColor(Color.WHITE);
                g.setColor(backgroundColor);
                // END KGU#303 2016-12-02
                g.fillRect(0,0,getWidth(),getHeight());
                // START KGU#303 2016-12-03: Enh. #302
                //g.setColor(Color.BLACK);
                g.setColor(defaultPenColor);
                // END KGU#303 2016-12-03

                // START KGU#685 2020-12-11: Enh. #704
                Dimension dim = new Dimension(pos.x, pos.y);
                Rectangle visibleRect = g.getClipBounds();
                // END KGU#685 2020-12-11
                
                // draw all elements
                // START KGU#449 2017-10-28: The use of iterators may lead to lots of
                //java.util.ConcurrentModificationException errors slowing down all.
                // So we better avoid the iterator and loop against a snapshot size
                // (which is safe because the elements Vector can't shrink during execution).
                //for (Element ele : elements)
                //{
                //    ele.draw(g);
                //}
                int nElements = elements.size();
                // START KGU#597 2018-10-12: Issue #622 - Monitoring drawing detention underMac
                //logger.config("Painting " + nElements + " elements...");
                // END KGU#597 2018-10-12
                for (int i = 0; i < nElements; i++) {
                    // START KGU#685 2020-12-11: Enh. #704
                    //elements.get(i).draw(g);
                    elements.get(i).draw(g, visibleRect, dim);
                    // END KGU#685 2020-12-11
                }
                // END KGU#449 2017-10-28

                // START KGU#685 2020-12-11: Enh. #704
                // Add some pixels for the scrollbars
                dim.width += MARGIN;
                dim.height += MARGIN;
                this.setPreferredSize(dim);
                this.revalidate();
                // END KGU#685 2020-12-11

                if (!turtleHidden)
                {
                    // START #272 2016-10-16
                    // fix drawing point
                    //int x = (int) Math.round(pos.x - (image.getWidth(this)/2));
                    //int y = (int) Math.round(pos.y - (image.getHeight(this)/2));
                    // fix drawing point
                    //int xRot = x+image.getWidth(this)/2;
                    //int yRot = y+image.getHeight(this)/2;
                    // apply rotation
                    //g.rotate((270-angle)/180*Math.PI,xRot,yRot);
                    // fix drawing point
                    double x = posX - (image.getWidth(this)/2);
                    double y = posY - (image.getHeight(this)/2);
                    // apply rotation
                    g.rotate((270-angle)/180*Math.PI, posX, posY);
                    // END #272 2016-10-16
                    // draw the turtle
                    g.drawImage(image,(int)Math.round(x),(int)Math.round(y),this);
                }
            }
        };

        frame.setTitle(TITLE);
        //frame.setIconImage((new ImageIcon(this.getClass().getResource("turtle.png"))).getImage());
        frame.setIconImage(image);

        //this.setDefaultCloseOperation(TurtleBox.EXIT_ON_CLOSE);
        //this.setDefaultCloseOperation(TurtleBox.DISPOSE_ON_CLOSE);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame.setBounds(0,0,width,height);
        // START KGU#685 2020-12-11: Enh. #704
        //frame.getContentPane().add(panel);
        //this.setVisible(true);
        //setPos(new Point(panel.getWidth()/2,panel.getHeight()/2));
        //home = new Point(panel.getWidth()/2,panel.getHeight()/2);
        //panel.setDoubleBuffered(true);
        //panel.repaint();
        
        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger() && popupMenu != null) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger() && popupMenu != null) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }});
        
        scrollarea = new JScrollPane(panel);
        scrollarea.getViewport().putClientProperty("EnableWindowBlit", Boolean.TRUE);
        scrollarea.setWheelScrollingEnabled(true);
        scrollarea.setDoubleBuffered(true);
        scrollarea.setBorder(BorderFactory.createEmptyBorder());
        scrollarea.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent mwEvt) {
                if (mwEvt.isControlDown()) {
                    int rotation = mwEvt.getWheelRotation();
//                    //if (Element.E_WHEEL_REVERSE_ZOOM) {
                    //    rotation *= -1;
                    //}
//                    if (rotation >= 1) {
//                    	mwEvt.consume();
//                    	this.zoom(false);
//                    }
//                    else if (rotation <= -1) {
//                    	mwEvt.consume();
//                    	this.zoom(true);
//                    }
                }
            }});
        frame.getContentPane().add(scrollarea, java.awt.BorderLayout.CENTER);
        panel.setAutoscrolls(true);
        statusbar = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 0, 0));
        //statusbar.setBorder(new javax.swing.border.CompoundBorder(new javax.swing.border.LineBorder(java.awt.Color.DARK_GRAY),
        //        new javax.swing.border.EmptyBorder(0, 4, 0, 4)));
        statusTurtle = new javax.swing.JLabel();
        statusSize = new javax.swing.JLabel();
        statusViewport = new javax.swing.JLabel();
        statusZoom = new javax.swing.JLabel();
        statusSelection = new javax.swing.JLabel("0");
        statusTurtle.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
                javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        statusSize.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
                javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        statusViewport.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
                javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        statusZoom.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
                javax.swing.BorderFactory.createEmptyBorder(0, 4, 0, 4)));
        statusSelection.setBorder(new javax.swing.border.CompoundBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED),
                new javax.swing.border.EmptyBorder(0, 4, 0, 4)));
        statusTurtle.setToolTipText("Current turtle position");
        statusSize.setToolTipText("Extension of the drawn area");
        statusViewport.setToolTipText("Current scrolling viewport");
        statusZoom.setToolTipText("Zoom factor");
        statusSelection.setToolTipText("Number of selected lines");
        statusbar.add(statusTurtle);
        statusbar.add(statusSize);
        statusbar.add(statusViewport);
//        statusbar.add(statusZoom);
        statusbar.add(statusSelection);
        statusbar.setFocusable(false);
        frame.getContentPane().add(statusbar, java.awt.BorderLayout.SOUTH);
        scrollarea.getViewport().addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                updateStatusBar();
            }
        });
        setPos(new Point(scrollarea.getWidth()/2, scrollarea.getHeight()/2));	// FIXME!
        home = new Point(pos.x, pos.y);

        initPopupMenu();

        repaint();
        // END KGU#685 2020-12-11
    }

    // START KGU#685 2020-12-11: Enh. #704
    private void initPopupMenu()
    {
        popupMenu = new javax.swing.JPopupMenu();
        popupGotoCoord = new javax.swing.JMenuItem("Scroll to coordinate ...");
        popupGotoTurtle = new javax.swing.JMenuItem("Scroll to turtle position");
        popupShowTurtle = new javax.swing.JCheckBoxMenuItem("Show turtle");
        popupExportCSV = new javax.swing.JMenuItem("Export list of drawing intems ...");
        popupExportImage = new javax.swing.JMenu("Export as image");
        popupExportPNG = new javax.swing.JMenuItem("to PNG ...");
        popupExportSVG = new javax.swing.JMenuItem("to SVG ...");
        
        popupGotoCoord.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Point coord = askForCoordinate();
                if (coord != null) {
                    coord.x = Math.max(MARGIN, Math.min(coord.x, panel.getWidth() - MARGIN));
                    coord.y = Math.max(MARGIN, Math.min(coord.y, panel.getHeight() - MARGIN));
                    panel.scrollRectToVisible(
                            new Rectangle(
                                    coord.x - MARGIN, coord.y - MARGIN,
                                    coord.x + MARGIN, coord.y + MARGIN
                                    )
                            );
                }
            }});
        popupMenu.add(popupGotoCoord);

        popupGotoTurtle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.scrollRectToVisible(
                        new Rectangle(
                                pos.x - MARGIN, pos.y -MARGIN,
                                pos.x + MARGIN, pos.y + MARGIN
                                )
                        );
            }});
        popupMenu.add(popupGotoTurtle);

        popupMenu.addSeparator();

        popupShowTurtle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                turtleHidden = !popupShowTurtle.isSelected();
                repaint();
            }});
        popupMenu.add(popupShowTurtle);

        popupMenu.addSeparator();

        popupExportCSV.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportCSV();
            }});
        popupMenu.add(popupExportCSV);

        popupMenu.add(popupExportImage);
        popupExportImage.add(popupExportPNG);
        popupExportImage.add(popupExportSVG);
        // TODO Implement the image export
        popupExportImage.setEnabled(false);
    }

    /**
     * Asks the user for a target coordinate and returns the entered coordinate
     * as {@link Point} or its nearest point within the occupied area (if the
     * coordinate lie outside).
     * @return the {@link Point} representing the user input or {@code null} if
     * cancelled.
     */
    protected Point askForCoordinate() {
        Point point = null;
        javax.swing.JPanel pnl = new javax.swing.JPanel();
        pnl.setLayout(new java.awt.GridBagLayout());
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = java.awt.GridBagConstraints.LINE_START;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(1, 2, 2, 1);
        javax.swing.JLabel lblPrompt = new javax.swing.JLabel("Target coordinate");
        pnl.add(lblPrompt, gbc);
        javax.swing.JTextField[] fields = new javax.swing.JTextField[2];

        gbc.gridwidth = 1;
        for (int row = 0; row < 2; row++) {
            gbc.gridy++;
            gbc.gridx = 0;
            gbc.weightx = 0;
            gbc.fill = java.awt.GridBagConstraints.NONE;
            pnl.add(new javax.swing.JLabel((char)('x' + row) + ""), gbc);
            gbc.gridx++;
            gbc.weightx = 1;
            gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
            fields[row] = new javax.swing.JTextField();
            pnl.add(fields[row], gbc);
        }

        do {
            boolean committed = JOptionPane.showConfirmDialog(frame, pnl,
                    popupGotoCoord.getText(),
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.OK_OPTION;
            if (!committed) {
                return null;
            }
            point = new Point();
            for (int row = 0; row < fields.length; row++) {
                try {
                    int coord = Integer.parseInt(fields[row].getText());
                    if (row == 0) {
                        point.x = coord;
                    }
                    else {
                        point.y = coord;
                    }
                    fields[row].setForeground(null);
                }
                catch (NumberFormatException ex) {
                    fields[row].setForeground(Color.RED);
                    point = null;
                    break;
                }
            }
        } while (point == null);
        return point;
    }

	// END KGU#685 2020-12-11
    
    //@Override
    /**
     * Realizes the window on the screen , brings it to front and fetches the window focus.
     * @param visible
     * @see JFrame#setVisible(boolean)
     */
    public void setVisible(boolean visible)
    {
        // START KGU#480 2018-01-16: Enh. #490 - lazy initialization
        if (visible && frame == null) {
            init(300, 300);
        }
        // END KGU#480 2018-01-16
        frame.setVisible(visible);
// START KGU#303 2016-12-03: Issue #302 - replaced by reinit() call below
//        elements.clear();
//        angle=-90;
//        // START KGU#303 2016-12-02: Enh. #302
//        backgroundColor = Color.WHITE;
//        defaultPenColor = Color.BLACK;
//        turtleHidden = false;
//        // END KGU#3032016-12-02
//        setPos(new Point(panel.getWidth()/2,panel.getHeight()/2));
// END KGU#303 2016-12-03
        if (visible) {
            //home = new Point(panel.getWidth()/2, panel.getHeight()/2);
            home = new Point(scrollarea.getWidth()/2, scrollarea.getHeight()/2);
            // START KGU#303 2016-12-03: Issue #302 - replaces disabled code above
            reinit();
            // END KGU#303 2016-12-03
            frame.paint(frame.getGraphics());
            // START KGU#685 2020-12-11: Enh. #704
            updateStatusBar();
            // END KGU#685 2020-12-11
        }
    }
    
    // START KGU#303 2016-12-03: Issue #302
    /**
     * Undoes all possible impacts of a previous main diagram execution  
     */
    private void reinit()
    {
        elements.clear();
        angle = -90;
        backgroundColor = Color.WHITE;
        defaultPenColor = Color.BLACK;
        turtleHidden = false;
        setPos(home.getLocation());
        penDown();
    }
    // END KGU#303 2016-12-03
    
    private void delay()
    {
        //panel.repaint();
        // force repaint (not recommended!)
        // START KGU#480 2018-01-16: Enh. #490 - lazy initialization
        if (frame == null) {
            init(300, 300);
        }
        // END KGU#480 2018-01-16
        // START KGU#597 2018-10-12: Issue #622 Attempt to fix a drawing contention on some Macbook
        //logger.config(panel + " enqueuing repaint()...");
        // START KGU#685 2020-12-11: Enh. #704
        //panel.repaint();
        repaint();
        // END KGU#685 2020-12-11
        // END KGU#597 2018-10-12
        if (delay!=0)
        {
            // START KGU#597 2018-10-12: Issue #622 Replaced by the queued repaint() above
            //panel.paint(panel.getGraphics());
            // END KGU#597 2018-10-12
            try { Thread.sleep(delay); }
            catch (InterruptedException e) { System.out.println(e.getMessage());}
        }
        // START KGU#597 2018-10-12: Issue #622 Now done before the alternative
//        else
//        {
//            panel.repaint();
//        }
        // END KGU#597 2018-10-12
    }

    public void fd(Integer pixels)
    {
        Point newPos = new Point(pos.x-(int) Math.round(Math.cos(angle/180*Math.PI)*pixels),
                                 pos.y+(int) Math.round(Math.sin(angle/180*Math.PI)*pixels));
        if (isPenDown)
        {
            elements.add(new Line(pos,newPos,penColor));
        }
        else
        {
            elements.add(new Move(pos,newPos));
        }
        //System.out.println("from: ("+pos.x+","+pos.y+") => to: ("+newPos.x+","+newPos.y+")");
        setPos(newPos);
        delay();
    }

    // START #272 2016-10-16 (KGU)
//    public void forward(int pixels)
//    {
//        fd(pixels);
//    }
    public void forward(Double pixels)
    {
        double newX = posX - Math.cos(angle/180*Math.PI) * pixels;
        double newY = posY + Math.sin(angle/180*Math.PI) * pixels;
        Point newPos = new Point((int)Math.round(newX), (int)Math.round(newY));
        if (isPenDown)
        {
            elements.add(new Line(pos, newPos, penColor));
        }
        else
        {
            elements.add(new Move(pos, newPos));
        }
        //System.out.println("from: ("+pos.x+","+pos.y+") => to: ("+newPos.x+","+newPos.y+")");
        setPos(newX, newY);
        delay();
    }
    // END #272 2016-10-16

    public void bk(Integer pixels)
    {
        fd(-pixels);
    }

    // START #272 2016-10-16 (KGU)
    //public void backward(int pixels)
    //{
    //    fd(-pixels);
    //}
    public void backward(Double pixels)
    {
        forward(-pixels);
    }
    // END #272 2016-10-16
    
    // START KGU#448 2017-10-28: Enh. #443 Wrappers with color argument
    public void fd(Integer pixels, Color color)
    {
        this.setColorNonWhite(color);
        this.fd(pixels);
    }
    public void bk(Integer pixels, Color color)
    {
        this.setColorNonWhite(color);
        this.fd(-pixels);
    }
    public void forward(Double pixels, Color color)
    {
        this.setColorNonWhite(color);
        this.forward(pixels);
    }
    public void backward(Double pixels, Color color)
    {
        this.setColorNonWhite(color);
        this.forward(-pixels);
    }
    // END KGU#448 2017-10-28

    private void rl(Double degrees)
    {
        this.angle+=degrees;
        delay();
    }

    public void left(Double degrees)
    {
        rl(degrees);
    }

    private void rr(Double degrees)
    {
        rl(-degrees);
    }

    public void right(Double degrees)
    {
        rr(degrees);
    }
    
    // START KGU#303 2016-12-02: Enh. #302
    /** Non-retarded method to set the background colour directly
     * @param bgColor - the new background colour
     */
    public void setBackgroundColor(Color bgColor)
    {
        // START KGU#480 2018-01-16: Enh. #490 - lazy initialization
        if (frame == null) {
            init(300, 300);
        }
        // END KGU#480 2018-01-16
        backgroundColor = bgColor;
        panel.repaint();
    }

    /** Delayed API method to set the background colour from RGB values
     * @param red
     * @param green
     * @param blue
     */
    public void setBackgroundColor(Integer red, Integer green, Integer blue)
    {
        backgroundColor = new Color(Math.min(255, red), Math.min(255, green), Math.min(255, blue));
        delay();
    }

    /**
     * Non-retarded method to set the default pen colour directly
     * This colour is used whenever a move is carried out with colour WHITE
     * @param bgColor - the new foreground colour
     */
    public void setPenColor(Color penColor)
    {
        // START KGU#480 2018-01-16: Enh. #490 - lazy initialization
        if (frame == null) {
            init(300, 300);
        }
        // END KGU#480 2018-01-16
        defaultPenColor = penColor;
        panel.repaint();
    }

    /**
     * Delayed API method to set the default pen colour from RGB values
     * This colour is used whenever a move is carried out with colour WHITE
     * @param red
     * @param green
     * @param blue
     */
    public void setPenColor(Integer red, Integer green, Integer blue)
    {
        defaultPenColor = new Color(Math.min(255, red), Math.min(255, green), Math.min(255, blue));
        delay();
    }
    // END KGU#303 2016-12-02

    // KGU: Where is this method used?
    /**
     * @return the angle from the turtle home position to its current position
     * in degrees
     */
    public double getAngleToHome()
    {
        // START #272 2016-10-16 (KGU)
        //double hypo = Math.sqrt(Math.pow((pos.x-home.x), 2)+Math.pow((pos.y-home.y),2));
        //double base = (home.x-pos.x);
        //double haut = (home.y-pos.y);
        double base = (home.x-posX);
        double haut = (home.y-posY);
        double hypo = Math.sqrt(base*base + haut*haut);
        // END #272 2016-10-16

        double sinAlpha = haut/hypo;
        double cosAlpha = base/hypo;

        double alpha = Math.asin(sinAlpha);

        alpha = alpha/Math.PI*180;

        // rounding (FIXME: what for?)
        alpha = Math.round(alpha*100)/100;

        if (cosAlpha<0) // Q2 & Q3
        {
            alpha=180-alpha;
        }
        alpha =- alpha;
        alpha -= getAngle();

        while (alpha < 0)
        {
            alpha+=360;
        }
        
        while (alpha>=360)
        {
            alpha-=360;
        }

        return alpha;
    }

    /**
     * Raises the pen, i.e. subsequent moves won't leave a trace
     */
    public void penUp()
    {
        isPenDown=false;
    }

    /**
     * Pus the pen down, i.e. subsequent moves will leave a trace
     */
    public void penDown()
    {
        isPenDown=true;
    }

    /**
     * Direct positioning (without trace, without changing orientation)
     * @param x - new horizontal pixel coordinate (from left window edge)
     * @param y - new vertical pixel coordinate (from top window edge downwards)
     */
    public void gotoXY(Integer x, Integer y)
    {
        Point newPos = new Point(x,y);
        elements.add(new Move(pos, newPos));
        setPos(newPos);
        delay();
   }

    /**
     * Direct vertical positioning (without trace, without changing orientation,
     * horizontal position remains unchanged)
     * @param y - new vertical pixel coordinate (from top window edge downwards)
     */
    public void gotoY(Integer y)
    {
        gotoXY(pos.x, y);
    }

    /**
     * Direct horizontal positioning (without trace, without changing orientation,
     * vertical position remains unchanged)
     * @param x - new horizontal pixel coordinate (from left window edge)
     */
    public void gotoX(Integer x)
    {
        gotoXY(x, pos.y);
    }

    /** The turtle icon will no longer be shown, has no impact on the pen */
    public void hideTurtle()
    {
        turtleHidden = true;
        delay();
    }

    /** The turtle icon will be shown again, has no impact on the pen */
    public void showTurtle()
    {
        turtleHidden = false;
        delay();
    }

    /**
     * Directly set the current pen colour (without delay), not part of the API, does not
     * modify the default pen colour, does not avert colour WHITE.
     * @param color - the new pen colour
     * @see #setColorNonWhite(Color)
     * @see #setPenColor(Color)
     * @see #setBackground(Color)
     */
    public void setColor(Color color)
    {
        this.penColor=color;
    }

    // START KGU#448 2017-10-28: Enh. #443
    //public void setAnimationDelay(int delay)
    /* (non-Javadoc)
     * @see lu.fisch.diagrcontrol.DelayableDiagramController#setAnimationDelay(int, boolean)
     */
    public void setAnimationDelay(int delay, boolean _reinit)
    // END KGU#448 2017-10-28
    {
        if (_reinit) {
            reinit();
        }
        this.delay=delay;
    }

    // START KGU#356 2019-03-02: Issue #366 - Allow focus control of he DiagramController copes with it
    /**
     * @return whether this TurtleBox window is focused
     */
    @Override
    public boolean isFocused()
    {
        return this.frame != null && this.frame.isFocused();
    }

    /**
     * Requests the focus for this TurtleBox window.
     */
    @Override
    public void requestFocus()
    {
        if (this.frame != null) {
            this.frame.requestFocus();
        }
    }
    // END KGU#356 2019-03-02
    
    // START KGU#685 2020-12-11: Enh. #704 Status bar support
    /**
     * Repaints the turtle drawing and updates the status bar
     */
    private void repaint()
    {
        panel.repaint();
        updateStatusBar();
    }
    
    /**
     * Updates all status information in the status bar
     */
    private void updateStatusBar() {
        statusTurtle.setText("(" + pos.x + ", " + pos.y + ") " + this.getOrientation() + "°");
        Dimension size = panel.getPreferredSize();
        statusSize.setText((size.width - MARGIN) + " x " + (size.height - MARGIN));
        scrollarea.getLocation();	// This is just to determine the current origin
        java.awt.Rectangle vRect = scrollarea.getViewport().getViewRect();
        Rect visRect = (new Rect(vRect)).scale(zoomFactor);
        statusViewport.setText(visRect.left + ".." + visRect.right + " : " +
               visRect.top + ".." + visRect.bottom);
        statusZoom.setText(String.format("%.1f %%", 100 / zoomFactor));
        popupShowTurtle.setSelected(!this.turtleHidden);
    }

    /**
     * Exports the drawing elements and moves to a CSV file. Will open a
     * file chooser dialog first. May pop up a message box if something goes wrong.
     */
    private void exportCSV()
    {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle(popupExportCSV.getText());
        fc.setCurrentDirectory(currentDirectory);
        int decision = fc.showSaveDialog(frame);
        if (decision == JFileChooser.APPROVE_OPTION) {
            File chosen = fc.getSelectedFile();
            if (chosen != null) {
                Path path = chosen.toPath().toAbsolutePath();
                Path name = path.getFileName();
                String[] parts = name.toString().split("\\.", 0);
                String ext = null;
                if (parts.length < 2
                        || !(ext = parts[parts.length-1]).equals("txt") && !ext.equals("csv")) {
                    path = new File(chosen.getParent().toString() + File.separator + name.toString() + ".txt").toPath();
                }
                // TODO: Check for overriding
                if (Files.exists(path)) {
                    decision = JOptionPane.showConfirmDialog(frame,
                            lblOverwrite.getText(),
                            popupExportCSV.getText(), JOptionPane.OK_CANCEL_OPTION);
                    if (decision != JOptionPane.OK_OPTION) {
                        return;
                    }
                }
                try (BufferedWriter bw = Files.newBufferedWriter(path)) {
                    bw.append("xFrom,yFrom,xTo,yTo,color\n");
                    int nElements = elements.size();
                    for (int i = 0; i < nElements; i++) {
                        bw.append(elements.get(i).toCSV(null));
                        bw.newLine();
                    }
                } catch (IOException exc) {
                    String message = exc.getMessage();
                    if (message == null || message.isEmpty()) {
                        message = exc.toString();
                    }
                    JOptionPane.showMessageDialog(frame, message,
                            popupExportCSV.getName(),
                            JOptionPane.ERROR_MESSAGE);
                }
                if (Files.exists(path) && !chosen.isDirectory()) {
                    currentDirectory = chosen.getParentFile();
                }
            }
        }
    }
    // END KGU#685 2020-12-11

// START KGU#448 2020-12-11: Enh. #443 deprecated stuff disabled
//    @Deprecated
//    private String parseFunctionName(String str)
//    {
//        if (str.trim().indexOf("(")!=-1)
//            return str.trim().substring(0,str.trim().indexOf("(")).trim().toLowerCase();
//        else
//            return null;
//    }
//
//    @Deprecated
//    private String parseFunctionParam(String str, int count)
//    {
//    	String res = null;
//    	int posParen1 = (str = str.trim()).indexOf("(");
//    	if (posParen1 > -1)
//    	{
//    		String params = str.substring(posParen1+1, str.indexOf(")")).trim();
//    		if (!params.isEmpty())
//    		{
//    			String[] args = params.split(",");
//    			if (count < args.length) {
//    				res = args[count];
//    			}
//    		}
//    	}
//    	return res;
//    }
//
//    @Deprecated
//    private Double parseFunctionParamDouble(String str, int count)
//    {
//        String res = parseFunctionParam(str, count);
//        if( res == null || res.isEmpty() ) { return 0.0; }
//        return Double.valueOf(res);
//    }
//
//    @Deprecated
//    public String execute(String message, Color color)
//    {
//        setColorNonWhite(color);
//        return execute(message);
//    }
// END KGU#448 2020-12-11

    /**
     * Sets the given color except in case of WHITE where the {@link #defaultPenColor} is used instead.
     * Does not influence the default pen colour.
     * @param color - the specified colour (where WHITE means default)
     * @see #setColor(Color)
     * @see #setPenColor(Color)
     * @see #setBackground(Color)
     */
    private void setColorNonWhite(Color color) {
        if(color.equals(Color.WHITE))
        {
            // START KGU#303 2016-12-03: Enh. #302
            //this.setColor(Color.BLACK);
            this.setColor(defaultPenColor);
            // END KGU#303 2016-12-03
        }
        else this.setColor(color);
    }

// START KGU#448 2020-12-11: Enh. #443 deprecated stuff disabled
//    @Deprecated
//    public String execute(String message)
//    {
//        String name = parseFunctionName(message);
//        double param1 = parseFunctionParamDouble(message,0);
//        double param2 = parseFunctionParamDouble(message,1);
//        // START KGU#303 2016-12-02: Enh. #302
//        double param3 = parseFunctionParamDouble(message,2);
//        // END KGU#303 2016-12-02
//        String res = new String();
//        if(name!=null)
//        {
//            if (name.equals("init")) {
//// START KGU#303 2016-12-03: Issue #302 - replaced by reinit() call
////                elements.clear();
////                angle=-90;
////                // START KGU#303 2016-12-02: Enh. #302
////                backgroundColor = Color.WHITE;
////                // END KGU#3032016-12-02
////                // START KGU#303 2016-12-03: Enh. #302
////                defaultPenColor = Color.BLACK;
////                turtleHidden = false;
////                // END KGU#3032016-12-03
////                setPos(home.getLocation());
////                penDown();
////                reinit();
//// END KGU#303 2016-12-03
//                setAnimationDelay((int) param1, true);
//            }
//            // START #272 2016-10-16 (KGU): Now different types (to allow to study rounding behaviour)
//            //else if (name.equals("forward") || name.equals("fd")) { forward((int) param1); }
//            //else if (name.equals("backward") || name.equals("bk")) { backward((int) param1); }
//            else if (name.equals("forward")) { forward(param1); }
//            else if (name.equals("backward")) { backward(param1); }
//            else if (name.equals("fd")) { fd((int)param1); }
//            else if (name.equals("bk")) { bk((int)param1); }
//            // END #272 2016-10-16
//            // START KGU 20141007: Wrong type casting mended (led to rotation biases)
//            //else if (name.equals("left") || name.equals("rl")) { left((int) param1); }
//            //else if (name.equals("right") || name.equals("rr")) { right((int) param1); }
//            else if (name.equals("left") || name.equals("rl")) { left(param1); }
//            else if (name.equals("right") || name.equals("rr")) { right(param1); }
//            // END KGU 20141007
//            else if (name.equals("penup") || name.equals("up")) { penUp(); }
//            else if (name.equals("pendown") || name.equals("down")) { penDown(); }
//            else if (name.equals("gotoxy")) { gotoXY((int) param1, (int) param2); }
//            else if (name.equals("gotox")) { gotoX((int) param1); }
//            else if (name.equals("gotoy")) { gotoY((int) param1); }
//            else if (name.equals("hideturtle")) { hideTurtle(); }
//            else if (name.equals("showturtle")) { showTurtle(); }
//            // START KGU#303 2016-12-02: Enh. #302 - A procedure to set the backgroud colour was requested
//            else if (name.equals("setbackground")) { setBackgroundColor((int)Math.abs(param1),(int)Math.abs(param2),(int)Math.abs(param3)); }
//            else if (name.equals("setpencolor")) { setPenColor((int)Math.abs(param1),(int)Math.abs(param2),(int)Math.abs(param3)); }
//            // END KGU#303 2016-12-02
//            else { res="Procedure <"+name+"> not implemented!"; }
//        }
//        
//        return res;
//    }
// END KGU#448 2020-12-11

    // START KGU#448 2017-10-28: Enh. #443
    /* (non-Javadoc)
     * @see lu.fisch.diagrcontrol.DiagramController#getProcedureMap()
     */
    @Override
    public HashMap<String, Method> getProcedureMap() {
    	return definedProcedures;
    }
    // END KGU#448 2017-10-28

    // START KGU#417 2017-06-29: Enh. #424, #443 - Allow function getX(), getY(), getOrientation();
    /* (non-Javadoc)
     * @see lu.fisch.diagrcontrol.DiagramController#getFunctionMap()
     */
    public HashMap<String, Method> getFunctionMap()
    {
    	return definedFunctions;
    }
    
    // START KGU#448 2017-10-28: Enh. #417, #443 - support the generic execute method
    /**
     * Returns the current horizontal pixel coordinate.
     * @return the precise result of preceding moves, i.e. as double value
     */
    public double getX()
    {
    	return this.posX;
    }
    /**
     * Returns the current vertical pixel coordinate (from top downwards).
     * @return the precise result of preceding moves, i.e. as double value
     */
    public double getY()
    {
    	return this.posY;
    }
    // END KGU#448 2017-10-28

    // START KGU#566 2018-07-30: Enh. #576 API procedure allowing the user algorithm to wipe the box
    /**
     * Delayed API function to wipe the TurtleBox from all content
     */
    public void clear()
    {
    	this.elements.clear();
    	this.delay();
    }
    // END KGU#566 2018-07-30
}
