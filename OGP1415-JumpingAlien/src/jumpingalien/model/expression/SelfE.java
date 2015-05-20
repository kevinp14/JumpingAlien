package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class SelfE implements Expression {
	
	private SourceLocation sourceLocation;
	
	public SelfE(SourceLocation sourceLocation){
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		return program.getGameObject();
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
