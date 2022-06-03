package controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import main.Main;
import model.Etudiant;
import model.Promotion;
import model.Referentiel;
import service.EtudiantDAO;
import service.PromotionDAO;
import service.ReferentielDAO;
import utils.Utilitaires;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.ResourceBundle;

public class EtudiantController implements Initializable {

    @FXML
    private Button updateBtn;

    @FXML
    private TableColumn<Etudiant, String> prenomsTbclum;

    @FXML
    private TextField emailTfld;

    @FXML
    private TextField prenomsTfld;

    @FXML
    private TextField photoTfld;

    @FXML
    private TableColumn<Etudiant, LocalDate> dateNaissanceTbclum;

    @FXML
    private TableColumn<Etudiant, String> promoTbclum;

    @FXML
    private TableColumn<Etudiant, ImageView> photoTbclum;

    @FXML
    private TableColumn<Etudiant, String> referentielTbclum;

    @FXML
    private TextField telephoneTfld;

    @FXML
    private ComboBox<Referentiel> referentielCbx;

    @FXML
    private TableColumn<Etudiant, Integer> idTbclum;

    @FXML
    private DatePicker dateNaissnceDpker;

    @FXML
    private TableColumn<Etudiant, String> telephneTbclum;

    @FXML
    private Button newBtn;

    @FXML
    private TableColumn<Etudiant, String> nomTbclum;

    @FXML
    private TextField nomTfld;

    @FXML
    private ComboBox<Promotion> promotionCbx;

    @FXML
    private ImageView photoImview;

    @FXML
    private Button deleteBtn;

    @FXML
    private TextField adresseTfld;

    @FXML
    private TableView<Etudiant> etudiantTbview;

    @FXML
    private TableColumn<Etudiant, String> adresseTbclum;

    @FXML
    private Button saveBtn;

    @FXML
    private TableColumn<Etudiant, String> emailTbclum;
    //debut de la partie qui me permet d'ajouter une image
    File file;
    BufferedImage bim;
    String cheminImage="D:\\MesProjetJava\\DossiersImageFx\\";
    @FXML
    void parcourirHandle(ActionEvent event) throws IOException {
        FileChooser fileChooser = new  FileChooser();
        FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("Image files",".*jpeg","*.jpg","*.png","*.pdf");
        fileChooser.getExtensionFilters().add(ext);
        file=fileChooser.showOpenDialog(Main.stage);
        if (file!=null){
            bim= ImageIO.read(file);
            Image image= SwingFXUtils.toFXImage(bim,null);
            photoImview.setImage(image);
            photoTfld.setText(file.getName());
        }
        //Fin de la partie qui me permet d'ajouter une image.
    }

    @FXML
    void newHandle(ActionEvent event) throws IOException {
        clearField();
    }

    @FXML
    void saveHandle(ActionEvent event) throws Exception {
        Etudiant etudiant=new Etudiant();
        if (nomTfld.getText().trim().equals("")||prenomsTfld.getText().trim().equals("")||dateNaissnceDpker.getValue()==null
                ||emailTfld.getText().trim().equals("")||telephoneTfld.getText().trim().equals("")
                ||adresseTfld.getText().trim().equals("")||referentielCbx.getValue().equals("")
                ||promotionCbx.getValue().equals("")){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"ETUDIANTS",
                    "GESTION DES ETUDIANTS","Tous les champs sont obligatoire");
            return;
        }else if (etudiantDAO.findEtudiantByEmail(emailTfld.getText().trim())!=null){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"ETUDIANTS",
                    "GESTION DES ETUDIANTS","Un etudiant avec ce mail existe deja");
        } else{
            //Conversion da la date de naissance entier comme age
            Long age;
            LocalDate DateNaissance;
            DateNaissance=LocalDate.parse(String.valueOf(dateNaissnceDpker.getValue()));
            Date date=new Date();
            LocalDate localDate=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            age= ChronoUnit.YEARS.between(DateNaissance,localDate);
            //Fin Conversion
            if(age<17 || age>35) {
                Utilitaires.showMessage(Alert.AlertType.WARNING, "ETUDIANTS",
                        "GESTION DES ETUDIANTS", "Votre age doit etre compris entre 18 et 34");
            } else {
                EtudiantDAO etudiantDAO=new EtudiantDAO();
                etudiant.setNom(nomTfld.getText());
                etudiant.setPrenoms(prenomsTfld.getText());
                etudiant.setDateNaissance(dateNaissnceDpker.getValue());
                etudiant.setEmail(emailTfld.getText());
                etudiant.setTelephone(telephoneTfld.getText());
                etudiant.setAdresse(adresseTfld.getText());
                if (photoTfld.getText().equals("")){
                    file=new File(cheminImage+"default.png");
                    try {
                        bim=ImageIO.read(file);
//                Image image=SwingFXUtils.toFXImage(bim,null);
//                photoImview.setImage(image);
                        photoTfld.setText(file.getName());
                    }catch (Exception e){
                        throw e;
                    }
                }
                etudiant.setPhoto(photoTfld.getText());
                etudiant.setReferentiel(referentielCbx.getValue());
                etudiant.setPromotion(promotionCbx.getValue());
                etudiantDAO.addEtudiant(etudiant);
                try {
                    //ces deux lignes de codes permettent de deplacer l'image vers le chemin qu'on a crer pour stocker les image
                    File out = new File(cheminImage+file.getName());
                    ImageIO.write(bim,"png",out);
                    //ici prend fin ces deux lignes
                    Utilitaires.showMessage(Alert.AlertType.INFORMATION,"ETUDIANTS",
                            "GESTION DES ETUDIANTS","Etudiant ajouté avec succees");
                    etudiants.add(etudiant);
                }catch (Exception e){
                    Utilitaires.showMessage(Alert.AlertType.WARNING,"ETUDIANTS",
                            "GESTION DES ETUDIANTS",e.getMessage());
                }
            }
        }
    }

    @FXML
    void updateHandle(ActionEvent event) throws Exception {
        Etudiant etudiant=new Etudiant();
        if (nomTfld.getText().trim().equals("")||prenomsTfld.getText().trim().equals("")||dateNaissnceDpker.getValue()==null
                ||emailTfld.getText().trim().equals("")||telephoneTfld.getText().trim().equals("")
                ||adresseTfld.getText().trim().equals("")||referentielCbx.getValue().equals("")
                ||promotionCbx.getValue().equals("")){
            Utilitaires.showMessage(Alert.AlertType.WARNING,"ETUDIANTS",
                    "GESTION DES ETUDIANTS","Tous les champs sont obligatoire");
            return;
        }else{
            //Conversion da la date de naissance entier comme age
            Long age;
            LocalDate DateNaissance;
            DateNaissance=LocalDate.parse(String.valueOf(dateNaissnceDpker.getValue()));
            Date date=new Date();
            LocalDate localDate=date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            age= ChronoUnit.YEARS.between(DateNaissance,localDate);
            //Fin Conversion
            if(age<17 || age>35) {
                Utilitaires.showMessage(Alert.AlertType.WARNING, "ETUDIANTS",
                        "GESTION DES ETUDIANTS", "Votre age doit etre compris entre 18 et 34");
            }
             else {
                EtudiantDAO etudiantDAO=new EtudiantDAO();
                etudiant.setId(selectedEtudiant.getId());
                etudiant.setNom(nomTfld.getText());
                etudiant.setPrenoms(prenomsTfld.getText());
                etudiant.setDateNaissance(dateNaissnceDpker.getValue());
                etudiant.setEmail(emailTfld.getText());
                etudiant.setTelephone(telephoneTfld.getText());
                etudiant.setAdresse(adresseTfld.getText());
                if (photoTfld.getText().equals("")){
                    file=new File(cheminImage+"default.png");
                    try {
                        bim=ImageIO.read(file);
//                Image image=SwingFXUtils.toFXImage(bim,null);
//                photoImview.setImage(image);
                        photoTfld.setText(file.getName());
                    }catch (Exception e){
                        throw e;
                    }
                }
                etudiant.setPhoto(photoTfld.getText());
                etudiant.setReferentiel(referentielCbx.getValue());
                etudiant.setPromotion(promotionCbx.getValue());
                etudiantDAO.updateEtudiant(etudiant);
                try {
                    //ces deux lignes de codes permettent de deplacer l'image vers le chemin qu'on a crer pour stocker les image
                    File out = new File(cheminImage+file.getName());
                    ImageIO.write(bim,"png",out);
                    //ici prend fin ces deux lignes
                    Utilitaires.showMessage(Alert.AlertType.INFORMATION,"ETUDIANTS",
                            "GESTION DES ETUDIANTS","Etudiant modifier avec succes");
                    etudiants.add(etudiant);
                } catch (Exception e) {
                    Utilitaires.showMessage(Alert.AlertType.ERROR,"ETUDIANTS",
                            "GESTION DES ETUDIANTS",e.getMessage());
                }
            }

        }
    }

    @FXML
    void deleteHandle(ActionEvent event) {
        Etudiant etudiant = new Etudiant();
        etudiant.setId(selectedEtudiant.getId());
        etudiant.setNom(nomTfld.getText());
        etudiant.setPrenoms(prenomsTfld.getText());
        etudiant.setDateNaissance(dateNaissnceDpker.getValue());
        etudiant.setEmail(emailTfld.getText());
        etudiant.setTelephone(telephoneTfld.getText());
        etudiant.setAdresse(adresseTfld.getText());
        etudiant.setPhoto(photoTfld.getText());
        etudiant.setReferentiel(referentielCbx.getValue());
        etudiant.setPromotion(promotionCbx.getValue());
        EtudiantDAO etudiantDAO = new EtudiantDAO();
        try {
            etudiantDAO.deleteEtudiant(etudiant);
            Utilitaires.showMessage(Alert.AlertType.INFORMATION,"ETUDIANTS",
                    "GESTION DES ETUDIANTS","Etudiant supprimer avec succes");
            etudiants.add(etudiant);
        } catch (Exception e) {
            Utilitaires.showMessage(Alert.AlertType.ERROR,"ETUDIANTS",
                    "GESTION DES ETUDIANTS",e.getMessage());
        }

    }
    ObservableList<Etudiant> etudiants;
    EtudiantDAO etudiantDAO;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ReferentielDAO referentielDAO=new ReferentielDAO();
        PromotionDAO promotionDAO=new PromotionDAO();
        idTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        nomTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
        prenomsTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getPrenoms()));
        dateNaissanceTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getDateNaissance()));
        emailTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));
        telephneTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getTelephone()));
        adresseTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getAdresse()));
        photoTbclum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Etudiant, ImageView>,
                ObservableValue<ImageView>>() {
            @Override
            public ObservableValue<ImageView> call(TableColumn.CellDataFeatures<Etudiant, ImageView> eiv) {
                File f = new File(cheminImage+eiv.getValue().getPhoto());
                if (eiv.getValue().getPhoto().equals("")){
                    f = new File(cheminImage+"default.png");
                }
                ImageView imgV = null;
                try {
                    BufferedImage bimg = ImageIO.read(f);
                    Image image = SwingFXUtils.toFXImage(bimg, null);
                    imgV=new ImageView(image);
                    imgV.setFitHeight(50);
                    imgV.setFitWidth(50);
                }catch (Exception e){
                    e.printStackTrace();
                }
                return new SimpleObjectProperty<>(imgV);
            }
        });
        referentielTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getReferentiel().getNom()));
        promoTbclum.setCellValueFactory(cellData->new ReadOnlyObjectWrapper<>(cellData.getValue().getPromotion().getNomPromotion()));
        etudiantDAO=new EtudiantDAO();
        try {
            etudiants = FXCollections.observableArrayList(etudiantDAO.findAll());
            referentielCbx.setItems(FXCollections.observableList(referentielDAO.findAll()));
            promotionCbx.setItems(FXCollections.observableList(promotionDAO.findAll()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        etudiantTbview.setItems(etudiants);
        activeDesactiveBtn(false);
        etudiantTbview.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {
                    selectEtudaint(etudiantTbview.getSelectionModel().getSelectedItem());
                    activeDesactiveBtn(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private Etudiant selectedEtudiant=null;
    private void selectEtudaint(Etudiant etudiant) throws IOException {
        nomTfld.setText(etudiant.getNom());
        prenomsTfld.setText(etudiant.getPrenoms());
        dateNaissnceDpker.setValue(etudiant.getDateNaissance());
        emailTfld.setText(etudiant.getEmail());
        telephoneTfld.setText(etudiant.getTelephone());
        adresseTfld.setText(etudiant.getAdresse());
        if (etudiant.getPhoto().equals("")){
            file=new File(cheminImage+"default.png");
        }else {
            file=new File(cheminImage+etudiant.getPhoto());
        }
        try {
            bim=ImageIO.read(file);
            Image image=SwingFXUtils.toFXImage(bim,null);
            photoImview.setImage(image);
            photoTfld.setText(file.getName());
        }catch (Exception e){
            throw e;
        }
        referentielCbx.setValue(etudiant.getReferentiel());
        promotionCbx.setValue(etudiant.getPromotion());
        selectedEtudiant=etudiant;
//        activeDesactiveBtn(true);
    }
    private void activeDesactiveBtn(boolean active){
        saveBtn.setDisable(active);
        updateBtn.setDisable(!active);
        deleteBtn.setDisable(!active);
    }
    private void clearField() throws IOException {
        nomTfld.setText(null);
        prenomsTfld.setText(null);
        dateNaissnceDpker.setValue(null);
        emailTfld.setText(null);
        telephoneTfld.setText(null);
        adresseTfld.setText(null);
        if (photoTfld.getText().equals("")){
            file=new File(cheminImage+"default.png");
            try {
                bim=ImageIO.read(file);
                Image image=SwingFXUtils.toFXImage(bim,null);
                photoImview.setImage(null);
                photoTfld.setText(null);
            }catch (Exception e){
                throw e;
            }
        }else {
            if (photoTfld.getText()!=null){
                try {
                    bim=ImageIO.read(file);
                    Image image=SwingFXUtils.toFXImage(bim,null);
                    photoImview.setImage(null);
                    photoTfld.setText(null);
                }catch (Exception e){
                    throw e;
                }
            }
        }
        photoTfld.setText(null);
        referentielCbx.setValue(null);
        promotionCbx.setValue(null);
        activeDesactiveBtn(false);
    }
}
