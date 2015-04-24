package jumpingalien.model;

import java.util.ArrayList;

public class School {
	private ArrayList<Slime> slimes = new ArrayList<>();
	
	public School() {
	}
	
	protected void addSlimeToSchool(Slime slime){
		this.slimes.add(slime);
	}
	
	protected void removeSlimeFromSchool(Slime slime){
		this.slimes.remove(slime);
	}
	
	protected ArrayList<Slime> getSlimes() {
		return slimes;
	}
}