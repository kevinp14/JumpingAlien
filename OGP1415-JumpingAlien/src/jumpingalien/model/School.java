package jumpingalien.model;

import java.util.ArrayList;

public class School {
	private ArrayList<Slime> slimes = new ArrayList<>();
	
	public School(){
		this.slimes = null;
	}
	
	public void addSlimeToSchool(Slime slime){
		this.slimes.add(slime);
	}
	
	public void removeSlimeFromSchool(Slime slime){
		this.slimes.remove(slime);
	}
}