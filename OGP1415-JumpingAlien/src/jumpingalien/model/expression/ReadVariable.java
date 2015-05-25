package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.type.Type;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to read a variable.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class ReadVariable implements Expression {
	
	private String variableName;
	private Type<?> variableType;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the read variable.
	 * 
	 * @param	variableName
	 * 			The name of the variable to read.
	 * @param	variableType
	 * 			The type of the variable to read.
	 * @param	sourceLocation
	 * 			The location in the source file where this read variable was called.
	 */
	public ReadVariable(String variableName, Type<?> variableType, SourceLocation sourceLocation){
		this.variableName = variableName;
		this.variableType = variableType;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the variable has to be read.
	 * @return	The value of the object by name.
	 * 
	 */
	@Override
	public Object evaluate(Program program) {
		return program.getObjectByName(this.variableName, this.variableType);
	}
	

	@Override
	public Object evaluateForGivenObject(Program program, Object object) {
		return program.getObjectByName(this.variableName, this.variableType);
	}

	/**
	 * @return	The location in the source file where this variable was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
