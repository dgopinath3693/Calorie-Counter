import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Backend implements BackendInterface{

    public List<String> categories;
	public IterableMultiKeySortedCollectionInterface<IngredientInterface> dataStructureName;
	public IterableMultiKeySortedCollectionInterface<IngredientInterface> dataStructureCalories;
	public List<IngredientInterface> ingredientsList;

    public Backend (IterableMultiKeySortedCollectionInterface<IngredientInterface> dataStructureName, IterableMultiKeySortedCollectionInterface<IngredientInterface> dataStructureCalories){
		this.dataStructureName = dataStructureName;
		this.dataStructureCalories = dataStructureCalories;
		ingredientsList = new ArrayList<>();
		categories = new ArrayList<>();
	}


    @Override
public boolean readFileData(String filename) throws IOException {
    List<IngredientInterface> ingredients = new ArrayList<>();
    Path filePath = Paths.get(filename);

    try (BufferedReader reader = Files.newBufferedReader(filePath)) {
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");

            // Check if the array has at least 4 elements and the fourth element can be parsed as an integer
            if (parts.length >= 4) {
                try {
                    int calories = Integer.parseInt(parts[3].split(" ")[0]);
                    ingredients.add(new IngredientName(parts[0], calories, parts[1]));
                } catch (NumberFormatException e) {
                    System.err.println("Skipping invalid line: " + line);
                }
            } else {
                System.err.println("Skipping line with insufficient data: " + line);
            }
        }
    } catch (IOException e) {
        throw new IOException("Error");
    }

    ingredientsList.addAll(ingredients);

    return true;
}


    @Override
    public List<IngredientInterface> listReplacements(String replacement) {
        Comparable<IngredientInterface> startPoint = null;

    // Find the IngredientInterface that matches the replacement
        for (IngredientInterface ingredient : ingredientsList) {
             if (ingredient.getIngredient().equals(replacement)) {
                startPoint = ingredient;
             break;
        }
    }

    // Set the iteration start point based on calories
        dataStructureCalories.setIterationStartPoint(startPoint);

        Iterator<IngredientInterface> iterator = dataStructureCalories.iterator();
        List<IngredientInterface> replacements = new ArrayList<>();

    // Iterate and add ingredients based on their calories
        for (int i = 0; i < 3 && iterator.hasNext(); i++) {
            IngredientInterface temp = iterator.next();
            replacements.add(temp);
     }

    return replacements;
}

	
        public void addKey(List<IngredientInterface> ingredients){
		for(int i = 0; i<ingredients.size(); i++){
			dataStructureName.insertSingleKey(ingredients.get(i));
			dataStructureCalories.insertSingleKey(ingredients.get(i));
			
		}
	}

    @Override
    public int numOfIngredients() {
        return ingredientsList.size(); 
    }

    @Override
    public int numOfCategories() {
        categories.clear(); // Clear the categories list before counting
    
        for (IngredientInterface ingredient : ingredientsList) {
            String category = ingredient.getCategory().trim(); // Trim to remove leading/trailing whitespaces
    
            if (!categories.contains(category)) {
                categories.add(category);
            }
        }
        return categories.size();
    }
    

    public static void main (String[] args) {
        IterableMultiKeySortedCollectionInterface<IngredientInterface> dataStructureName = new IterableMultiKeySortedCollection<>();
        IterableMultiKeySortedCollectionInterface<IngredientInterface> dataStructureCalories = new IterableMultiKeySortedCollection<>();
        Scanner input = new Scanner(System.in); 
        Backend backend = new Backend(dataStructureName, dataStructureCalories);
        Frontend frontend = new Frontend(backend, input);
        frontend.run(); 
    }
}
