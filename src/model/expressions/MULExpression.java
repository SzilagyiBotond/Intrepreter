package model.expressions;

import exception.InterpreterException;
import model.adt.DictionaryInterface;
import model.adt.HeapInterface;
import model.types.IntType;
import model.types.Type;
import model.values.IntValue;
import model.values.Value;

public class MULExpression implements Expression{
    private Expression expression1;
    private Expression expression2;
    public MULExpression(Expression expression1,Expression expression2){
        this.expression1=expression1;
        this.expression2=expression2;
    }
    @Override
    public Value eval(DictionaryInterface<String, Value> table, HeapInterface<Integer, Value> heap) throws InterpreterException {
        IntValue v1=(IntValue) expression1.eval(table,heap);
        IntValue v2=(IntValue) expression2.eval(table, heap);
        return new IntValue((v1.getValue()*v2.getValue())-(v1.getValue()+ v2.getValue()));
    }

    @Override
    public Expression deepCopy() throws InterpreterException {
        return new MULExpression(expression1.deepCopy(),expression2.deepCopy());
    }

    @Override
    public String toString() {
        return "MUL("+expression1+","+expression2+")";
    }

    @Override
    public Type typeCheck(DictionaryInterface<String, Type> typeEnvironment) throws InterpreterException {
        Type t1=expression1.typeCheck(typeEnvironment);
        Type t2=expression2.typeCheck(typeEnvironment);
        if (t1.equals(new IntType()))
            if (t2.equals(new IntType()))
                return new IntType();
            else
                throw new InterpreterException("Second expression is not of type INT");
        else
            throw new InterpreterException("First expression is not of type INT");
    }
}
