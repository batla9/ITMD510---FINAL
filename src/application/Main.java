package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.OrderDB;

public class Main extends Application{
	
	public void start(Stage primaryStage) throws Exception {

		OrderDB orderDB = new OrderDB();
        orderDB.createOrderDBTable();
		
		Parent root = FXMLLoader.load(getClass().getResource("/view/HomePage.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Restaruant Delivery Application");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);	
		
	}
}
