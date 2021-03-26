package SmartUndoBuffer;

import java.util.ArrayList;

public class Buffer {
    private String text;
    private ArrayList<Operation> operations;

    public String getText() {
        return text;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public Buffer() {
        this.text = "";
        this.operations = new ArrayList<Operation>();
    }

    public Buffer(String text) {
        this.text = text;
        this.operations = new ArrayList<Operation>();
    }

    public void addText(int index, String chunk) {
        AddOperation op = new AddOperation(index, chunk);
        applyOperation(op);
    }

    public void removeText(int index, int length) {
        RemoveOperation op = new RemoveOperation(index, length);
        applyOperation(op);
    }

    public void undo() {
        int index = operations.size() - 1;
        Operation inverse = operations.get(index).inverse();
        ArrayList<Operation> sublist = new ArrayList<Operation>(operations.subList(index + 1, operations.size()));
        Operation transformed = sublist.stream().reduce(inverse, (memo, opposed) -> memo.transform(opposed));
        applyOperation(transformed);
    }

    public void undo(int index) {
        Operation inverse = operations.get(index).inverse();
        ArrayList<Operation> sublist = new ArrayList<Operation>(operations.subList(index + 1, operations.size()));
        Operation transformed = sublist.stream().reduce(inverse, (memo, opposed) -> memo.transform(opposed));
        applyOperation(transformed);
    }

    private void applyOperation(Operation op) {
        text = op.apply(text);
        operations.add(op);
    }

    @Override
    public String toString() {
        return "text: " + text + "\n" + "operations: " + operations.toString();
    }
}
