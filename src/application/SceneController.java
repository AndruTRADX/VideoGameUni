package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.stage.Stage;

public class SceneController {
	public Stage stage;
	public Scene scene;
	public Parent parent;
	
	public void switchToMainScene(ActionEvent event)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();	
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void switchToScene1(ActionEvent event) 
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scene1.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void switchToScene2(ActionEvent event)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void switchToScene3(ActionEvent event)
	{
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Scene3.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
