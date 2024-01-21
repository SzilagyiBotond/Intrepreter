package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.expressions.LogicExpression;
import model.expressions.RelationalExpression;
import model.expressions.ValueExpression;
import model.types.BoolType;
import model.types.Type;
import model.values.BoolValue;

public class RepeatUntilStatement implements Statement{
    private Statement statement;
    private Expression expression;
    public RepeatUntilStatement(Statement statement,Expression expression){
        this.statement=statement;
        this.expression=expression;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        state.getExecutionStack().push(new WhileStatement(new LogicExpression(expression,new ValueExpression(new BoolValue(true)), LogicExpression.Operand.XOR),statement));
        state.getExecutionStack().push(statement);
        return null;
    }

    @Override
    public String toString() {
        return "(repeat ("+statement+") until "+expression+");";
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new RepeatUntilStatement(statement.deepCopy(),expression.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type=expression.typeCheck(typeEnvironment);
        if(type.equals(new BoolType())){
            return typeEnvironment;
        }else{
            throw new InterpreterException("Expression is not of type boolean");
        }
    }
}
