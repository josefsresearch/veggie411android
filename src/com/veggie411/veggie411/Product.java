package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Product {
	private String barcode;
	private String name;
	private String brand;
	private ArrayList<String> ingredients;
	private byte[] image;
	
	private static final String BARCODE = "BARCODE";
	private static final String NAME = "NAME";
	private static final String INGREDIENTS = "INGREDIENTS";

	
	public Product(String barcode) {
		this.barcode = barcode;
		name = null;
		brand = null;
		ingredients = new ArrayList<String>();
		image = null;
	}
	
	public Product(String barcode, JSONObject product) {
		this(barcode);
		try {
			name = product.getString(NAME);
			JSONArray jsonIngredientsArray = product.getJSONArray(INGREDIENTS);
			for (int index=0; index<jsonIngredientsArray.length();index++) {
				ingredients.add(jsonIngredientsArray.getString(index));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getBarcode() {
		return barcode;
	}
	
	public boolean setName(String name) {
		this.name = name;
		return true;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean setBrand(String brand) {
		this.brand = brand;
		return true;
	}
	
	public String getBrand() {
		return brand;
	}
	
	public boolean addIngredient(String ingredient) {
		ingredients.add(ingredient);
		return true;
	}
	
	public boolean setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
		return true;
	}
	
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	
	public String[] getIngredientsArray() {
		String[] ret = new String[ingredients.size()];
		for (int i=0;i<ret.length;i++) {
			ret[i] = ingredients.get(i);
		}
		return ret;
	}
	
	public boolean setImage(byte[] image) {
		this.image = image;
		return true;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public JSONObject toJSON() {
		JSONObject productObject = new JSONObject();
		JSONArray ingredientsArray = new JSONArray();
		
		for (String ingredient:ingredients) {
			ingredientsArray.put(ingredient);
		}
		
		try{
		productObject.put(BARCODE, barcode);
		productObject.put(NAME, name);
		productObject.put(INGREDIENTS, ingredientsArray);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productObject;
	}
	

}
