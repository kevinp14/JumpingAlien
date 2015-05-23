package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.Type;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to assign a value to a variable.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class Assignment implements Statement {
	
	private String variableName;
	private Expression value;
	private Type<?> variableType;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the assignment.
	 * 
	 * @param	variableName
	 * 			The name of the variable to be assigned.
	 * @param	variableType
	 * 			The type of the variable to be assigned.
	 * @param	value
	 * 			The value the variable should get.
	 * @param	sourceLocation
	 * 			The location in the source file where this assignment was called.
	 */
	public Assignment(String variableName, Type<?> variableType,
	Expression value, SourceLocation sourceLocation){
		this.variableName = variableName;
		this.variableType = variableType;
		this.value = value;
		this.variableType = variableType;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the assignment has to be executed.
	 * @param	condition
	 * 			The condition under which the assignment has to be executed.
	 * @effect	The variable is set by its name to the given value.
	 * 			| Object val = this.value.evaluate(program)
	 * 			| program.setObjectByName(this.variableName, this.variableType, val)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		Type<?> val = (Type<?>)this.value.evaluate(program);
		program.setObjectByName(this.variableName, this.variableType, val);
	}

	/**
	 * @return	The location in the source file where this assignment was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
