package SmartUndoBuffer;

import java.util.ArrayList;

public class SequenceOperation implements Operation {
    private ArrayList<Operation> operations;

    public SequenceOperation(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    @Override
    public String getType() {
        return "SequenceOperation";
    }

    @Override
    public String apply(String target) {
        return applyImpl(target, operations);
    }

    @Override
    public Operation inverse() {
        return new SequenceOperation(inverseImpl(new ArrayList<Operation>(), operations));
    }

    @Override
    public Operation transform(Operation op) {
        ArrayList<Operation> ops = new ArrayList<Operation>();
        for (int i = operations.size() - 1; i >= 0; i--) {
            ops.add(operations.get(i).transform(op));
        }
        return new SequenceOperation(ops);
    }

    private String applyImpl(String target, ArrayList<Operation> listOfOperations) {
        if (listOfOperations.size() == 0) {
            return target;
        }

        Operation op = listOfOperations.get(0);
        ArrayList<Operation> ops = new ArrayList<Operation>(listOfOperations.subList(1, listOfOperations.size()));
        for (Operation operation : ops) {
            operation.transform(op);
        }
        return applyImpl(op.apply(target), ops);
    }

    private ArrayList<Operation> inverseImpl(ArrayList<Operation> acc, ArrayList<Operation> listOfOperations) {
        if (listOfOperations.size() == 0) {
            return acc;
        }

        Operation op = listOfOperations.get(0);
        ArrayList<Operation> ops = new ArrayList<Operation>(listOfOperations.subList(1, listOfOperations.size()));
        for (Operation operation : ops) {
            operation.transform(op);
        }
        acc.add(0, op.inverse());
        return inverseImpl(acc, ops);
    }
}
