package Editor;

import SmartUndoBuffer.Buffer;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TextAreaListener implements DocumentListener {
    private Buffer buffer;
    private static boolean isActive = true;

    public TextAreaListener(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (isActive) {
            Document document = e.getDocument();
            String chunk = null;
            try {
                chunk = document.getText(e.getOffset(), e.getLength());
            } catch (BadLocationException badLocationException) {
                badLocationException.printStackTrace();
            }
            buffer.addText(e.getOffset(), chunk);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (isActive) {
            buffer.removeText(e.getOffset(), e.getLength());
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (isActive) {
            ;
        }
    }

    public static void setActive(boolean isActive) {
        TextAreaListener.isActive = isActive;
    }
}
