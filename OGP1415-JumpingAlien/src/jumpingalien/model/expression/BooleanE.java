package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class BooleanE implements Expression {
	
	private boolean var;
	private SourceLocation sourceLocation;
	
	public BooleanE(boolean var, SourceLocation sourceLocation){
		this.var = var;
		this.sourceLocation = sourceLocation;
	}
	
	@Override
	public Object evaluate(Program program) {
		return this.var;
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
