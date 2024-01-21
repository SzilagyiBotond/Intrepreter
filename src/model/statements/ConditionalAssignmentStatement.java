package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.expressions.VariableExpression;
import model.types.BoolType;
import model.types.Type;
import model.values.Value;

public class ConditionalAssignmentStatement implements Statement{
    private String id;
    private Expression expression1;
    private Expression expression2;
    private Expression expression3;
    public ConditionalAssignmentStatement(String id,Expression expression1,Expression expression2,Expression expression3){
        this.id=id;
        this.expression1=expression1;
        this.expression2=expression2;
        this.expression3=expression3;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        state.getExecutionStack().push(new IfStatement(expression1,new AssignmentStatement("v",expression2),new AssignmentStatement("v",expression3)));
        return null;
    }

    @Override
    public String toString() {
        return id+"="+expression1+"?"+expression2+":"+expression3+";";
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new ConditionalAssignmentStatement(id,expression1.deepCopy(),expression2.deepCopy(),expression3.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type expressionType=expression1.typeCheck(typeEnvironment);
        if (expressionType.equals(new BoolType())){
            Type expression2Type=expression2.typeCheck(typeEnvironment.copy());
            if (expression2Type.equals(new VariableExpression("v").typeCheck(typeEnvironment.copy())))
            {
                Type expression3Type=expression3.typeCheck(typeEnvironment.copy());
                if (expression3Type.equals(new VariableExpression("v").typeCheck(typeEnvironment.copy())))
                {
                    return typeEnvironment;
                }else{
                    throw new InterpreterException("CA statement: The third expression is not of the type of the var");
                }
            }else{
                throw new InterpreterException("CA statement: The second expression is not of the type of the var");
            }
        }
        else
            throw new InterpreterException("CA statement: The condition is not of type boolean");
    }
}
