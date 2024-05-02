public class IngredientName<T extends Comparable<T>> implements IngredientInterface,Comparable<IngredientInterface> {

	//refers to ingredient name
    public String ingredient;
	//refers to ingredient calorie number
    public int calories;
    //refers to ingredient category
    public String category;
	
	//constructor initializing variables
    public IngredientName (String ingredient, int calories, String category) {
		this.ingredient = ingredient;
		this.calories = calories;	
        this.category = category;
	}
    
    @Override
    public String getCategory() {
        return category; 
    }

    @Override
    public String getIngredient() {
        return ingredient;
    }

    @Override
    public int getCalories() {
        return calories;
    }

    //compares ingredients based on their names
    @Override
    public int compareTo(IngredientInterface otherIngredient) throws IllegalArgumentException {
        //check if the other ingredient is an instance of ingredient 
        if (otherIngredient instanceof IngredientName) {
            // Compare based on the names
            return getIngredient().compareTo(((IngredientName<T>) otherIngredient).getIngredient());
        } else {
            throw new IllegalArgumentException("Cannot compare with a different type");
        }
    }

    
}
