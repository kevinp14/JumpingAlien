package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of null expressions.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class NullE implements Expression {
	
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the null expression.
	 * 
	 * @param	sourceLocation
	 * 			The location in the source file where this addition was called.
	 */
	public NullE(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the null expression has to be created.
	 * @return	null.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return null;
	}
	
	/**
	 * @param	program
	 * 			The program in which the null expression has to be created.
	 * @param	object
	 * 			The object for which the null expression has to be created.
	 * @return	null.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return null;
	}
	
	/**
	 * @return	The location in the source file where this null was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
