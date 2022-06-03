package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import utils.Utilitaires;

public class MenuController {

    @FXML
    private BorderPane paneBorder;

    @FXML
    void HomeHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("Menu");
    }

    @FXML
    void AjouterUserHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("FormAddUser");
        paneBorder.setCenter(pane);
    }

    @FXML
    void AjouterProfilHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("ProfilForm");
        paneBorder.setCenter(pane);
    }

    @FXML
    void ajouterPromoHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("PromotionForm");
        paneBorder.setCenter(pane);

    }

    @FXML
    void AjouterReferentielHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("ReferentielForm");
        paneBorder.setCenter(pane);

    }

    @FXML
    void ActiverReferentielHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("ReferentielDesactiverForm");
        paneBorder.setCenter(pane);
    }

    @FXML
    void ajouterEtudiantHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("EtudiantForm");
        paneBorder.setCenter(pane);

    }

    @FXML
    void AjouterEtudiantHandle(ActionEvent event) {

    }
    @FXML
    void activerPromoHandle(ActionEvent event) {
        Utilitaires utilitaires = new Utilitaires();
        Pane pane=utilitaires.getPage("PromotionDesactiveForm");
        paneBorder.setCenter(pane);

    }

}
