package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.Expression;
import jumpingalien.model.type.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class IfS implements Statement{
	
	private Expression<BooleanType> condition;
	private Statement ifBody;
	private Statement elseBody;
	private SourceLocation sourceLocation;
	
	public IfS(Expression<BooleanType> condition, Statement ifBody, Statement elseBody,
			SourceLocation sourceLocation){
		this.condition = condition;
		this.ifBody = ifBody;
		this.elseBody = elseBody;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression<BooleanType> condition) {
		boolean cond = (boolean) this.condition.evaluate(program);
		if (cond){
			this.ifBody.execute(program, condition);
		}
		else{
			this.elseBody.execute(program, condition);
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
