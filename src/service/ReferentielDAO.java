package service;

import model.Promotion;
import model.Referentiel;
import utils.DataBaseHelper;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReferentielDAO implements IReferentiel {
    private DataBaseHelper dbc;
    public ReferentielDAO(){
        dbc=new DataBaseHelper();
    }

    @Override
    public void addReferentiel(Referentiel referentiel) throws Exception {
        String sql="INSERT INTO referentiel  VALUES(null,?,?,?)";
        try {
            int etat=1;
            dbc.myPrepardQuery(sql);
            Object[] param={referentiel.getNom(),
                    etat,
                    referentiel.getPromotion().getId()
            };
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        }catch (Exception e){
            throw e;
        }
    }
    @Override
    public List<Referentiel> findAll() throws Exception {
        List<Referentiel> referentiels = new ArrayList<>();
        String sql="SELECT * FROM referentiel r, promotion p  WHERE r.etat=1 AND r.promotion_id=p.id";
        try {
            dbc.myPrepardQuery(sql);
            ResultSet resultSet=dbc.myExecuteQuery();
            while (resultSet.next()){
                Referentiel referentiel = new Referentiel();
                referentiel.setId(resultSet.getInt(1));
                referentiel.setNom(resultSet.getString(2));
                Promotion promotion=new Promotion();
                promotion.setId(resultSet.getInt(5));
                promotion.setNomPromotion(resultSet.getString(6));
                promotion.setDateOuverture(resultSet.getDate(7).toLocalDate());
                promotion.setDateFermeture(resultSet.getDate(8).toLocalDate());
                referentiel.setPromotion(promotion);
                referentiels.add(referentiel);
            }
            resultSet.close();
        }catch (Exception e){
            throw e;
        }
        return referentiels;
    }

    @Override
    public void updateReferentiel(Referentiel referentiel) throws Exception {
        String sql="UPDATE referentiel SET nom=? , promotion_id=? WHERE id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={
                    referentiel.getNom(),
                    referentiel.getPromotion().getId(),
                    referentiel.getId()
            };
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Referentiel getReferentielByNom(String nom) throws Exception {
        String sql="SELECT * FROM referentiel WHERE nom=?";
        Referentiel referentiel=null;
        try {
            dbc.myPrepardQuery(sql);
            Object [] parm={nom};
            dbc.addParameters(parm);
            ResultSet resultat=dbc.myExecuteQuery();
            while (resultat.next()){
                referentiel = new Referentiel();
                referentiel.setId(resultat.getInt(1));
                referentiel.setNom(resultat.getString(2));
                Promotion promotion=new Promotion();
                promotion.setId(resultat.getInt(4));
                promotion.setNomPromotion(resultat.getString(5));
                promotion.setDateOuverture(resultat.getDate(6).toLocalDate());
                promotion.setDateFermeture(resultat.getDate(7).toLocalDate());
            }
            resultat.close();
        } catch (Exception e) {
            throw e;
        }
        return referentiel;
    }

    @Override
    public void desactiveReferentiel(Referentiel referentiel, int etat) throws Exception {
        String sql="UPDATE Referentiel SET etat=? WHERE id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={etat,referentiel.getId()};
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void deleteReferentiel(Referentiel referentiel) throws Exception {
        String sql="DELETE FROM referentiel WHERE nom=? " +
                "AND promotion_id=? AND id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={
                    referentiel.getNom(),
                    referentiel.getPromotion().getId(),
                    referentiel.getId(),
            };
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public List<Referentiel> findByDesactive() throws Exception {
        List<Referentiel> referentiels = new ArrayList<>();
        String sql="SELECT * FROM referentiel r, promotion p  WHERE r.etat=0 AND r.promotion_id=p.id";
        try {
            dbc.myPrepardQuery(sql);
            ResultSet resultSet=dbc.myExecuteQuery();
            while (resultSet.next()){
                Referentiel referentiel = new Referentiel();
                referentiel.setId(resultSet.getInt(1));
                referentiel.setNom(resultSet.getString(2));
                Promotion promotion=new Promotion();
                promotion.setId(resultSet.getInt(5));
                promotion.setNomPromotion(resultSet.getString(6));
                promotion.setDateOuverture(resultSet.getDate(7).toLocalDate());
                promotion.setDateFermeture(resultSet.getDate(8).toLocalDate());
                referentiel.setPromotion(promotion);
                referentiels.add(referentiel);
            }
            resultSet.close();
        }catch (Exception e){
            throw e;
        }
        return referentiels;
    }

    @Override
    public void activeReferentiel(Referentiel referentiel, int etat) throws Exception {
        String sql="UPDATE Referentiel SET etat=? WHERE id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={etat,referentiel.getId()};
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }
    }
}
