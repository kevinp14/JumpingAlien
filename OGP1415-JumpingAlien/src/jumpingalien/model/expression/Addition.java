package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of addition, to take the sum of two doubles.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Addition extends DoubleExpression implements Expression {
	
	/**
	 * Initialize the addition.
	 * 
	 * @param	expr1
	 * 			The first term of the sum.
	 * @param	expr2
	 * 			The second term of the sum.
	 * @param	sourceLocation
	 * 			The location in the source file where this addition was called.
	 */
	public Addition(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the sum has to be evaluated.
	 * @return	The result of the sum.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.sum(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the sum has to be evaluated.
	 * @param	object
	 * 			The object for which the sum has to be evaluated
	 * @return	The result of the sum.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		this.expr1 = (Expression)object;
		return this.sum(program);
	}
	
	/**
	 * @return	The location in the source file where this addition was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
