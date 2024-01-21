package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.types.Type;

public class SleepStatement implements Statement{
    private final int number;
    public SleepStatement(int number){
        this.number=number;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (number != 0) {
            state.getExecutionStack().push(new SleepStatement(number - 1));
        }
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new SleepStatement(number);
    }

    @Override
    public String toString() {
        return "sleep("+number+");";
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return typeEnvironment;
    }
}
