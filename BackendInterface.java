
import java.io.IOException;
import java.util.List;

public interface BackendInterface {

    //constructor
    // public class IndividualBackend implements IndividualBackendInterface(); 

    //reading data set 
    public boolean readFileData(String filename) throws IOException;         

   //lists 3 substitutions for said ingredient
    public List<IngredientInterface> listReplacements(String replacement);

    //returns number of ingredients
    public int numOfIngredients(); 

    //returns number of categories
    public int numOfCategories(); 

    //adds ingredients to the rbt/data structure thing
    public void addKey(List<IngredientInterface> ingredients); 
}
