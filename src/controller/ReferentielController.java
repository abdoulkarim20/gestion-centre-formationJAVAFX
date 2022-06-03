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

public class ReferentielController implements Initializable {

    @FXML
    private TableColumn<Referentiel, String> promotionTbclum;

    @FXML
    private ComboBox<Promotion> promotionCbx;

    @FXML
    private Button desactiveRefBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<Referentiel, String> nomreferentielTbcolum;

    @FXML
    private Button newBtn;

    @FXML
    private TableColumn<Referentiel, Integer> idTbcolum;

    @FXML
    private TextField referentielTfld;

    @FXML
    private TableView<Referentiel> referentielTbview;

    @FXML
    private Button saveBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    void newHandle(ActionEvent event) {
        clearField();
    }

    @FXML
    void saveHandle(ActionEvent event) throws Exception {
        Referentiel referentiel=new Referentiel();
        if (referentielTfld.getText().trim().equals("")||promotionCbx.getValue()==null){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"REFERENCES",
                    "GESTION DES REFERENCES","Tous les champs sont obligatoires");
            return;
        }if (referentielDAO.getReferentielByNom(referentielTfld.getText().trim())!=null){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"REFERENCES",
                    "GESTION DES REFERENCES","Ce REFERENCE existe deja");
        }else {
            referentiel.setNom(referentielTfld.getText());
            referentiel.setPromotion(promotionCbx.getValue());
            referentielDAO.addReferentiel(referentiel);
            try {
                Utilitaires.showMessage(Alert.AlertType.INFORMATION,
                        "REFERENCES","GESTION DES REFERENCES",
                        "REFERENCE ajouter avec succes" );
                referentiels.add(referentiel);
            }catch (Exception e){
                Utilitaires.showMessage(Alert.AlertType.WARNING,"REFERENCES",
                        "GESTION DES REFERENCES",e.getMessage());
            }
        }
    }

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
    void deleteHandle(ActionEvent event) throws Exception {
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
    void desactiveRefHandle(ActionEvent event) throws Exception {
        Referentiel referentiel = new Referentiel();
        referentiel.setId(selectedReferentiel.getId());
        ReferentielDAO referentielDAO = new ReferentielDAO();
        referentielDAO.desactiveReferentiel(referentiel,0);
        try {
            Utilitaires.showMessage(Alert.AlertType.CONFIRMATION,"REFERENTIELS","GESTION DES REFERENTIELS",
                    "REFERENTIEL desactiver avec succees");
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
            referentiels= FXCollections.observableArrayList(referentielDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        referentielTbview.setItems(referentiels);
        activeDesactiveBtn(false);
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
        saveBtn.setDisable(active);
        updateBtn.setDisable(!active);
        deleteBtn.setDisable(!active);
        desactiveRefBtn.setDisable(!active);
    }
    private void clearField(){
        referentielTfld.setText(null);
        promotionCbx.setValue(null);
        activeDesactiveBtn(false);
    }
}
