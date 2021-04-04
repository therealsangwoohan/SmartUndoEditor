package Editor;

import SmartUndoBuffer.Buffer;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Scanner;

public class TextEditor implements ActionListener {
    private static JTextArea textArea;
    private Buffer buffer;
    private static int returnValue = 0;


    public TextEditor() {
        JMenuBar menu = new JMenuBar();

        JMenu menuFile = new JMenu("File");
        JMenuItem menuItemNew = new JMenuItem("New");
        JMenuItem menuItemOpen = new JMenuItem("Open");
        JMenuItem menuItemSave = new JMenuItem("Save");
        JMenuItem menuItemQuit = new JMenuItem("Quit");
        menuItemNew.addActionListener(this);
        menuItemOpen.addActionListener(this);
        menuItemSave.addActionListener(this);
        menuItemQuit.addActionListener(this);
        menuFile.add(menuItemNew);
        menuFile.add(menuItemOpen);
        menuFile.add(menuItemSave);
        menuFile.add(menuItemQuit);
        menu.add(menuFile);

        buffer = new Buffer();

        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(new TextAreaListener(buffer));

        JMenu menuUndo = new JMenu("Undo");
        menuUndo.addMenuListener(new UndoMenuListener(menuUndo, buffer, textArea));
        menu.add(menuUndo);

        JFrame frame = new JFrame("Text Edit");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menu);
        frame.add(textArea);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String ingest = null;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();
        if (ae.equals("Open")) {
            returnValue = jfc.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                try {
                    FileReader read = new FileReader(f);
                    Scanner scan = new Scanner(read);
                    while (scan.hasNextLine()) {
                        String line = scan.nextLine() + "\n";
                        ingest += line;
                    }
                    textArea.setText(ingest);
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (ae.equals("Save")) {
            returnValue = jfc.showSaveDialog(null);
            try {
                File f = new File(jfc.getSelectedFile().getAbsolutePath());
                FileWriter out = new FileWriter(f);
                out.write(textArea.getText());
                out.close();
            } catch (FileNotFoundException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f, "File not found.");
            } catch (IOException ex) {
                Component f = null;
                JOptionPane.showMessageDialog(f, "Error.");
            }
        } else if (ae.equals("New")) {
            textArea.setText("");
        } else if (ae.equals("Quit")) {
            System.exit(0);
        }
    }
}

