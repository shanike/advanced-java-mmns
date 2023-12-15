package question2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RandomShapes extends Application {
	public void start(Stage stage) throws Exception{ 
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("RandomShapes.fxml")); 
		Scene scene = new Scene(root); 
		stage.setTitle("RandomShapes"); 
		stage.setScene(scene); 
		stage.show(); 
	} 
	

	public static void main(String[] args) {
		launch(args);
		System.out.println();
	}
}