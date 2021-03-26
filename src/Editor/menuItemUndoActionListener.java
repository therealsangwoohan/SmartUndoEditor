package Editor;

import SmartUndoBuffer.Buffer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class menuItemUndoActionListener implements ActionListener {
    private Buffer buffer;
    private int i;
    private JTextArea textArea;

    public menuItemUndoActionListener(Buffer buffer, int i, JTextArea textArea) {
        this.buffer = buffer;
        this.i = i;
        this.textArea = textArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        buffer.undo(i);
        TextAreaListener.setActive(false);
        textArea.setText(buffer.getText());
        TextAreaListener.setActive(true);
    }
}
