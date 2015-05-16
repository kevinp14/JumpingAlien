package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class NullE implements Expression{
	
	private SourceLocation sourceLocation;
	
	public NullE(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		return null;
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
