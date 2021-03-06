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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class PromotionController implements Initializable {

    @FXML
    private Button desactivePromoaBtn1;

    @FXML
    private TableColumn<Promotion, LocalDate> dateOuvertureTbclum;

    @FXML
    private Button newPromoBtn;

    @FXML
    private TableColumn<Promotion, String> nomPromoTbclum;

    @FXML
    private TableColumn<Promotion, LocalDate> dateFermetureTbclum;

    @FXML
    private TextField nomPromoTfld;

    @FXML
    private Button deletePromoaBtn;

    @FXML
    private DatePicker dateFermetureDatePiker;

    @FXML
    private TableView<Promotion> promoTview;

    @FXML
    private Button updatePromoBtn;

    @FXML
    private DatePicker dateOuvertureDatePiker;

    @FXML
    private Button savePromoBtn;

    @FXML
    private TableColumn<Promotion, Integer> idTbclum;

    @FXML
    void newHandle(ActionEvent event) {
        clearField();
    }

    @FXML
    void saveHandle(ActionEvent event) throws Exception {
        Promotion promotion=new Promotion();
        if (nomPromoTfld.getText().trim().equals("")||dateOuvertureDatePiker.getValue()==null
                ||dateFermetureDatePiker.getValue()==null){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"PROMOTION",
                    "GESTION DES PROMOTION","Tous les champs sont obligatires");
            return;
        }if (promotionDAO.findPromotionByNomPromotion(nomPromoTfld.getText().trim())!=null){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"PROMOTION",
                    "GESTION DES REFERENCES","Une promotion portant ce nom existe deja");
        }else {
            promotion.setNomPromotion(nomPromoTfld.getText());
            promotion.setDateOuverture(dateOuvertureDatePiker.getValue());
            promotion.setDateFermeture(dateFermetureDatePiker.getValue());
            PromotionDAO promotionDAO = new PromotionDAO();
            promotionDAO.addPromotion(promotion);
            try {
                Utilitaires.showMessage(Alert.AlertType.INFORMATION,"PROMOTION",
                        "GESTION DES PROMOTION","PROMOTION ajouter avec succes");
                promotions.add(promotion);
            }catch (Exception e){
                Utilitaires.showMessage(Alert.AlertType.INFORMATION,"PROMOTION",
                        "GESTION DES PROMOTION",e.getMessage());
            }

        }
    }

    @FXML
    void updateHandle(ActionEvent event) throws Exception {
        if (nomPromoTfld.getText().trim().equals("")||dateOuvertureDatePiker.getValue()==null
                ||dateFermetureDatePiker.getValue()==null){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"PROMOTION",
                    "GESTION DES PROMOTION","Tous les champs sont obligatires");
            return;
        }else {
            Promotion promotion=new Promotion();
            promotion.setId(selectedPromotion.getId());
            promotion.setNomPromotion(nomPromoTfld.getText());
            promotion.setDateOuverture(dateOuvertureDatePiker.getValue());
            promotion.setDateFermeture(dateFermetureDatePiker.getValue());
            PromotionDAO promotionDAO = new PromotionDAO();
            promotionDAO.updatePromotion(promotion);
            try {
                Utilitaires.showMessage(Alert.AlertType.INFORMATION,"PROMOTION",
                        "GESTION DES PROMOTION","PROMOTION Modifier avec succees");
                promotions.add(promotion);
            }catch (Exception e){
                Utilitaires.showMessage(Alert.AlertType.ERROR,"PROMOTION",
                        "GESTION DES PROMOTION",e.getMessage());
            }

        }
    }

    @FXML
    void deleteHandle(ActionEvent event) {
        Promotion promotion=new Promotion();
        promotion.setId(selectedPromotion.getId());
        promotion.setNomPromotion(nomPromoTfld.getText());
        promotion.setDateOuverture(dateOuvertureDatePiker.getValue());
        promotion.setDateFermeture(dateFermetureDatePiker.getValue());
        PromotionDAO promotionDAO = new PromotionDAO();
        try {
            promotionDAO.deletePromotion(promotion);
            Utilitaires.showMessage(Alert.AlertType.INFORMATION,"PROMOTION",
                    "GESTION DES PROMOTION","PROMOTION Supprimer avec succes");
            promotions.add(promotion);
        } catch (Exception e) {
            Utilitaires.showMessage(Alert.AlertType.ERROR,"PROMOTION",
                    "GESTION DES PROMOTION","Impossible de supprimer une promotion appartenant " +
                            "a un groupe d'apprenants ou un referentiel! Merci de verifier que la promotion n'est pas " +
                            "occup??e");
        }
    }

    @FXML
    void desactiveHandle(ActionEvent event) throws Exception {
        Promotion promotion = new Promotion();
        promotion.setId(selectedPromotion.getId());
        PromotionDAO promotionDAO = new PromotionDAO();
        promotionDAO.activePromotion(promotion,0);
        try {
            Utilitaires.showMessage(Alert.AlertType.CONFIRMATION,"PROMOTION","GESTION DES PROMOTION",
                    "PROMOTION desactiver avec succees");
        }catch (Exception e){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"PROMOTION","GESTION DES PROMOTION",
                    e.getMessage());
        }

    }
    ObservableList<Promotion> promotions;
    PromotionDAO promotionDAO;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        nomPromoTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getNomPromotion()));
        dateOuvertureTbclum.setCellValueFactory(cellData-> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateOuverture()));
        dateFermetureTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getDateFermeture()));
        promotionDAO=new PromotionDAO();
        try {
            promotions=FXCollections.observableList(promotionDAO.findAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
        promoTview.setItems(promotions);
        activeDesactiveBtn(false);
        promoTview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                selectPromotion(promoTview.getSelectionModel().getSelectedItem());
                activeDesactiveBtn(true);
            }
        });
    }
    private Promotion selectedPromotion=null;
    private void selectPromotion(Promotion promotion){
        nomPromoTfld.setText(promotion.getNomPromotion());
        dateOuvertureDatePiker.setValue(promotion.getDateOuverture());
        dateFermetureDatePiker.setValue(promotion.getDateFermeture());
        selectedPromotion=promotion;
    }
    private void activeDesactiveBtn(boolean active){
        savePromoBtn.setDisable(active);
        updatePromoBtn.setDisable(!active);
        deletePromoaBtn.setDisable(!active);
        desactivePromoaBtn1.setDisable(!active);
    }
    private void clearField(){
        nomPromoTfld.setText(null);
        dateOuvertureDatePiker.setValue(null);
        dateFermetureDatePiker.setValue(null);
        activeDesactiveBtn(false);

    }
}
