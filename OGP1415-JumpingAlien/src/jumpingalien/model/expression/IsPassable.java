package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.model.World;
import jumpingalien.part3.programs.SourceLocation;

public class IsPassable implements Expression{

	private Expression expr;
	private SourceLocation sourceLocation;
	
	public IsPassable(Expression expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		int object = (int)this.expr.evaluate(null);
		World world = program.getGameObject().getWorld();
		return (! world.isNotPassable(object));
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}
