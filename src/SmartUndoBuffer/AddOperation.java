package SmartUndoBuffer;

public class AddOperation implements Operation {
    private int index;
    private String chunk;
    private int length;

    public AddOperation(int index, String chunk) {
        this.index = index;
        this.chunk = chunk;
        this.length = chunk.length();
    }

    public int getIndex() {
        return index;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String getType() {
        return "AddOperation";
    }

    @Override
    public String apply(String target) {
        String pre = target.substring(0, index);
        String suf = target.substring(index);
        return pre + chunk + suf;
    }

    @Override
    public Operation inverse() {
        return new RemoveOperation(index, length);
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
        if (index > op.getIndex()) {
            return new AddOperation(index + op.getLength(), chunk);
        }
        return this;
    }

    private Operation removeTransform(RemoveOperation op) {
        if (index > op.getIndex()) {
            return new AddOperation(Math.min(op.getLength(), index - op.getIndex()),
                                    chunk);
        }
        return this;
    }

    private Operation sequenceTransform(SequenceOperation op) {
        return op.getOperations().stream().reduce(this, (memo, opposed) -> memo.transform(opposed));
    }

    @Override
    public String toString() {
        return "Add " + chunk + " at " + index;
    }
}
