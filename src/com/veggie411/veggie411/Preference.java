package com.veggie411.veggie411;

public class Preference {
	private long id;
	private String ingredient;
	private boolean edible;
	
	
	public Preference(String ingredient, boolean edible) {
		this.ingredient = ingredient;
		this.edible = edible;
	}
	
	public Preference() {
		this.ingredient = null;
		this.edible = false;
	}

	public boolean setId(long id) {
		this.id = id;
		return true;
	}
	
	public long getId() {
		return id;
	}
	
	public boolean setIngredient(String ingredient) {
		this.ingredient = ingredient;
		return true;
	}
	
	public String getIngredient() {
		return ingredient;
	}
	
	public boolean setEdible(boolean edible) {
		this.edible = edible;
		return true;
	}
	
	public boolean isEdible() {
		return edible;
	}

}
