
public interface IngredientInterface extends Comparable<IngredientInterface> {
    //constructor 
    //public class Subsgredient implements SubsgredientBackendInterface<String> 

    public String getCategory();
    public String getIngredient(); 
    public int getCalories();

    public int compareTo(IngredientInterface other) throws IllegalArgumentException;
}
