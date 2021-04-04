package Editor;

import SmartUndoBuffer.Buffer;
import SmartUndoBuffer.Operation;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.HashSet;
import java.util.Set;

public class UndoMenuListener implements MenuListener {
    private JMenu menuUndo;
    private Buffer buffer;
    private JTextArea textArea;
    private Set<Integer> undid;

    public UndoMenuListener(JMenu menuUndo, Buffer buffer, JTextArea textArea) {
        this.menuUndo = menuUndo;
        this.buffer = buffer;
        this.textArea = textArea;
        this.undid = new HashSet<Integer>();
    }

    @Override
    public void menuSelected(MenuEvent e) {
        menuUndo.removeAll();
        for (int i = 0; i < buffer.getOperations().size(); i++) {
            Operation op = buffer.getOperations().get(i);
            if (!undid.contains(i) && !op.getType().equals("NoOperation")) {
                JMenuItem menuItemUndo = new JMenuItem("" + op);
                menuItemUndo.addActionListener(new menuItemUndoActionListener(buffer, i, textArea, undid));
                menuUndo.add(menuItemUndo);
            }
        }
    }

    @Override
    public void menuDeselected(MenuEvent e) {}

    @Override
    public void menuCanceled(MenuEvent e) {}
}
