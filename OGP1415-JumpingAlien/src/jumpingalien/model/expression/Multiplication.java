package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of multiplication, to take the product of two doubles.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Multiplication extends DoubleExpression implements Expression {

	/**
	 * Initialize the addition.
	 * 
	 * @param	expr1
	 * 			The first factor of the product.
	 * @param	expr2
	 * 			The second factor of the product.
	 * @param	sourceLocation
	 * 			The location in the source file where this multiplication was called.
	 */
	public Multiplication(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	/**
	 * @param	program
	 * 			The program in which the sum has to be evaluated.
	 * @return	The result of the multiplication.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.multiplication(program);
	}
	
	/**
	 * @param	program
	 * 			The program in which the sum has to be evaluated.
	 * @param	object
	 * 			The object for which the sum has to be evaluated.
	 * @return	The result of the multiplication.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return this.multiplication(program);
	}

	/**
	 * @return	The location in the source file where this multiplication was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
