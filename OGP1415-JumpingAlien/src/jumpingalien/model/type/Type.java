package jumpingalien.model.type;

public class Type<T> {
	private T value;

	public Type(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		return false;
	}
}