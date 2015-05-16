package jumpingalien.model.type;

import jumpingalien.part3.programs.IProgramFactory.Direction;
import be.kuleuven.cs.som.annotate.Value;

@Value
public class DirectionType extends Type<Direction> {

	public DirectionType(Direction direction) {
		super(direction);
	}
}
