
runBDtests: BackendTests.class
	java -jar ../junit5.jar -cp . -c BackendTests

runFDTests: FrontendTests.java Frontend.java
	javac -cp ../junit5.jar:. FrontendTests.java
	javac Frontend.java

BackendTests.class: BackendTests.java Backend.class IterableMultiKeySortedCollection.class TextUITester.class
	javac -cp .:../junit5.jar BackendTests.java

Backend.class: Backend.java BackendInterface.java IterableMultiKeySortedCollection.class IngredientCalorie.class IngredientName.class
	javac BackendInterface.java Backend.java

IngredientCalorie.class: IngredientCalorie.java IngredientInterface.java
	javac IngredientCalorie.java IngredientInterface.java

IngredientName.class: IngredientName.java IngredientInterface.java
	javac IngredientName.java IngredientInterface.java
	
IterableMultiKeySortedCollection.class: IterableMultiKeySortedCollectionInterface.java SortedCollectionInterface.java IterableMultiKeySortedCollection.java
	javac IterableMultiKeySortedCollection.java IterableMultiKeySortedCollectionInterface.java SortedCollectionInterface.java

TextUITester.class: TextUITester.java
	javac TextUITester.java

clean: 
	rm -f *.class
	rm -f *~