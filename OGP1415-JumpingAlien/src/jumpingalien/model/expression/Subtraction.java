package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of suctraction, to take the difference of two doubles.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Subtraction extends DoubleExpression implements Expression {
	
	/**
	 * Initialize the subtraction.
	 * 
	 * @param	expr1
	 * 			The first term of the difference.
	 * @param	expr2
	 * 			The second term of the difference.
	 * @param	sourceLocation
	 * 			The location in the source file where this subtraction was called.
	 */
	public Subtraction(Expression expr1, Expression expr2,
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the sum has to be evaluated.
	 * @return	The result of the difference.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.difference(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the sum has to be evaluated.
	 * @param	object
	 * 			The object for which the sum has to be evaluated.
	 * @return	The result of the difference.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return this.difference(program);
	}
	
	/**
	 * @return	The location in the source file where this subtraction was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
