package Editor;

import SmartUndoBuffer.Buffer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class menuItemUndoActionListener implements ActionListener {
    private Buffer buffer;
    private int i;
    private JTextArea textArea;
    private Set<Integer> undid;

    public menuItemUndoActionListener(Buffer buffer, int i, JTextArea textArea, Set<Integer> undid) {
        this.buffer = buffer;
        this.i = i;
        this.textArea = textArea;
        this.undid = undid;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buffer.undo(i);
        undid.add(i);
        TextAreaListener.setActive(false);
        textArea.setText(buffer.getText());
        TextAreaListener.setActive(true);
    }
}
