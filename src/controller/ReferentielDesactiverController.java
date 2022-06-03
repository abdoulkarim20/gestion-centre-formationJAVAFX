package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Promotion;
import model.Referentiel;
import service.PromotionDAO;
import service.ReferentielDAO;
import utils.Utilitaires;

import java.net.URL;
import java.util.ResourceBundle;

public class ReferentielDesactiverController implements Initializable {

    @FXML
    private ComboBox<Promotion> promotionCbx;

    @FXML
    private TextField referentielTfld;

    @FXML
    private TableColumn<Referentiel, String> promotionTbclum;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<Referentiel, String> nomreferentielTbcolum;

    @FXML
    private Button activeRefBtn;

    @FXML
    private TableColumn<Referentiel, Integer> idTbcolum;

    @FXML
    private TableView<Referentiel> referentielTbview;

    @FXML
    private Button deleteBtn;

    @FXML
    void updateHandle(ActionEvent event) throws Exception {
        Referentiel referentiel = new Referentiel();
        if (referentielTfld.getText().trim().equals("")||promotionCbx.getValue().equals("")){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"REFERENCES",
                    "GESTION DES REFERENCES","Tous les champs sont obligatoires");
            return;
        }else {
            referentiel.setId(selectedReferentiel.getId());
            referentiel.setNom(referentielTfld.getText());
            referentiel.setPromotion(promotionCbx.getValue());
            ReferentielDAO referentielDAO = new ReferentielDAO();
            referentielDAO.updateReferentiel(referentiel);
            try {
                Utilitaires.showMessage(Alert.AlertType.INFORMATION,
                        "REFERENCES","GESTION DES REFERENCES",
                        "REFERENCE Modifier avec succees");
                referentiels.add(referentiel);
            }catch (Exception e){
                Utilitaires.showMessage(Alert.AlertType.WARNING,"REFERENCES",
                        "GESTION DES REFERENCES",e.getMessage());
            }
        }
    }

    @FXML
    void deleteHandle(ActionEvent event) {
        Referentiel referentiel = new Referentiel();
        referentiel.setId(selectedReferentiel.getId());
        referentiel.setNom(referentielTfld.getText());
        referentiel.setPromotion(promotionCbx.getValue());
        ReferentielDAO referentielDAO = new ReferentielDAO();
        try {
            referentielDAO.deleteReferentiel(referentiel);
            Utilitaires.showMessage(Alert.AlertType.INFORMATION,"REFERENTIELS",
                    "GESTION DES REFERENTIELS","REFERENTIELS Supprimer avec succes");
            referentiels.add(referentiel);
        } catch (Exception e) {
            Utilitaires.showMessage(Alert.AlertType.ERROR,"REFERENTIELS",
                    "GESTION DES REFERENTIELS","Impossible de supprimer un referentiel appartenant " +
                            "a un groupe d'apprenants! Merci de verifier que le referentiel n'est pas " +
                            "occupée");
        }
    }

    @FXML
    void activeRefHandle(ActionEvent event) throws Exception {
        Referentiel referentiel = new Referentiel();
        referentiel.setId(selectedReferentiel.getId());
        ReferentielDAO referentielDAO = new ReferentielDAO();
        referentielDAO.activeReferentiel(referentiel,1);
        try {
            Utilitaires.showMessage(Alert.AlertType.CONFIRMATION,"REFERENTIELS","GESTION DES REFERENTIELS",
                    "REFERENTIEL activer avec succees");
        }catch (Exception e){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"REFERENTIELS","GESTION DES REFERENTIELS",
                    e.getMessage());
        }
    }
    ObservableList<Referentiel> referentiels;
    ReferentielDAO referentielDAO;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTbcolum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        nomreferentielTbcolum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
        promotionTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getPromotion().getNomPromotion()));
        referentielDAO=new ReferentielDAO();
        try {
            PromotionDAO promotionDAO = new PromotionDAO();
            promotionCbx.setItems(FXCollections.observableArrayList(promotionDAO.findAll()));
            referentiels= FXCollections.observableArrayList(referentielDAO.findByDesactive());
            activeDesactiveBtn(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        referentielTbview.setItems(referentiels);
        referentielTbview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectReferentiel(referentielTbview.getSelectionModel().getSelectedItem());
                activeDesactiveBtn(true);
            }
        });
    }
    private Referentiel selectedReferentiel=null;
    private void selectReferentiel(Referentiel referentiel){
        referentielTfld.setText(referentiel.getNom());
        promotionCbx.setValue(referentiel.getPromotion());
        selectedReferentiel=referentiel;
    }
    private void activeDesactiveBtn(boolean active){
        updateBtn.setDisable(!active);
        deleteBtn.setDisable(!active);
        activeRefBtn.setDisable(active);
    }
}
