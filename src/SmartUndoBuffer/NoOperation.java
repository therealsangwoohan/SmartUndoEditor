package SmartUndoBuffer;

public class NoOperation implements Operation {

    @Override
    public String getType() {
        return "NoOperation";
    }

    @Override
    public String apply(String target) {
        return target;
    }

    @Override
    public Operation inverse() {
        return this;
    }

    @Override
    public Operation transform(Operation op) {
        return this;
    }

    @Override
    public String toString() {
        return "No operation";
    }
}
