import java.util.ArrayList;

public class FeBackendPlaceholder implements BackendInterface{

    public FeBackendPlaceholder(){
    }

    public boolean readFileData(String fileName){
	if(fileName.equals("fail")){
	    return false;
	}
	return true;
    }
    
    public String[] listReplacements(String ingredient){
	if(ingredient.equals("milk")){
	    String[] correct = {"almond milk", "cashew milk"};
	    return correct;
	}
	return null;
    }

    public int numOfIngredients(){
	return 0;
    }

    public int numOfCategories(){
	return 0;
    }
    
}
