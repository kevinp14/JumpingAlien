package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class DirectionConstant implements Expression{
	private Direction direction;
	private SourceLocation sourceLocation;
	
	public DirectionConstant(Direction direction, SourceLocation sourceLocation){
		this.direction = direction;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		return this.direction;
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
	
}
