package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.User;
import service.UserDAO;
import utils.Utilitaires;

public class UserController {

    @FXML
    private PasswordField PasswordFLD;

    @FXML
    private TextField UsernameTFLD;

    @FXML
    void ConnexionHandle(ActionEvent event) throws Exception {
        UserDAO userDAO = new UserDAO();
        if (UsernameTFLD.getText().trim().equals("") || PasswordFLD.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authentification");
            alert.setHeaderText("Gestion d'Authentification");
            alert.setContentText("Le Login et le Mot de passe sont obligatoires");
            alert.showAndWait();
            return;
        }
        User user = userDAO.getUser(UsernameTFLD.getText().trim(),PasswordFLD.getText());
        if (user==null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Authentification");
            alert.setHeaderText("Gestion d'Authentification");
            alert.setContentText("Login ou Mot de passe inncorrect");
            alert.showAndWait();
        }if (user!=null && user.getProfil().getId()==2){
                Utilitaires.load(event,"Espace Utilisateur","/view/EspceUser.fxml");
        }else {
            if (user!=null && user.getProfil().getId()==1){
                Utilitaires.load(event,"Espace admin","/view/Menu.fxml");
            }
        }
    }

}
