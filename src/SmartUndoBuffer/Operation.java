package SmartUndoBuffer;

public interface Operation {
    String getType();
    String apply(String target);
    Operation inverse();
    Operation transform(Operation op);
}
