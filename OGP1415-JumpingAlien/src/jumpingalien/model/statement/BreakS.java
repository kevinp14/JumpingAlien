package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.*;
import jumpingalien.model.type.BooleanType;
import jumpingalien.part3.programs.SourceLocation;

public class BreakS implements Statement{
	
	private SourceLocation sourceLocation;
	
	public BreakS(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression<BooleanType> condition) {
		SourceLocation srcLoc = condition.getSourceLocation();
		boolean cond = (boolean)condition.evaluate(program);
		while (cond) {
			break;
		}
		this.sourceLocation = srcLoc;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
