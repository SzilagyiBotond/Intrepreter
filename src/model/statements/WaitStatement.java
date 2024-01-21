package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.ValueExpression;
import model.types.Type;
import model.values.IntValue;

public class WaitStatement implements Statement{
    private int number;
    public WaitStatement(int number){
        this.number=number;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        if (number!=0){
            state.getExecutionStack().push(new WaitStatement(number-1));
            state.getExecutionStack().push(new PrintStatement(new ValueExpression(new IntValue(number))));
        }
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new WaitStatement(number);
    }

    @Override
    public String toString() {
        return "wait("+number+");";
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        return typeEnvironment;
    }
}
