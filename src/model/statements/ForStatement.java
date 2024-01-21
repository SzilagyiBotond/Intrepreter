package model.statements;

import exception.InterpreterException;
import model.ProgramState;
import model.adt.DictionaryInterface;
import model.expressions.Expression;
import model.expressions.RelationalExpression;
import model.expressions.VariableExpression;
import model.types.IntType;
import model.types.Type;

public class ForStatement implements Statement{

    private String id;
    private Expression expression1;
    private Expression expression2;
    private Expression expression3;
    private Statement statement;
    public ForStatement(String id,Expression expression1,Expression expression2,Expression expression3,Statement statement){
        this.id=id;
        this.expression1=expression1;
        this.expression2=expression2;
        this.expression3=expression3;
        this.statement=statement;
    }
    @Override
    public ProgramState execute(ProgramState state) throws InterpreterException {
        state.getExecutionStack().push(new WhileStatement(new RelationalExpression(new VariableExpression(id),expression2, RelationalExpression.Operand.LESS),new CompoundStatement(statement,new AssignmentStatement(id,expression3))));
        state.getExecutionStack().push(new AssignmentStatement(id,expression1));
        return null;
    }

    @Override
    public String toString() {
        return "for("+id+"="+expression1+";"+id+"<"+expression2+";"+id+"="+expression3+")"+statement;
    }

    @Override
    public Statement deepCopy() throws InterpreterException {
        return new ForStatement(id,expression1.deepCopy(),expression2.deepCopy(),expression3.deepCopy(),statement.deepCopy());
    }

    @Override
    public DictionaryInterface<String, Type> typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type exp1Type=expression1.typeCheck(typeEnvironment);
        if (exp1Type.equals(new IntType()))
        {
            Type exp2Type=expression2.typeCheck(typeEnvironment.copy());
            if (exp2Type.equals(new IntType())){
                Type exp3Type=expression3.typeCheck(typeEnvironment.copy());
                if (exp3Type.equals(new IntType())){
                    statement.typeCheck(typeEnvironment.copy());
                    return typeEnvironment;
                }else{
                    throw new InterpreterException("Expression 3 is not of type INT");
                }
            }else{
                throw new InterpreterException("Expression 2 is not of type INT");
            }
        }else {
            throw new InterpreterException("Expression 1 is not of type INT");
        }
    }
}
