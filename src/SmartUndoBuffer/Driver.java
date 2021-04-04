package SmartUndoBuffer;

public class Driver {
    public static void main(String[] args) {
        Buffer b = new Buffer();
        b.addText(0, "abcde");
        b.removeText(4, 1);
        b.removeText(3, 1);
        b.removeText(2, 1);
        b.removeText(1, 1);
        b.removeText(0, 1);
        b.undo(0);
        System.out.println(b);
    }
}
