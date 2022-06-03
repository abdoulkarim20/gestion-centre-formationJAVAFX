package utils;
import main.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class Utilitaires {
    public static void showMessage(Alert.AlertType type,String title,String header,String content){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void loadPage(ActionEvent event,String title,String url) throws IOException {
        ((Node) event.getSource()).getScene().getWindow().hide();
        Parent root=FXMLLoader.load(getClass().getResource(url));
        Scene scene=new Scene(root);

        Stage stage=new Stage();
        stage.setScene(scene);
        stage.setTitle(title);

        stage.show();
    }
    public static void load(ActionEvent event,String title, String url) throws IOException {
        new Utilitaires().loadPage(event,title,url);
    }
    public Pane getPage(String fileName){
        Pane view=null;
        try{
            URL fileURL = Main.class.getResource("/view/"+fileName+".fxml");
            if(fileURL==null){
                throw new FileNotFoundException("Page innexistant");
            }
            view = new FXMLLoader().load(fileURL);
        }
        catch (Exception e){
            showMessage(Alert.AlertType.ERROR,"ERROR-PAGE","Chargement de page",e.getMessage());
        }
        return view;
    }
}
