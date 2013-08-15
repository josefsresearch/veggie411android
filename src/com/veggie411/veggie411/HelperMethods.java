package com.veggie411.veggie411;

import java.util.ArrayList;
import java.util.HashMap;

public class HelperMethods {

	public static ArrayList<Ingredient> getAllIngredients() {
		
//		name = "";
//		ename = "";
//		references = "";
//		status = 0;
//		description = "";
//		temp = new Ingredient(name, ename, references, status, description);
//		ret.add(temp);
		
		Ingredient temp;
		String name = "";
		String ename = "";
		String references = "";
		int status = 0;
		String description = "";
		ArrayList ret = new ArrayList();
		
		name = "Curcumin";
		ename = "E100";
		references = "";
		status = 0;
		description = "An orange yellow colour derived from the root of the curcuma (turmeric) plant. " +
				"High doses should not be taken by people with gallstones, obstructive jaundice, acute bilious colic or toxic liver disorders.";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "Riboflavin";
		ename = "E101";
		references = "";
		status = 0;
		description = "Also known as vitamin B2. It has a key role in maintaining health in humans and animals.";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "lactoflavin";
		ename = "E101";
		references = "Riboflavin";
		status = 0;
		description = "";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "Vitamin B2";
		ename = "E101";
		references = "Riboflavin";
		status = 0;
		description = "";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "Riboflavin-5\'-Phosphate";
		ename = "E101a";
		references = "";
		status = 0;
		description = "A more soluble form of Riboflavin (B2), sometimes used as an orange / red food color. " +
				"Although naturally present in many foods such as milk, eggs, liver and vegetables, it is commercially prepared " +
				"from yeasts and also manufactured synthetically.";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "Flavin mononucleotide";
		ename = "E101a";
		references = "Riboflavin-5\'-Phosphate";
		status = 0;
		description = "";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "FMN";
		ename = "E101a";
		references = "Riboflavin-5\'-Phosphate";
		status = 0;
		description = "";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);		  			
		
		name = "A";
		ename = "";
		references = "";
		status = 0;
		description = "status=0";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "B";
		ename = "";
		references = "";
		status = 1;
		description = "status=1";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "C";
		ename = "";
		references = "";
		status = 2;
		description = "status=2";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "D";
		ename = "";
		references = "";
		status = 3;
		description = "status=3";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		name = "E";
		ename = "";
		references = "";
		status = 4;
		description = "status=4";
		temp = new Ingredient(name, ename, references, status, description);
		ret.add(temp);
		
		return ret;
	}
	
}
