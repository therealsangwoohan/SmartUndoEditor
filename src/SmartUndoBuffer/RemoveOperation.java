package SmartUndoBuffer;

import java.util.ArrayList;

public class RemoveOperation implements Operation {
    private int index;
    private int length;
    private String chunk;

    public RemoveOperation(int index, int length) {
        this.index = index;
        this.length = length;
        chunk = "";
    }

    public int getIndex() {
        return index;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String getType() {
        return "RemoveOperation";
    }

    @Override
    public String apply(String target) {
        String pre = target.substring(0, index);
        String suf = target.substring(index + length);
        chunk = target.substring(index, index + length);
        return pre + suf;
    }

    @Override
    public Operation inverse() {
        return new AddOperation(index, chunk);
    }

    @Override
    public Operation transform(Operation op) {
        if (op.getType().equals("AddOperation")) {
            return addTransform((AddOperation) op);
        } else if (op.getType().equals("RemoveOperation")) {
            return removeTransform((RemoveOperation) op);
        } else if ((op.getType().equals("SequenceOperation"))) {
            return sequenceTransform((SequenceOperation) op);
        } else {
            return this;
        }
    }

    private Operation addTransform(AddOperation op) {
        if (index + length <= op.getIndex()) {
            return this;
        } else if (op.getIndex() <= index) {
            return new RemoveOperation(index + op.getLength(), length);
        } else {
            RemoveOperation pre = new RemoveOperation(index, op.getIndex() - index);
            RemoveOperation post = new RemoveOperation(op.getIndex() + op.getLength(),
                                                       length - (op.getIndex() - index));
            return new SequenceOperation(new ArrayList<Operation>() {{
                add(pre);
                add(post);
            }});
        }
    }

    private Operation removeTransform(RemoveOperation op) {
        if (index + length <= op.getIndex()) {
            return this;
        } else if (op.getIndex() <= index && index + length <= op.getIndex() + op.getLength()) {
            return new NoOperation();
        } else {
            int intersect = Math.max(0,
                                     length -
                                     Math.max(0, op.getIndex() - index) -
                                     Math.max(0, index + length - op.getIndex() + op.getLength()));
            return new RemoveOperation(index - (op.getLength() - intersect), length - intersect);
        }
    }

    private Operation sequenceTransform(SequenceOperation op) {
        return op.getOperations().stream().reduce(this, (memo, opposed) -> memo.transform(opposed));
    }

    @Override
    public String toString() {
        return "Remove " + chunk + " from " + index + " to " + (index + length);
    }
}
