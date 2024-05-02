public class IngredientCalorie<T extends Comparable<T>> implements IngredientInterface,Comparable<IngredientInterface> {

	//refers to ingredient name
    public String ingredient;
	//refers to ingredient calorie number
    public int calories;
    //refers to ingredient category
    public String category;
	
	//constructor initializing variables
    public IngredientCalorie (String ingredient, int calories, String category) {
		this.ingredient = ingredient;
		this.calories = calories;	
        this.category = category;
	}
    
    @Override
    public String getCategory() {
        return this.category; 
    }

    @Override
    public String getIngredient() {
        return this.ingredient;
    }

    @Override
    public int getCalories() {
        return this.calories;
    }

    //compares ingredients based on their calories
    @Override
    public int compareTo(IngredientInterface otherCalories) throws IllegalArgumentException {
        if (otherCalories instanceof IngredientCalorie) {
            // Compare based on the calories
            //result negative if current ingredient's cals are less than other ingredient's cals
            //result positive if current ingredient's cals are more than other ingredient's cals
            //result 0 if current ingredient's cals are same to other ingredient's cals
            return this.getCalories() - ((IngredientCalorie<T>) otherCalories).getCalories();
        } else {
            throw new IllegalArgumentException("Cannot compare with a different type");
        }
    }
    
}
