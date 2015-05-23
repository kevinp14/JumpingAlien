package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to generate a random double.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class RandomE implements Expression {
	
	private Expression maxValue;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the random double.
	 * 
	 * @param	maxValue
	 * 			The maximum value the double can have.
	 * @param	sourceLocation
	 * 			The location in the source file where this random double was called.
	 */
	public RandomE(Expression maxValue, SourceLocation sourceLocation){
		this.maxValue = maxValue;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the random double has to be created.
	 * @return	A random double between 0 and the maximum.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		double maxValue = (double)this.maxValue.evaluate(program);
		return Math.random() * maxValue;
	}
	
	/**
	 * @param	program
	 * 			The program in which the random double has to be created.
	 * @param	object
	 * 			The object for which the random double has to be created.
	 * @return	A random double between 0 and the maximum.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return false;
	}

	/**
	 * @return	The location in the source file where this random double was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
