package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of booleans.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class BooleanE implements Expression {
	
	private boolean var;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the boolean.
	 * 
	 * @param	var
	 * 			The given boolean.
	 * @param	sourceLocation
	 * 			The location in the source file where this addition was called.
	 */
	public BooleanE(boolean var, SourceLocation sourceLocation){
		this.var = var;
		this.sourceLocation = sourceLocation;
	}
	
	/**
	 * @param	program
	 * 			The program in which the boolean has to be created.
	 * @return	The given boolean.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.var;
	}
	
	/**
	 * @param	program
	 * 			The program in which the boolean has to be created.
	 * @param	object
	 * 			The object for which the boolean has to be created.
	 * @return	The given boolean.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return false;
	}

	/**
	 * @return	The location in the source file where this boolean was created.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
