package jumpingalien.model.expression;

import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of general expressions to check whether expressions are equals or not.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class GeneralExpression {

	protected Expression expr1;
	protected Expression expr2;
	protected SourceLocation sourceLocation;
	
	/**
	 * Initialize the general expression.
	 * 
	 * @param	expr1
	 * 			The first expression.
	 * @param	expr2
	 * 			The second expression.
	 * @param	sourceLocation
	 * 			The location in the source file where this comparison was called.
	 */
	public GeneralExpression(Expression expr1, Expression expr2, 
			SourceLocation sourceLocation) {
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.sourceLocation = sourceLocation;
	}
	
	/**
	 * @param	program
	 * 			The program for which the comparison has to be evaluated.
	 * @return	True if and only if expr1 equals expr2.
	 */
	public boolean equals(){
		return (this.expr1.evaluate(null) == this.expr2.evaluate(null));
	}
	
	/**
	 * @param	program
	 * 			The program for which the comparison has to be evaluated.
	 * @return	True if and only if expr1 does not equal expr2.
	 */
	public boolean notEquals(){
		return (this.expr1.evaluate(null) != this.expr2.evaluate(null));
	}

}
