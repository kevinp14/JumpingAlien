package jumpingalien.model.type;

import be.kuleuven.cs.som.annotate.Value;

@Value
public class BooleanType extends Type<Boolean> {
	
	public BooleanType(Boolean value) {
		super(value);
	}
}
