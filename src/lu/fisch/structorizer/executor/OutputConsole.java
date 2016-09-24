/*
    Structorizer
    A little tool which you can use to create Nassi-Schneiderman Diagrams (NSD)

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

package lu.fisch.structorizer.executor;

/******************************************************************************************************
 *
 *      Author:         Kay Gürtzig
 *
 *      Description:    This class represents the Output text area for executor console mode.
 *
 ******************************************************************************************************
 *
 *      Revision List
 *
 *      Author          Date            Description
 *      ------			----			-----------
 *      Kay Gürtzig     2016.04.08      First Issue (implementing enhancement request #137 / KGU#160)
 *      Kay Gürtzig     2016.04.12      Functionality accomplished
 *      Kay Gürtzig     2016.04.25      Scrolling to last line ensured
 *      Kay Gürtzig     2016.04.26      Converted to JTextPane in order to allow styled output
 *      Kay Gürtzig     2016.09.25      Bugfix #251 averting Nimbus disrespect of panel settings 
 *
 ******************************************************************************************************
 *
 *      Comment:  /
 *         
 ******************************************************************************************************///

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.UIDefaults;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import lu.fisch.structorizer.gui.IconLoader;

@SuppressWarnings("serial")
public class OutputConsole extends JFrame {

	private JPanel panel;
	private JTextPane textPane;
	private StyledDocument doc = null;
	
	public OutputConsole()
	{
		initComponents();
	}
	
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    private void initComponents() {

    	panel = new JPanel();
    	
    	setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setTitle("Structorizer Output Console");
    	this.setIconImage(IconLoader.ico004.getImage());
    	
    	textPane = new JTextPane();
    	// START KGU#255 2016-09-25: Bugfix #251 background setting diddn't work with Nimbus
    	//textPane.setBackground(Color.BLACK);
    	Color bgColor = Color.BLACK;
    	UIDefaults defaults = new UIDefaults();
    	defaults.put("TextPane[Enabled].backgroundPainter", bgColor);
    	textPane.putClientProperty("Nimbus.Overrides", defaults);
    	textPane.putClientProperty("Nimbus.Overrides.InheritDefaults", true);
    	textPane.setBackground(bgColor);
    	// END KGU#255 2016-09-25
    	textPane.setForeground(Color.WHITE);
    	JScrollPane scrText = new JScrollPane(textPane);
    	doc = textPane.getStyledDocument();
    	Color[] colours = {Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.LIGHT_GRAY,
    			Color.MAGENTA, Color.ORANGE, Color.PINK, Color.RED, Color.WHITE, Color.YELLOW};
    	for (Color colour : colours)
    	{
    		Style style = doc.addStyle(colour.toString(), null);
    		style.addAttribute(StyleConstants.Foreground, colour);
    	}
    	panel.setLayout(new BorderLayout());
    	panel.add(scrText, BorderLayout.CENTER);
    	this.add(panel, null);
    	this.setSize(500, 250);
    }
    
    public void clear()
    {
    	try {
			doc.remove(0, doc.getLength());
		} catch (BadLocationException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
    }
    
    /**
     * Appends string _text to the textArea as is, i.e. without additional newline.
     * @param _text - a string
     */
    public void write(String _text)
    {
    	write(_text, textPane.getForeground());
    }

    /**
     * Appends string _text in the specified (foreground) colour to the textArea
     * as is, i.e. without additional newline.
     * TODO Remark: Color attribute doesn't work in the current version
     * @param _text - a string
     * @param _colour - the text colour to use
     */
    public void write(String _text, Color _colour)
    {
    	System.out.println(textPane.getBackground().toString());
    	try {
			this.doc.insertString(doc.getLength(), _text, doc.getStyle(_colour.toString()));
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// Scroll to end (if there is an easier way, I just didn't find it).
    	Rectangle rect = this.textPane.getBounds();
    	rect.y = rect.height - 1;
    	rect.height = 1;
    	this.textPane.scrollRectToVisible(rect);
    }

    /**
     * Appends string _text to the textArea with additional newline.
     * @param _text - a string
     */
    public void writeln(String _text)
    {
    	this.write(_text + "\n", textPane.getForeground());
    }

    /**
     * Appends string _text in the specified (foreground) colour to the textArea
     * with additional newline.
     * TODO Remark: Color attribute doesn't work in the current version
     * @param _text - a string
     * @param _colour - the text colour to use
     */
    public void writeln(String _text, Color _colour)
    {
    	this.write(_text + "\n", _colour);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OutputConsole().setVisible(true);
            }
        });
    }
	
	
}
