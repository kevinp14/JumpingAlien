package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

/**
 * A class to make an if statement.
 * 
 * @author	Kevin Peeters (Tweede fase ingenieurswetenschappen)
 * 			Jasper Mariën (Tweede fase ingenieurswetenschappen)
 * @version 1.0
 */
public class IfS implements Statement {
	
	private Expression condition;
	private Statement ifBody;
	private Statement elseBody;
	private SourceLocation sourceLocation;
	
	/**
	 * Initialize the if statement.
	 * 
	 * @param	condition
	 * 			The condition under which the if body should be executed.
	 * @param	ifBody
	 * 			The statement that has to be executed if the given condition is true.
	 * @param	elseBody
	 * 			The statement that has to be executed if the given condition is not true.
	 * @param	sourceLocation
	 * 			The location in the source file where this if statement was called.
	 */
	public IfS(Expression condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation){
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
		this.sourceLocation = sourceLocation;
	}

	/**
	 * @param	program
	 * 			The program in which the if statement has to be executed.
	 * @param	condition
	 * 			The condition under which the if statement has to be executed.
	 * @effect	If the given condition is true, the given if body is executed.
	 * 			| boolean cond = (boolean) this.condition.evaluate(program)
	 * 			| if (cond)
	 * 			|	this.ifBody.execute(program, condition)
	 * @effect	If the given condition is not true, the given else body is executed.
	 * 			| boolean cond = (boolean) this.condition.evaluate(program)
	 * 			| if (!cond)
	 * 			|	this.elseBody.execute(program, condition)
	 */
	@Override
	public void execute(Program program, Expression condition) {
		boolean cond = (boolean) this.condition.evaluate(program);
		if (cond){
			this.ifBody.execute(program, condition);
		}
		if ((this.condition == null) || (cond == false)) {
			if (this.elseBody != null){
				this.elseBody.execute(program, condition);
			}
		}
	}
	
	/**
	 * @param	program
	 * 			The program in which the if statement has to be executed.
	 * @param	condition
	 * 			The condition under which the if statement has to be executed.
	 * @param	object
	 * 			The object for which the if statement has to be executed.
	 * @effect	If the given condition is true, the given if body is executed.
	 * 			| boolean cond = (boolean) this.condition.evaluate(program)
	 * 			| if (cond)
	 * 			|	this.ifBody.execute(program, condition)
	 * @effect	If the given condition is not true, the given else body is executed.
	 * 			| boolean cond = (boolean) this.condition.evaluate(program)
	 * 			| if (!cond)
	 * 			|	this.elseBody.execute(program, condition)
	 */
	@Override
	public void executeForGivenObject(Program program, Expression condition,
			Object object) {
		boolean cond = (boolean) this.condition.evaluateForGivenObject(program, object);
		if (cond){
			this.ifBody.executeForGivenObject(program, condition, object);
		}
		else if (this.elseBody != null){
			this.elseBody.executeForGivenObject(program, condition, object);
		}
	}

	/**
	 * @return	The location in the source file where this if statement was called.
	 * 
	 */
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
