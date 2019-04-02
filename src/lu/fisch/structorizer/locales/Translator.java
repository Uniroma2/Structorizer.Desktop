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

package lu.fisch.structorizer.locales;

/*
 ******************************************************************************************************
 *
 *      Author:         Bob Fisch
 *
 *      Description:    This class represents the frame of the Translator tool for Structorizer.
 *
 ******************************************************************************************************
 *
 *      Revision List
 *
 *      Author          Date            Description
 *      ------          ----            -----------
 *      Bob Fisch       2016-08-01      First Issue
 *      Kay Gürtzig     2016-09-05      Structural redesign of locale button generation (no from the Locales list)
 *      Kay Gürtzig     2016-09-06      Opportunity to reload a saved language file to resume editing it
 *      Kay Gürtzig     2016-09-09      Handling of unsaved changes improved, loadLocale() API modified,
 *                                      command line parameter "-test" introduced to re-allow full consistency check
 *      Kay Gürtzig     2016-11-02      Issue #81: Scaling as DPI awareness workaround
 *      Kay Gürtzig     2016-11-09      Issue #81: scaleFactor ensured to be >= 1; table row height scaling
 *      Kay Gürtzig     2017-11-20      Issue #400: Ensures key listeners on buttons (also preparing enh. #425)
 *      Kay Gürtzig     2017-12-11/12   Enh. #425: Support for Find mechanism
 *      Kay Gürtzig     2017-12-18      Enh. #425: Missing key binding for Ctrl-F added to the tabs component
 *      Kay Gürtzig     2019-02-05      Field NSDControl disabled (isn't actually need anymore)
 *      Kay Gürtzig     2019-03-24      Issue #712: We should at least cache the last saving folder, then we
 *                                      ought to have look whether Local filed cachedFilename might be set on
 *                                      saving.
 *      Kay Gürtzig     2019-03-28      Issue #712: Further usability improvements (current directory also
 *                                      used for loading, save button coloured together with locale button)
 *
 ******************************************************************************************************
 *
 *      Comment:		/
 *
 ******************************************************************************************************
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import lu.fisch.structorizer.gui.IconLoader;
import lu.fisch.structorizer.gui.NSDController;
import lu.fisch.structorizer.io.Ini;
import lu.fisch.utils.StringList;

/**
 *
 * @author robertfisch
 */
@SuppressWarnings("serial")
// START KGU 2016-08-04: Issue #220
//public class Translator extends javax.swing.JFrame
public class Translator extends javax.swing.JFrame implements PropertyChangeListener, DocumentListener
// END KGU 2016-08-04
{
    
    private final Locales locales = Locales.getInstance();
    private final HashMap<String,JTable> tables = new HashMap<String,JTable>();
    
    private String loadedLocaleName = null;
    public static Locale loadedLocale = null;
    // START KGU#694 2019-03-24: Issue #712
    private File currentDirectory = null;
    // END KGU#694 2019-03-24
    
    // START KGU 2016-08-04: Issue #220
    // Button colour for saved but still cached modifications
    private static final Color savedColor = new Color(170,255,170);
    // Standard button background colour (for restauring original appearance)
    private Color stdBackgroundColor = null;
    // END KGU 2016-08-04

    private static Translator instance = null;
    
    //private NSDController NSDControl = null;

    // START KGU#418 2017-12-11: Enh. #425
    private TranslatorFindDialog searchDialog = null;
    // END KGU#418 2017-12-11
    
    public static Translator getInstance() 
    {
        if(instance==null) instance = new Translator();
        return instance;
    }
    
    /**
     * Creates new form MainFrame
     */
    private Translator() {
        // START KGU 2016-09-05: Bugfix - We must ensure that the default locale had been loaded (to create all tabs)
        Locales.getInstance().getDefaultLocale();
        // END KGU 2016-09-05
        
        // START KGU#287 2016-11-02: Issue #81 (DPI awareness workaround)
        //initComponents();
        Ini ini = Ini.getInstance();
        // START KGU#287 2016-11-09: Issue #81 update: no longer rounded but ensured to be >= 1
        //double scaleFactor = Double.valueOf(ini.getProperty("scaleFactor","1")).intValue();
        double scaleFactor = Double.valueOf(ini.getProperty("scaleFactor","1"));
        if (scaleFactor < 1) scaleFactor = 1.0;
        // END KGU#287 2016-11-09
        initComponents(scaleFactor);
        // END KGU#287 2016-11-02
        
        button_preview.setVisible(false);
        
        // START KGU 2016-08-04: Issue #220
        // set icon depending on OS ;-)
        String os = System.getProperty("os.name").toLowerCase();
        setIconImage(IconLoader.getIcon(0).getImage());
        /*if (os.indexOf("windows") != -1) 
        {
            setIconImage(IconLoader.getIcon(0).getImage());
        } 
        else*/
        if (os.indexOf("mac") != -1) 
        {
            setIconImage(IconLoader.icoNSD.getImage());
        }
        this.setTitle("Structorizer Translator");
        // START KGU#287 2016-11-02: Issue #81 (DPI awareness workaround)
        //setSize(1000, 500);	// with less width the save button was invisible
        setSize((int)(1000*scaleFactor), (int)(500*scaleFactor));
        // END KGU#287 2016-11-02
        stdBackgroundColor = button_empty.getBackground();	// for resetting
        // END KGU 2016-08-04
        
        // disable some buttons
        button_save.setEnabled(false);
        tabs.setEnabled(false);
        
        // initialise the header text
        headerText.setText("Please load a language!");
        // START KGU 2016-08-04: Issue #220
        headerText.setEditable(false);
        // END KGU 2016-08-04

        // loop through all sections
        ArrayList<String> sectionNames = locales.getSectionNames();
        for (int i = 0; i < sectionNames.size(); i++) {
            // get the name
            String sectionName = sectionNames.get(i);
            
            // create a new tab
            Tab tab = new Tab();
            
            // add it to the panel
            tabs.add(sectionName, tab);
            
            // store a reference
            JTable table = tab.getTable();
            tables.put(sectionName, table);
            // START KGU 2016-08-04: Issue #220
            table.addPropertyChangeListener(this);
            // END KGU 2016-08-04
            
            // START KGU#287 2016-11-09: Issue #81 (DPI awareness workaround)
            if (scaleFactor > 2.0) {
                table.setRowHeight((int)Math.ceil(table.getRowHeight() * (scaleFactor - 1)*0.75));
            }
            // END KGU#287 2016-11-09
            
            // set the name
            table.getColumnModel().getColumn(1).setHeaderValue(Locales.DEFAULT_LOCALE);
            table.getTableHeader().repaint();
            
            // fill the table with the keys and the values
            // from the default loadedLocale
            DefaultTableModel model = ((DefaultTableModel)table.getModel());
            ArrayList<String> keys = locales.getDefaultLocale().getKeyValues(sectionName);
            for (int j = 0; j < keys.size(); j++) {
                String key = keys.get(j);
                model.addRow(key.trim().split("=", 2));
            }
        }
        
        // CHECK WE NEED TO IMPLEMENT
        // - default loadedLocale is missing strings others have
        checkMissingStrings();
        // - default loadedLocale contains duplicated strings
        checkForDuplicatedStrings();

        /******************************
         * Set onClose event
         ******************************/
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent e) 
            {
                String[] localeNames = locales.getNames();
                StringList unsavedLocales = new StringList();
                for (int i = 0; i < localeNames.length; i++)
                {
                    if (locales.getLocale(localeNames[i]).hasUnsavedChanges)
                    {
                        for (int j = 0; j < Locales.LOCALES_LIST.length; j++)
                        {
                            if (Locales.LOCALES_LIST[j][0].equals(localeNames[i]))
                            {
                                unsavedLocales.add("    " + localeNames[i] +
                                        " (" + Locales.LOCALES_LIST[j][1] + ")");
                            }
                        }
                    }
                }
                if (unsavedLocales.count() > 0)
                {
                    int answer = JOptionPane.showConfirmDialog (null, 
                            "There are unsaved changes in following locales:\n"
                            + unsavedLocales.getText() + "\nSure to close?", 
                            "Unsaved Changes",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                        if (answer == JOptionPane.YES_OPTION)
                    {
                        dispose();
                    }
                }
                else
                {
                    dispose();
                }
            }
        });
    
    }
    
    public boolean loadLocale(String localeName, java.awt.event.ActionEvent evt, boolean toLoadFromFile)
    {
        ((JButton)evt.getSource()).setName(localeName);
        //((JButton)evt.getSource()).setToolTipText(localeName);
        
        headerText.getDocument().removeDocumentListener(this);

        // backup current loadedLocale
        if (loadedLocale != null && loadedLocaleName != null)
        {
            // Check if user wants to discard changes
            if (loadedLocaleName.equals(localeName)
                    &&
                    (loadedLocale.hasUnsavedChanges
                            || 
                            loadedLocale.hasCachedChanges() &&
                            !((loadedLocale.cachedFilename != null) && toLoadFromFile && !loadedLocale.hasUnsavedChanges)
                    )
                ||
                !loadedLocaleName.equals(localeName) &&
                locales.getLocale(localeName).hasUnsavedChanges && toLoadFromFile)
            {
                String question = locales.getLocale(localeName).hasUnsavedChanges ? "Do you want to discard all changes for" : "Do you want to reload the released locale";
                int answer = JOptionPane.showConfirmDialog (null, 
                        question + " \"" + localeName + "\"?", 
                        "Existing Changes",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (answer == JOptionPane.YES_OPTION) {
                    // discard all cached changes if any
                    JButton button = (JButton) getComponentByName(loadedLocaleName);
                    loadedLocale.values.clear();
                    loadedLocale.cachedHeader.clear();
                    loadedLocale.hasUnsavedChanges = false;
                    loadedLocale.cachedFilename = null;
                    button.setBackground(this.stdBackgroundColor);
                }
                // START KGU 2016-09-05: Bugfix - if the user doesn't want to discard changes we shouldn't continue
                else {          
                    headerText.getDocument().addDocumentListener(this);
                    return false;
                }
                // END KGU 2016-09-05
            }
            
            cacheUnsavedData();
        }
        
        headerText.setText(locales.getLocale(localeName).getHeader().getText());
        loadedLocale = locales.getLocale(localeName);
        
        // First check if we have some cached values
        // START KGU#231 2016-08-09: Issue #220
        // Take care of a modified header
        if (!loadedLocale.cachedHeader.isEmpty())
        {
            headerText.setText(loadedLocale.cachedHeader.getText());
        }
        // END KGU#231 2016-08-09
        if (loadedLocale.values.size() > 0)
        {
            // Present a different column header if the locale data were from file
            String column2Header = localeName;
            if (loadedLocale.cachedFilename != null)
            {
                column2Header += " (" + loadedLocale.cachedFilename + ")";
            }
            // loop through all sections
            ArrayList<String> sectionNames = locales.getSectionNames();
            for (int i = 0; i < sectionNames.size(); i++) {
                // get the name of the section
                String sectionName = sectionNames.get(i);

                // fetch the corresponding table
                JTable table = tables.get(sectionName);

                // put the label on the column
                table.getColumnModel().getColumn(2).setHeaderValue(column2Header);
                table.getTableHeader().repaint();

                // get a reference to the model
                DefaultTableModel model = ((DefaultTableModel)table.getModel());

                // get the strings and put them into the right row
                for (int r = 0; r < model.getRowCount(); r++) {
                    // get the key
                    String key = ((String) model.getValueAt(r, 0)).trim();
                    // put the value
                    model.setValueAt(loadedLocale.values.get(sectionName).get(key), r, 2);
                }
            }
        }
        else {
            // loop through all sections
            ArrayList<String> sectionNames = locales.getSectionNames();
            for (int i = 0; i < sectionNames.size(); i++) {
                // get the name of the section
                String sectionName = sectionNames.get(i);

                // fetch the corresponding table
                JTable table = tables.get(sectionName);

                // put the label on the column
                table.getColumnModel().getColumn(2).setHeaderValue(localeName);
                table.getTableHeader().repaint();

                // get a reference to the model
                DefaultTableModel model = ((DefaultTableModel)table.getModel());

                // get the needed loadedLocale and the corresponding section
                Locale locale = locales.getLocale(localeName);

                // get the strings and put them into the right row
                for (int r = 0; r < model.getRowCount(); r++) {
                    // get the key
                    String key = ((String) model.getValueAt(r, 0)).trim();
                    // put the value
                    model.setValueAt(locale.getValue(sectionName, key), r, 2);
                }
            }
        }

        // enable the buttons
        button_save.setEnabled(true);
        // START KGU#694 2019-03-28: Issue #712 - save button should have same attention as locale button
        if (loadedLocale.hasUnsavedChanges) {
            button_save.setBackground(Color.GREEN);
        } else {
            button_save.setBackground(this.stdBackgroundColor);
        }
        // END KGU#694 2019-03-28
        tabs.setEnabled(true);
        headerText.setEditable(true);
        
        // remember the loaded loadedLocale name
        loadedLocaleName = localeName;

        headerText.getDocument().addDocumentListener(this);
        return true;
    }
    
    // START KGU#231 2016-08-09: Issue #220
    private void cacheUnsavedData()
    {
        if (loadedLocale.hasUnsavedChanges || loadedLocale.cachedFilename != null) {
            //JButton button = (JButton) getComponentByName(loadedLocaleName);
            //button.setToolTipText(loadedLocaleName + " - cached!");
            
            // cache header modifications
            loadedLocale.cachedHeader = StringList.explode(headerText.getText(), "\n");

            // loop through all sections in order to merge the values
            ArrayList<String> sectionNames = locales.getSectionNames();
            for (int i = 0; i < sectionNames.size(); i++) {
                // get the name of the section
                String sectionName = sectionNames.get(i);
                loadedLocale.values.put(sectionName, new LinkedHashMap<String, String>());

                // fetch the corresponding table
                JTable table = tables.get(sectionName);

                // get a reference to the model
                DefaultTableModel model = ((DefaultTableModel)table.getModel());

                // get the strings and put them into loadedLocale
                for (int r = 0; r < model.getRowCount(); r++) {
                    // get the key
                    String key = ((String) model.getValueAt(r, 0)).trim();
                    // get the value
                    String value = ((String) model.getValueAt(r, 2)).trim();
                    // put the value
                    loadedLocale.values.get(sectionName).put(key, value);
                }
            }
        }
    }
    // END KGU#231 2016-08-09
    
    // START KGU#244 2016-06-09: Allow to reload a translation file
    private boolean presentLocale(Locale locale)
    {
        // first check if we have some cached values
        boolean differs = loadedLocale.getHeader().count() != locale.getHeader().count();
        if (!differs)
        {
            StringList header1 = loadedLocale.getHeader();
            StringList header2 = locale.getHeader();
            for (int line = 0; !differs && line < Math.min(header1.count(), header2.count()); line++)
            {
                differs = !header1.get(line).equals(header2.get(line));
            }
        }
        if (differs)
        {
            headerText.getDocument().removeDocumentListener(this);
            headerText.setText(locale.getHeader().getText());
            headerText.getDocument().addDocumentListener(this);
        }
        loadedLocale.cachedFilename = locale.getFilename();
        String column2Header = loadedLocaleName + " (" + locale.getFilename() + ")";
        // loop through all sections
        ArrayList<String> sectionNames = locales.getSectionNames();
        for (int i = 0; i < sectionNames.size(); i++) {
            // get the name of the section
            String sectionName = sectionNames.get(i);

            // fetch the corresponding table
            JTable table = tables.get(sectionName);
            // No need to trigger property change events here, we know what we do
            table.removePropertyChangeListener(this);

            // put the label on the column
            table.getColumnModel().getColumn(2).setHeaderValue(column2Header);
            table.getTableHeader().repaint();

            // get a reference to the model
            DefaultTableModel model = ((DefaultTableModel)table.getModel());

            // get the strings and put them into the right row
            for (int r = 0; r < model.getRowCount(); r++) {
                // get the key
                String key = ((String) model.getValueAt(r, 0)).trim();
                // Test the value
                if (locale.valueDiffersFrom(key, (String)model.getValueAt(r, 2)))
                {
                    differs = true;
                }
                // put the value
                model.setValueAt(locale.getValue(sectionName, key), r, 2);
            }
            // Table loaded, from now on react to user manipulations again
            table.addPropertyChangeListener(this);
        }
        return differs;
    }
    // END KGU#244 2016-09-05
    
    private void checkMissingStrings()
    {
        System.out.println("--[ checkMissingStrings ]--");

        // loop through all locales
        String[] localeNames = locales.getNames();
        ArrayList<String> sectionNames = locales.getSectionNames();
        ArrayList<String> keys = new ArrayList<String>();
        for (int i = 0; i < localeNames.length; i++) {
            String localeName = localeNames[i];
            for (int s = 0; s < sectionNames.size(); s++) {
                // get the name of the section
                String sectionName = sectionNames.get(s);
                ArrayList<String> localKeys = locales.getLocale(localeName).getKeys(sectionName);
                // check if key already exists before adding it
                for (int j = 0; j < localKeys.size(); j++) {
                    String get = localKeys.get(j);
                    if(!keys.contains(get)) keys.add(get);
                }
            }
        } // now "keys" contains all keys from all locales
        
        // subtract default loadedLocale keys
        Locale locale = locales.getDefaultLocale();
        for (int s = 0; s < sectionNames.size(); s++) {
            // get the name of the section
            String sectionName = sectionNames.get(s);
            ArrayList<String> localKeys = locale.getKeys(sectionName);
            for (int j = 0; j < localKeys.size(); j++) {
                String get = localKeys.get(j);
                keys.remove(get);
            }
        }
        
        if(keys.size()>0)
        {
            for (int i = 0; i < keys.size(); i++) {
                String key = keys.get(i);
                System.out.println("- "+key+" ("+locales.whoHasKey(key)+")");
            }
            
            JOptionPane.showMessageDialog(this, "The reference language file (en.txt) misses strings that have been found in another language file.\n"+
                    "Please take a look at the console output for details.\n\n" +
                    "Translator will terminate immediately in order to prevent data loss ...", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }    
    }
    
    private void checkForDuplicatedStrings()
    {
        System.out.println("--[ checkForDuplicatedStrings ]--");
        boolean error = false;
        
        // get the default loadedLocale
        Locale locale = locales.getDefaultLocale();

        // loop through all sections in order to merge the values
        ArrayList<String> sectionNames = locales.getSectionNames();
        for (int i = 0; i < sectionNames.size(); i++) {
            // get the name of the section
            String sectionName = sectionNames.get(i);
            System.out.println("Section: " + sectionName);

            ArrayList<String> keys = locale.getKeys(sectionName);

            while(!keys.isEmpty())
            {
                String key = keys.get(0);
                keys.remove(0);
                if(keys.contains(key))
                {
                    System.out.println("    - " + key);
                    error = true;
                }
            }
        }

        if(error)
        {
            JOptionPane.showMessageDialog(this, "Duplicated string(s) detected.\nPlease read the console output!\n\nTranslator is closing now!", "Error", JOptionPane.ERROR_MESSAGE);
            this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
    }
    
    
    private Component getComponentByName(String name) {
        return getComponentByName(this.getRootPane(), name);
    }

    private Component getComponentByName(Container root, String name) {
        for (Component c : root.getComponents()) {
            if (name.equals(c.getName())) {
                return c;
            }
            if (c instanceof Container) {
                Component result = getComponentByName((Container) c, name);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // START KGU#287 2016-11-02: Issue #81 (DPI awareness workaround)
    //private void initComponents()
    private void initComponents(double scaleFactor)
    // END KGU#287 2016-11-02
    {
        
        // START KGU#393/KGU#418 2017-11-20: Issues #400, #425
        KeyListener myKeyListener = new KeyListener() {

			@Override
            public void keyPressed(KeyEvent evt) {
                int keyCode = evt.getKeyCode();
                switch (keyCode) {
                case KeyEvent.VK_ESCAPE:
                {
                    dispatchEvent(new WindowEvent(Translator.this, WindowEvent.WINDOW_CLOSING));
                }
                break;
                case KeyEvent.VK_ENTER:
                {
                    Object source = evt.getSource();
                    if (source instanceof JButton) {
                        JButton button = (JButton)source;
                        ActionListener[] actLsnrs = button.getActionListeners();
                        int actionCode = ActionEvent.ACTION_PERFORMED;
                        if (evt.isShiftDown()) {
                            actionCode |= ActionEvent.SHIFT_MASK;
                        }
                        for (ActionListener al: actLsnrs) {
                            al.actionPerformed(new ActionEvent(button, actionCode, loadedLocaleName));;
                        }
                    }
                }
                case KeyEvent.VK_F:
                {
                    if (evt.isControlDown()) {
                        if (searchDialog == null) {
                            searchDialog = new TranslatorFindDialog(Translator.this);
                        }
                        searchDialog.setVisible(true);
                    }
                }
                break;
                }
            }

            @Override
            public void keyTyped(KeyEvent evt) {}
            @Override
            public void keyReleased(KeyEvent evt) {}
            
        };
        // END KGU#393/KGU#418 2017-11-20

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        button_save = new javax.swing.JButton();
        button_empty = new javax.swing.JButton();
        button_preview = new javax.swing.JButton();
        tabs = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        headerText = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        // START KGU 2016-08-04: Wasn't enough as size but too large as minimum size
        //setMinimumSize(new java.awt.Dimension(900, 500));
        setMinimumSize(new java.awt.Dimension((int)(500 * scaleFactor), (int)(300 * scaleFactor)));
        // END KGU 2016-08-04

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setPreferredSize(new java.awt.Dimension((int)(655*scaleFactor), (int)(48*scaleFactor)));

        for (int i = 0; i < Locales.LOCALES_LIST.length; i++)
        {
            final String localeName = Locales.LOCALES_LIST[i][0];
            String localeToolTip = Locales.LOCALES_LIST[i][1];
            if (localeToolTip != null)
            {
                javax.swing.JButton button = new javax.swing.JButton();
                // START KGU#287 2016-11-02:Issue #81 (DPI awareness workaround)
                //button.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lu/fisch/structorizer/gui/icons/locale_"+localeName+".png"))); // NOI18N
                button.setIcon(IconLoader.getLocaleIconImage(localeName)); // NOI18N
                // END KGU#287 2016-11-02
                button.setToolTipText(localeToolTip);
                button.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        button_localeActionPerformed(evt, localeName);
                    }
                });
                // START KGU#393/KGU#418 2017-11-20: Issues #400, #425
                button.addKeyListener(myKeyListener);
                // END KGU#393/KGU#418 2017-11-20
                localeButtons.add(button);
            }
        }

        // START KGU#287 2016-11-02: Issue #81 (DPI awareness workaround)
        //jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 24)); // NOI18N
        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, (int)(24 * scaleFactor))); // NOI18N
        // END KGU#287 2016-11-02
        jLabel1.setText("Load");

        // START KGU#287 2016-11-02:Issue #81 (DPI awareness workaround)
        //button_save.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lu/fisch/structorizer/gui/icons/003_Save.png"))); // NOI18N
        // START KGU#486 2018-02-06: Issue #4
        //button_save.setIcon(IconLoader.getIconImage(getClass().getResource("/lu/fisch/structorizer/gui/icons/003_Save.png"))); // NOI18N
        button_save.setIcon(IconLoader.getIcon(3)); // NOI18N
        // END KGU#486 2018-02-06
        // END KGU#287 2016-11-02
        button_save.setToolTipText("Save changes");
        button_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_saveActionPerformed(evt);
            }
        });
        // START KGU#393/KGU#418 2017-11-20: Issues #400, #425
        button_save.addKeyListener(myKeyListener);
        // END KGU#393/KGU#418 2017-11-20

        // START KGU#287 2016-11-02:Issue #81 (DPI awareness workaround)
        //button_empty.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lu/fisch/structorizer/gui/icons/locale_empty.png"))); // NOI18N
        button_empty.setIcon(IconLoader.getIconImage(getClass().getResource("/lu/fisch/structorizer/gui/icons/locale_empty.png"))); // NOI18N
        // END KGU#287 2016-11-02
        button_empty.setToolTipText("Create new locale");
        button_empty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_emptyActionPerformed(evt);
            }
        });
        // START KGU#393/KGU#418 2017-11-20: Issues #400, #425
        button_empty.addKeyListener(myKeyListener);
        // END KGU#393/KGU#418 2017-11-20

        // START KGU#287 2016-11-02:Issue #81 (DPI awareness workaround)
        //button_preview.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lu/fisch/structorizer/gui/icons/017_Eye.png"))); // NOI18N
        // START KGU#486 2018-02-06: Issue #4 Eye icon as preview here
        //button_preview.setIcon(IconLoader.getIconImage(getClass().getResource("/lu/fisch/structorizer/gui/icons/017_Eye.png"))); // NOI18N
        button_preview.setIcon(IconLoader.getIcon(17)); // NOI18N
        // END KGU#486 2018-02-06
        // END KGU#287 2016-11-02
        button_preview.setToolTipText("Preview in Structorizer");
        button_preview.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button_previewActionPerformed(evt);
            }
        });
        // START KGU#393/KGU#418 2017-11-20: Issues #400, #425
        button_preview.addKeyListener(myKeyListener);
        // END KGU#393/KGU#418 2017-11-20

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        SequentialGroup sGroup = jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1);
        for (javax.swing.JButton button: localeButtons)
        {
            sGroup = sGroup.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(button);
        }
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sGroup
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_empty)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 289, Short.MAX_VALUE)
                .addComponent(button_preview)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button_save)
                .addContainerGap())
        );
        int btnHeight = (int)(35 * scaleFactor);
        ParallelGroup pGroup = jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel1)
                .addComponent(button_preview, javax.swing.GroupLayout.PREFERRED_SIZE, btnHeight, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(button_empty, javax.swing.GroupLayout.PREFERRED_SIZE, btnHeight, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(button_save, javax.swing.GroupLayout.PREFERRED_SIZE, btnHeight, javax.swing.GroupLayout.PREFERRED_SIZE);
        for (javax.swing.JButton button: localeButtons)
        {
            pGroup = pGroup.addComponent(button, javax.swing.GroupLayout.PREFERRED_SIZE, btnHeight, javax.swing.GroupLayout.PREFERRED_SIZE);
        }
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pGroup)
                .addContainerGap(7, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.BorderLayout());

        // START KGU#287 2016-11-02: Issue #81 (DPI awareness workaround
        //headerText.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        headerText.setFont(new java.awt.Font("Monospaced", 0, (int)(10*scaleFactor))); // NOI18N
        // END KGU#287 2016-11-02
        jScrollPane2.setViewportView(headerText);

        jPanel2.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        tabs.addTab("Header", jPanel2);
        // START KGU#418 2017-12-18: Issue #425 - we needed a key binding on the tabs
        tabs.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
        		KeyStroke.getKeyStroke(KeyEvent.VK_F, InputEvent.CTRL_DOWN_MASK), "openFindDialog");
        tabs.getActionMap().put("openFindDialog", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent evt) {
        		if (searchDialog == null) {
        			searchDialog = new TranslatorFindDialog(Translator.this);
        		}
        		searchDialog.setVisible(true);
			}
        });
        // END KGU#418 2017-12-18

        getContentPane().add(tabs, java.awt.BorderLayout.CENTER);
        tabs.getAccessibleContext().setAccessibleName("Strings");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void button_localeActionPerformed(java.awt.event.ActionEvent evt, String localeName)
    {
        boolean fromFile = (evt.getModifiers() & ActionEvent.SHIFT_MASK) != 0;
        // (Re-)Load the selected standard locale - with check on unsaved changes
        boolean done = loadLocale(localeName, evt, fromFile);
        // In case of a user file to be reloaded, the file content overrides the standard
        if (done && fromFile)
        {
            Locale loaded = makeLocaleFromChosenFile(localeName);
            if (loaded != null)
            {
                // Override the data by the loaded locale ...
                boolean diffs = presentLocale(loaded);
                // ... and adjust the button colour accordingly
                ((JButton)evt.getSource()).setBackground(diffs ? savedColor : stdBackgroundColor);
            }
        }
    }
    
    // START KGU#244 2016-09-05: Allow reloading begun language files
    private Locale makeLocaleFromChosenFile(String localeName) {
        Locale extLocale = null;
        JFileChooser dlgOpen = new JFileChooser();
        dlgOpen.setDialogTitle("Load saved translations for <"+localeName+"> from...");
        // set directory
        // START KGU#694 2019-03-28: Enh. #712 - we ought to consider the last used directory
        //dlgOpen.setCurrentDirectory(new File(System.getProperty("user.home")));
        if (currentDirectory != null) {
            dlgOpen.setCurrentDirectory(currentDirectory);
        }
        else {
            dlgOpen.setCurrentDirectory(new File(System.getProperty("user.home")));
        }
        // END KGU#694 2019-03-28
        // config dialogue
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Structorizer language file", "txt");
        dlgOpen.addChoosableFileFilter(filter);
        dlgOpen.setFileFilter(filter);
        // show & get result
        int result = dlgOpen.showOpenDialog(this);
        // react on result
        if (result == JFileChooser.APPROVE_OPTION) {
            String filename = dlgOpen.getSelectedFile().getAbsoluteFile().toString();
            // Create a new Locale from it
            extLocale = (new Locale(filename));
            // START KGU#694 2019-03-28: Enh. #712 - we ought to remember the last used directory
            currentDirectory = dlgOpen.getSelectedFile().getParentFile();
            // END KGU#694 2019-03-28
        }
        return extLocale;
    }
    // END KGU#244 2016-09-05

    private Locale getComposedLocale()
    {
        // load a copy of the default loadedLocale
        Locale locale = locales.getDefaultLocale().loadCopyFromFile();
        
        // put the header the loadedLocale to save
        locale.setHeader(StringList.explode(headerText.getText(), "\n"));
        
        // loop through all sections in order to merge the values
        ArrayList<String> sectionNames = locales.getSectionNames();
        for (int i = 0; i < sectionNames.size(); i++) {
            // get the name of the section
            String sectionName = sectionNames.get(i);

            // fetch the corresponding table
            JTable table = tables.get(sectionName);

            // get a reference to the model
            DefaultTableModel model = ((DefaultTableModel)table.getModel());

            // get the strings and put them into loadedLocale
            for (int r = 0; r < model.getRowCount(); r++) {
                // get the key
                String key = ((String) model.getValueAt(r, 0)).trim();
                // get the value
                String value = ((String) model.getValueAt(r, 2)).trim();
                // put the value
                locale.setValue(sectionName, key, value);
            }
        }
        
        return locale;
    }

    private void button_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_saveActionPerformed
        // get the composed locale
        Locale locale = getComposedLocale();

        // now ask where to save the data
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save as");
        String proposedFilename = loadedLocale.cachedFilename;
        if (proposedFilename == null)
        {
            proposedFilename = loadedLocaleName+".txt";
        }
        // START KGU#694 2019-03-24: Issue #712
        if (this.currentDirectory != null && this.currentDirectory.isDirectory()) {
            fileChooser.setCurrentDirectory(this.currentDirectory);
        }
        // END KGU#694 2019-03-24
        fileChooser.setSelectedFile(new File(proposedFilename));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) 
        {
            File fileToSave = fileChooser.getSelectedFile();
            
            boolean save = true;
            
            if (fileToSave.exists() && !fileToSave.isDirectory() && !fileToSave.getAbsolutePath().equals(loadedLocale.cachedFilename)) { 
                if (JOptionPane.showConfirmDialog(this, 
                    "Are you sure to override the file <"+fileToSave.getName()+">?", "Override file?", 
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.NO_OPTION) {
                    save=false;
                }

            }
            
            if (save) try
            {
                FileOutputStream fos = new FileOutputStream(fileToSave);
                Writer out = new OutputStreamWriter(fos, "UTF8");
                out.write(locale.getText());
                out.close();
                // START KGU 2016-08-04: #220
                JButton button = (JButton) getComponentByName(loadedLocaleName);
                if (button != null)
                {
                    //button.setBackground(stdBackgroundColor);
                    button.setBackground(savedColor);
                }
                cacheUnsavedData();
                loadedLocale.hasUnsavedChanges = false;
                // END KGU 2016-08-04
                // START KGU#694 2019-03-24/28: Issue #712
                this.button_save.setBackground(stdBackgroundColor);
                this.currentDirectory = fileToSave.getParentFile();
                boolean updateTableHeader = loadedLocale.cachedFilename == null;
                loadedLocale.cachedFilename = fileToSave.getAbsolutePath();
                String column2Header = loadedLocaleName + " (" + loadedLocale.cachedFilename + ")";
                if (updateTableHeader) {
                    for (String sectionName: locales.getSectionNames()) {
                        // fetch the corresponding table
                        JTable table = tables.get(sectionName);
                        // No need to trigger property change events here, we know what we do
                        table.removePropertyChangeListener(this);

                        // put the label on the column
                        table.getColumnModel().getColumn(2).setHeaderValue(column2Header);
                        table.getTableHeader().repaint();

                        // Table header modified, from now on react to user manipulations again
                        table.addPropertyChangeListener(this);
                    }
                }
                // END KGU#694 2019-03-24/28
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(this,
                        "Error while saving language file\n" + e.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }     
    }//GEN-LAST:event_button_saveActionPerformed

    private void button_emptyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_emptyActionPerformed
        loadLocale("empty", evt, false);
    }//GEN-LAST:event_button_emptyActionPerformed

    private void button_previewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button_previewActionPerformed
        // get the composed locale
        Locale locale = getComposedLocale();

        // update the special "preview" locale with the generated body
        Locales.getInstance().getLocale("preview").setBody(locale.getBody());
        // make it the actual locale
        Locales.getInstance().setLocale("preview");

        /*
        if(NSDControl!=null) {
            NSDControl.setLocale(StringList.explode(locale.getText(), "\n"));
        }*/
    }//GEN-LAST:event_button_previewActionPerformed

    public static void launch(final NSDController NSDControl)
    {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Translator translator = getInstance();
                translator.setNSDControl(NSDControl);
                translator.setVisible(true);
                // START KGU 2016-08-04: Issue #220
                //translator.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // END KGU 2016-8-04
            }
        });
    }
    
    public void setNSDControl(NSDController NSDControl) {
        //this.NSDControl = NSDControl;
        //button_preview.setVisible(true);
        button_preview.setVisible(NSDControl != null);
    }
    
    
    
    // START KGU#231 2016-08-04: Issue #220
    public void propertyChange(PropertyChangeEvent pcEv) {
       // Check if it was triggered by the termination of some editing activity (i.e. the cell editor was dropped)
        if (pcEv.getPropertyName().equals("tableCellEditor") && pcEv.getNewValue() == null)
        {
            for (String sectionName: locales.getSectionNames())
            {
                JTable table = tables.get(sectionName);
                if (pcEv.getSource().equals(table))
                {
                    int rowNr = table.getSelectedRow();
                    DefaultTableModel tm = (DefaultTableModel) table.getModel();
                    Object val = tm.getValueAt(rowNr, 2);
                    if (val != null && val instanceof String && !((String)val).trim().isEmpty())
                    {
                        flagAsUnsaved();
                        break;
                    }
                }
            }
        }
    }
    // END KGU#231 2016-08-04
    
    // START KGU#231 2016-08-09: Issue #220
    protected void flagAsUnsaved()
    {
        loadedLocale.hasUnsavedChanges = true;
        JButton button = (JButton) getComponentByName(loadedLocaleName);
        if (button != null)
        {
            button.setBackground(Color.green);
        }
        // START KGU#694 2019-03-28: Issue #712
        // The save button needs at least as much attention as the locale butto now
        button_save.setBackground(Color.GREEN);
        // END KGU#694 2019 2019-03-28
    }
    // END KGU#231 2016-08-09

    // START KGU#233 2016-08-08: Issue #224 - Ensure the visibility of the table grids on LaF change
    public static void updateLookAndFeel()
    {
        if (instance != null)
        {
            try {
                javax.swing.SwingUtilities.updateComponentTreeUI(instance);
                if (!javax.swing.UIManager.getLookAndFeel().getName().equals("Nimbus"))
                {
                    for (JTable tbl: instance.tables.values())
                    {
                        tbl.setShowGrid(true);
                    }
                }
                // START KGU#418 2017-12-11: Enh. #425
                if (instance.searchDialog != null) {
                	javax.swing.SwingUtilities.updateComponentTreeUI(instance.searchDialog);
                	instance.searchDialog.adaptToNewLaF();;
                }
                // END KGU#418 2017-12-11
            }
            catch (Exception ex) {}
        }
    }
    // END KGU#233 2016-08-08


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Translator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Translator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Translator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Translator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        // START KGU 2016-09-09: With command line option "-test" load all locales such that the basic tests can be run sensibly
        boolean fullTest = false;
        for (int i = 0; !fullTest && i < args.length; i++)
        {
            fullTest = args[i].equalsIgnoreCase("-test");
        }
        if (fullTest)
        {
            for (int i = 0; i < Locales.LOCALES_LIST.length; i++)
            {
                if (Locales.LOCALES_LIST[i][1] != null)
                {
                    Locales.getInstance().getLocale(Locales.LOCALES_LIST[i][0]);
                }
            }
        }
        // END KGU 2016-09-09

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                getInstance().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // START KGU#242 2016-09-05
    private Vector<javax.swing.JButton> localeButtons = new Vector<javax.swing.JButton>();
//    private javax.swing.JButton button_chs;
//    private javax.swing.JButton button_cht;
//    private javax.swing.JButton button_cz;
//    private javax.swing.JButton button_de;
//    private javax.swing.JButton button_en;
//    private javax.swing.JButton button_es;
//    private javax.swing.JButton button_fr;
//    private javax.swing.JButton button_it;
//    private javax.swing.JButton button_lu;
//    private javax.swing.JButton button_nl;
//    private javax.swing.JButton button_pl;
//    private javax.swing.JButton button_pt_br;
//    private javax.swing.JButton button_ru;
    // END KGU#242 2016-09-05
    private javax.swing.JButton button_empty;
    private javax.swing.JButton button_preview;
    private javax.swing.JButton button_save;
    private javax.swing.JTextPane headerText;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane tabs;
    // End of variables declaration//GEN-END:variables

    @Override
    public void changedUpdate(DocumentEvent ev) {
        this.flagAsUnsaved();
    }

    @Override
    public void insertUpdate(DocumentEvent ev) {
        this.flagAsUnsaved();
    }

    @Override
    public void removeUpdate(DocumentEvent ev) {
        this.flagAsUnsaved();
    }

    // START GU#418 2017-12-11: Enh. #425 - Navigation callback for TranslatorFindPattern
	/**
	 * Navigates to the next row downwards (if {@code _forward} is true) or upwards (otherwise)
	 * that has a substring matching the given {@code _pattern} in one of the columns enabled by
	 * {@code _columns} of the current tab. 
	 * @param _pattern - the substring to be searched for (plain string, not a regular expression!)
	 * @param _forward - whether the search direction is downwards
	 * @param _wrapAround - whether the search is be continued at the first (last) row if end (or
	 * beginning) of the tabel is reached without match (otherwise a message box would pop up. 
	 * @param _caseSensitive - whether upper/lower case spelling is to matter (default: false)
	 * @param _columns - flag array specifying which of the table columns are to be searched. 
	 */
	public void gotoNextMatch(String _pattern, boolean _forward, boolean _wrapAround, boolean _caseSensitive, boolean[] _columns) {
		int tabIx = tabs.getSelectedIndex();
		String tabTitle = tabs.getTitleAt(tabIx);
		JTable table = tables.get(tabTitle);
		if (!_caseSensitive) {
			_pattern = _pattern.toLowerCase();
		}
		if (table != null) {
			DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
			int nRows = table.getRowCount();
			int nCols = table.getColumnCount();
			int currRowIx = table.getSelectedRow();
			boolean found = false;
			int row = currRowIx;
			int lastRow = _forward ? nRows : 0;
			int nCycles = _wrapAround ? 2 : 1;
			do {
				if (_forward) {
					while (!found && ++row < lastRow) {
						for (int col = 0; !found && col < nCols; col++) {
							if (!_columns[col]) {
								continue;
							}
							String val = ((String)tableModel.getValueAt(row, col));
							if (val != null) {
								if (!_caseSensitive) {
									val = val.toLowerCase();
								}
								found = val.contains(_pattern);
							}
						}
					}
					// Prepare wrapAraound mode!
					lastRow = currRowIx + 1;
					if (!found) {
						row = -1;
					}
				}
				else {
					while (!found && --row >= lastRow) {
						for (int col = 0; !found && col < nCols; col++) {
							if (!_columns[col]) {
								continue;
							}
							String val = ((String)tableModel.getValueAt(row, col));
							if (val != null) {
								if (!_caseSensitive) {
									val = val.toLowerCase();
								}
								found = val.contains(_pattern);
							}
						}
					}
					// Prepare wrapAraound mode!
					lastRow = currRowIx + 1;
					if (!found) {
						row = nRows;
					}
				}
			} while (!found && --nCycles > 0);
			if (found) {
				table.changeSelection(row, nCols-1, false, false);
			}
			else {
				JOptionPane.showMessageDialog(this,
						"No further occurrance of \"" + _pattern + "\" found.",
						"Find failed", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}
