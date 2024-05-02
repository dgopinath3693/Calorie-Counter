import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public interface FrontendInterface {
    
    /**
       * Constructor accepting references to the backend and a Scanner instance
       * 
       * @param o - Reference to the backend implementing the app's functionality
       * @param input - Scanner instance for reading user input
       */
    //IndividualFrontendInterface(Object o, Scanner input);
    
    /**
       * Method to start the main command loop.
       */
    public void run();
    
    /** 
       * Method displays the list of commands with a number assigned beside each one.
    */
    public void commandSelect();

    /**
       * Method to specify and load a data file.
       * 
       * @param fileName - The file path to the file to load
       * @return true if the file is loaded successfully.
       * @throws FileNotFoundException if the file path entered is incorrect.
       */
    public boolean loadFile(String fileName) throws FileNotFoundException;
    
    /**
       * Method to list three ingredient replacements.
       * 
       * @param ingredient - The ingredient that needs to be replaced.
       * @return A list of three replacements.
       */
    public List<IngredientInterface> ingredientReplacement(String ingredient);
    
    /**
       * Method to list the count of ingredients and categories.
       * 
       * @return the integer count of the ingredients and categories.
       */
    public int[] listIngredientsAndCategories();
    
    /**
       * Method to exit the main command loop.
       */
    public void exit();
}
