package jumpingalien.model.expression;

import jumpingalien.model.Direction;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;

public class SearchObject implements Expression{
	
	private Expression expr;
	private SourceLocation sourceLocation;
	
	public SearchObject(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		Direction direction = (Direction) this.expr.evaluate(program);
		if (direction == Direction.DOWN){
			return null;
		}
		if (direction == Direction.LEFT){
			return null;
		}
		if (direction == Direction.RIGHT){
			return null;
		}
		else{
			return null;
		}
	}
	
	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}

}
