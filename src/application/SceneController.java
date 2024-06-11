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
	
	/**
     * Cambia al menú principal del juego.
     * @param event El evento de acción que desencadena este método.
     */
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
	
	/**
     * Cambia al la escena del juego MemoryGame.
     * @param event El evento de acción que desencadena este método.
     */
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
	
	/**
     * Cambia al la escena del juego FindThePairs.
     * @param event El evento de acción que desencadena este método.
     */
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
	
	/**
     * Cambia al la escena del juego MathGame.
     * @param event El evento de acción que desencadena este método.
     */
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
