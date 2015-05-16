package jumpingalien.model.statement;

import jumpingalien.model.Program;
import jumpingalien.model.expression.*;
import jumpingalien.part3.programs.SourceLocation;

public class BreakS implements Statement{
	
	private SourceLocation sourceLocation;
	
	public BreakS(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	@Override
	public void execute(Program program, Expression condition) {
		SourceLocation srcLoc = condition.getSourceLocation();
		BooleanE falseE = new BooleanE(false, srcLoc);
		condition = falseE;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
