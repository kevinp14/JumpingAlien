package jumpingalien.model.expression;

import jumpingalien.model.Program;
import jumpingalien.part3.programs.SourceLocation;
import jumpingalien.model.*;
import jumpingalien.model.type.GameObjectType;

public class IsTerrain implements Expression<GameObjectType> {
	
	private Expression<GameObjectType> expr;
	private SourceLocation sourceLocation;
	
	public IsTerrain(Expression<GameObjectType> expr, SourceLocation sourceLocation){
		this.expr = expr;
		this.sourceLocation = sourceLocation;
	}

	@Override
	public Object evaluate(Program program) {
		IsAir air = new IsAir(this.expr, this.sourceLocation);
		IsWater water = new IsWater(this.expr, this.sourceLocation);
		IsMagma magma = new IsMagma(this.expr, this.sourceLocation);
		IsPassable passable = new IsPassable(this.expr, this.sourceLocation);
		return ((air.evaluate(program)) || (water.evaluate(program)) || (magma.evaluate(program))
				passable.evaluate(program));
	}

	@Override
	public SourceLocation getSourceLocation() {
		return this.sourceLocation;
	}
}