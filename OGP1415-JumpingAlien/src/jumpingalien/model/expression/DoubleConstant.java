package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class of doubles.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class DoubleConstant implements Expression {
	
	private double var;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the double.
	 * 
	 * @param	var
	 * 			The given double.
	 * @param	sourceLocation
	 * 			The location in the source file where this double was created.
	 */
	public DoubleConstant(double var, SourceLocation sourceLocation){
		this.var = var;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the double has to be created.
	 * @return	The given double.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return this.var;
	}
	
	/**
	 * @param	program
	 * 			The program in which the double has to be created.
	 * @param	object
	 * 			The object for which the double has to be created.
	 * @return	The given double.
	 * 
	 */
	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return this.var;
	}
	
	/**
	 * @return	The location in the source file where this double was created.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
}
