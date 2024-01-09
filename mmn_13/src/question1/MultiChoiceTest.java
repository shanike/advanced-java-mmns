package question1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MultiChoiceTest extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = (Parent) FXMLLoader.load(getClass().getResource("MultiChoiceTest.fxml"));
//		FXMLLoader loader = new FXMLLoader(getClass().getResource("MultiChoiceTest.fxml"));
//		Parent root = loader.load();

		Scene scene = new Scene(root);
		stage.setTitle("multi-choice-test");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
		System.out.println();
	}
}
