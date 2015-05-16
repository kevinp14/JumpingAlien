package jumpingalien.model.expression;

import jumpingalien.model.GameObject;
import jumpingalien.model.Program;
import jumpingalien.part3.programs.IProgramFactory.Direction;
import jumpingalien.part3.programs.SourceLocation;

public class IsMoving extends DoubleExpression implements Expression{
	
	public IsMoving(Expression expr1, Expression expr2, SourceLocation sourceLocation){
		super(expr1, expr2, sourceLocation);
	}

	@Override
	public Object evaluate(Program program) {
		GameObject gameObject = (GameObject) expr1.evaluate(program);
		Direction direction = (Direction) expr2.evaluate(null);
		if ((direction == Direction.LEFT) || (direction == Direction.RIGHT)){
			return (gameObject.getHorizontalVelocity() != 0);
		}
		else{
			return (gameObject.getVerticalVelocity() != 0);
		}
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
