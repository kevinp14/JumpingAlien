package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.part3.programs.SourceLocation;

public class WhileS implements Statement {
	
	private Expression condition;
	private Statement body;
	private SourceLocation sourceLocation;
	
	public WhileS(Expression condition, Statement body, SourceLocation sourceLocation){
		this.condition = condition;
		this.body = body;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		boolean cond = (boolean) this.condition.evaluate(program);
		while (cond){
			body.execute(program, this.condition);
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return sourceLocation;
	}

}
