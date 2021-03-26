package Editor;

import SmartUndoBuffer.Buffer;
import SmartUndoBuffer.Operation;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class UndoMenuListener implements MenuListener {
    private JMenu menuUndo;
    private Buffer buffer;
    private JTextArea textArea;

    public UndoMenuListener(JMenu menuUndo, Buffer buffer, JTextArea textArea) {
        this.menuUndo = menuUndo;
        this.buffer = buffer;
        this.textArea = textArea;
    }

    @Override
    public void menuSelected(MenuEvent e) {
        menuUndo.removeAll();
        for (int i = 0; i < buffer.getOperations().size(); i++) {
            Operation op = buffer.getOperations().get(i);
            JMenuItem menuItemUndo = new JMenuItem("" + op);
            menuItemUndo.addActionListener(new menuItemUndoActionListener(buffer, i, textArea));
            menuUndo.add(menuItemUndo);
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {}

    @Override
    public void menuCanceled(MenuEvent e) {}
}
