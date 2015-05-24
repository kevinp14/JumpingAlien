package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to check if a double is less than the other.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class LessThan extends DoubleExpression implements Expression {
	
	/**
	 * Initialize the less than checker.
	 * 
	 * @param	expr1
	 * 			The first double.
	 * @param	expr2
	 * 			The second double.
	 * @param	sourceLocation
	 * 			The location in the source file where this comparison was called.
	 */
	public LessThan(Expression expr1, Expression expr2,
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the comparison has to be evaluated.
	 * @return	The result of the less than checker.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.lessThan(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the comparison has to be evaluated.
	 * @param	object
	 * 			The object for which the comparison has to be evaluated.
	 * @return	The result of the less than checker.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		this.expr1 = (Expression)object;
		return this.lessThan(program);
	}

	/**
	 * @return	The location in the source file where this comparison was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
