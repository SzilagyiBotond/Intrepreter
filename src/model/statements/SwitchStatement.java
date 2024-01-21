package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.types.Type;

public class SwitchStatement implements Statement{
    private Expression expression;
    private Expression expression1;
    private Statement statement1;
    private Expression expression2;
    private Statement statement2;
    private Statement statement3;
    public SwitchStatement(Expression expression,Expression expression1,Statement statement1,Expression expression2,Statement statement2,Statement statement3){
        this.expression=expression;
        this.expression1=expression1;
        this.statement1=statement1;
        this.expression2=expression2;
        this.statement2=statement2;
        this.statement3=statement3;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        state.getExecutionStack().push(new IfStatement(new RelationalExpression(expression,expression1, RelationalExpression.Operand.EQUAL),statement1,new IfStatement(new RelationalExpression(expression,expression2, RelationalExpression.Operand.EQUAL),statement2,statement3)));
        return null;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new SwitchStatement(expression.deepCopy(),expression1.deepCopy(),statement1.deepCopy(),expression2.deepCopy(),statement2.deepCopy(),statement3.deepCopy());
    }

    @Override
    public String toString() {
        return "(switch ("+expression+"\n"+"(case ("+expression1+") : "+statement1+")\n"+"(case ("+expression2+") : "+statement2+")\n"+"(default : "+statement3+"));";
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type type= expression.typeCheck(typeEnvironment);
        Type type1= expression1.typeCheck(typeEnvironment);
        Type type2=expression2.typeCheck(typeEnvironment);
        if(type.equals(type1) && type.equals(type2)){
            statement1.typeCheck(typeEnvironment.copy());
            statement2.typeCheck(typeEnvironment.copy());
            statement3.typeCheck(typeEnvironment.copy());
        }else {
            throw new InterpreterException("Switch statement: The expressions type does not match");
        }
        return typeEnvironment;
    }
}
