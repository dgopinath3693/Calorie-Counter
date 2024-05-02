import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Frontend implements FrontendInterface{

    Backend backend;
    Scanner input;
    boolean repeat  = true;
    public Frontend(Backend o, Scanner input){
	backend = o;
	this.input = input;
    }

    public void run(){
	repeat = true;
	this.commandSelect();
    }

    public void commandSelect(){
	System.out.println("Enter command you wish:\n(1)LoadFile\n(2)List Replacements of ingredient\n(3)List Ingredients and Categories\n(4)Exit");
	int i =  input.nextInt();
	if(i == 1){
	    System.out.println("Enter File name: ");
	    String s = input.next();
	    boolean r = this.loadFile(s);
	    if(r){
		System.out.println("Successfully opened file");
	    }
	    else{
		System.out.println("Failed to open file");
	    }
	}
	else if(i == 2){
	    System.out.println("Enter ingredient you wish to replace: ");
	    String s = input.next();
	    List<IngredientInterface> replacements  = this.ingredientReplacement(s);
	    System.out.println("Ingredient replacements: " + replacements);
	}
	else if(i == 3){
	    int[] count = this.listIngredientsAndCategories();
	    System.out.println("Ingredient count: " + count[0] + "\nCategory count: " + count[1]); 
	}
	else if(i == 4){
	    this.exit();
		System.out.println("Thanks for using Subsgredient!");
	}
	else{
	    System.out.println("-- Invalid input --");
	}
	if(repeat){
	    this.run();
	}
	
    }

    public boolean loadFile(String filename) {
		try {
			return backend.readFileData(filename);
		} catch(IOException e) {
			return false;
		}
	}

    public List<IngredientInterface> ingredientReplacement(String ingredient){
	return backend.listReplacements(ingredient);
    }

    public int[] listIngredientsAndCategories(){
	int[] l = new int[2];
	l[0] = backend.numOfIngredients();
	l[1] = backend.numOfCategories();
	return l;
    }

    public void exit(){
	repeat = false;
    }


}
