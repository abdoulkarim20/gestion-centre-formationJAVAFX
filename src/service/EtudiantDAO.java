package service;

import model.Etudiant;
import model.Promotion;
import model.Referentiel;
import utils.DataBaseHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO implements IEtudiant {
    private DataBaseHelper dbc;
    public EtudiantDAO(){
        dbc=new DataBaseHelper();
    }
    @Override
    public void addEtudiant(Etudiant etudiant) throws Exception {
        String sql="INSERT INTO etudiant VALUES(null,?,?,?,?,?,?,?,?,?)";
        try {
            dbc.myPrepardQuery(sql);
            Object[] parm={
                    etudiant.getNom(),
                    etudiant.getPrenoms(),
                    etudiant.getDateNaissance(),
                    etudiant.getEmail(),
                    etudiant.getTelephone(),
                    etudiant.getAdresse(),
                    etudiant.getPhoto(),
                    etudiant.getReferentiel().getId(),
                    etudiant.getPromotion().getId()
            };
            dbc.addParameters(parm);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Etudiant> findAll() throws Exception {
        List<Etudiant> etudiants=new ArrayList<>();
        String sql="SELECT * FROM etudiant e , referentiel r , promotion p WHERE e.referentiel_id=r.id AND e.promotion_id=p.id";
        try {
            dbc.myPrepardQuery(sql);
            ResultSet resultSet=dbc.myExecuteQuery();
            while (resultSet.next()){
                Etudiant etudiant=new Etudiant();
                etudiant.setId(resultSet.getInt(1));
                etudiant.setNom(resultSet.getString(2));
                etudiant.setPrenoms(resultSet.getString(3));
                etudiant.setDateNaissance(resultSet.getDate(4).toLocalDate());
                etudiant.setEmail(resultSet.getString(5));
                etudiant.setTelephone(resultSet.getString(6));
                etudiant.setAdresse(resultSet.getString(7));
                etudiant.setPhoto(resultSet.getString(8));
                Referentiel referentiel = new Referentiel();
                referentiel.setId(resultSet.getInt(9));
                referentiel.setNom(resultSet.getString(12));
                etudiant.setReferentiel(referentiel);
                Promotion promotion = new Promotion();
                promotion.setId(resultSet.getInt(15));
                promotion.setNomPromotion(resultSet.getString(16));
                promotion.setDateOuverture(resultSet.getDate(17).toLocalDate());
                promotion.setDateFermeture(resultSet.getDate(18).toLocalDate());
                etudiant.setPromotion(promotion);
                etudiants.add(etudiant);
            }
            resultSet.close();
        } catch (Exception e) {
            throw e;
        }
        return etudiants;
    }

    @Override
    public Etudiant findEtudiantByEmail(String email) throws Exception {
        String sql="SELECT * FROM etudiant e , referentiel r , promotion p WHERE e.referentiel_id=r.id AND e.promotion_id=p.id AND email=?";
        Etudiant etudiant=null;
        try {
            dbc.myPrepardQuery(sql);
            Object[] parm={email};
            dbc.addParameters(parm);
            ResultSet resultSet=dbc.myExecuteQuery();
            while (resultSet.next()){
                etudiant=new Etudiant();
                etudiant.setId(resultSet.getInt(1));
                etudiant.setNom(resultSet.getString(2));
                etudiant.setPrenoms(resultSet.getString(3));
                etudiant.setDateNaissance(resultSet.getDate(4).toLocalDate());
                etudiant.setEmail(resultSet.getString(5));
                etudiant.setTelephone(resultSet.getString(6));
                etudiant.setAdresse(resultSet.getString(7));
                etudiant.setPhoto(resultSet.getString(8));
                Referentiel referentiel = new Referentiel();
                referentiel.setId(resultSet.getInt(10));
                referentiel.setNom(resultSet.getString(11));
                etudiant.setReferentiel(referentiel);
                Promotion promotion = new Promotion();
                promotion.setId(resultSet.getInt(15));
                promotion.setNomPromotion(resultSet.getString(16));
                promotion.setDateOuverture(resultSet.getDate(17).toLocalDate());
                promotion.setDateFermeture(resultSet.getDate(18).toLocalDate());
                etudiant.setPromotion(promotion);
            }
            resultSet.close();
        } catch (Exception e) {
            throw e;
        }
        return etudiant;
    }

    @Override
    public void updateEtudiant(Etudiant etudiant) throws Exception {
//        String sql="UPDATE etudiant e, referentiel r, promotion p SET e.nom=?, e.prenoms=?, e.dateNaissance=?, e.email=?," +
//                "e.telephone=?, e.adresse, e.photo=?, e.referentiel_id=?,e.promotion_id=? WHERE e.id=?";
        String sql="UPDATE etudiant SET nom=?, prenoms=?, dateNaissance=?, email=?," +
                "telephone=?, adresse=? , photo=?, referentiel_id=?, promotion_id=? WHERE id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={
                    etudiant.getNom(),
                    etudiant.getPrenoms(),
                    etudiant.getDateNaissance(),
                    etudiant.getEmail(),
                    etudiant.getTelephone(),
                    etudiant.getAdresse(),
                    etudiant.getPhoto(),
                    etudiant.getReferentiel().getId(),
                    etudiant.getPromotion().getId(),
                    etudiant.getId()
            };
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteEtudiant(Etudiant etudiant) throws Exception {
        String sql="DELETE FROM etudiant WHERE nom=? AND prenoms=? " +
                "AND dateNaissance=? AND email=? " +
                "AND telephone=? AND adresse=? " +
                "AND photo=? AND referentiel_id=? " +
                "AND promotion_id=? AND id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={
                    etudiant.getNom(),
                    etudiant.getPrenoms(),
                    etudiant.getDateNaissance(),
                    etudiant.getEmail(),
                    etudiant.getTelephone(),
                    etudiant.getAdresse(),
                    etudiant.getPhoto(),
                    etudiant.getReferentiel().getId(),
                    etudiant.getPromotion().getId(),
                    etudiant.getId()
            };
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }

    }
}
