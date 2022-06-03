package service;

import model.Promotion;
import model.Referentiel;
import utils.DataBaseHelper;

import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PromotionDAO implements IPromotion {
    private DataBaseHelper dbc;
    public PromotionDAO(){
        dbc = new DataBaseHelper();
    }
    @Override
    public void addPromotion(Promotion promotion) throws Exception {
        String sql="INSERT INTO promotion VALUES(null,?,?,?)";
        try {
            dbc.myPrepardQuery(sql);
            int etat=1;
            Object[] parm={ promotion.getNomPromotion(),
                            promotion.getDateOuverture(),
                            promotion.getDateFermeture(),
                            etat
            };
            dbc.addParameters(parm);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Promotion> findAll() {
        List<Promotion> promotions = new ArrayList<>();
        String sql="SELECT * FROM promotion WHERE etat=1";
        try {
            dbc.myPrepardQuery(sql);
            ResultSet resultat = dbc.myExecuteQuery();
            while (resultat.next()){
                Promotion promotion=new Promotion();
                promotion.setId(resultat.getInt(1));
                promotion.setNomPromotion(resultat.getString(2));
                promotion.setDateOuverture(resultat.getDate(3).toLocalDate());
                promotion.setDateFermeture(resultat.getDate(4).toLocalDate());
                promotions.add(promotion);
            }
            resultat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }

    @Override
    public Promotion findPromotionByNomPromotion(String nomPromotion) throws Exception {
        String sql="SELECT * FROM promotion WHERE nomPromotion=?";
        Promotion promotion=null;
        try {
            dbc.myPrepardQuery(sql);
            Object [] parm={nomPromotion};
            dbc.addParameters(parm);
            ResultSet resultat=dbc.myExecuteQuery();
            while (resultat.next()){
                promotion = new Promotion();
                promotion.setId(resultat.getInt(1));
                promotion.setNomPromotion(resultat.getString(2));
                promotion.setDateOuverture(resultat.getDate(3).toLocalDate());
                promotion.setDateFermeture(resultat.getDate(4).toLocalDate());
            }
            resultat.close();
        } catch (Exception e) {
            throw e;
        }
        return promotion;
    }

    @Override
    public void updatePromotion(Promotion promotion) throws Exception {
        String sql="UPDATE promotion SET nomPromotion=?, dateOuverture=?, dateFermeture=? WHERE id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={
                    promotion.getNomPromotion(),
                    promotion.getDateOuverture(),
                    promotion.getDateFermeture(),
                    promotion.getId()
            };
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deletePromotion(Promotion promotion) throws Exception {
        String sql="DELETE FROM promotion WHERE nomPromotion=? " +
                "AND dateOuverture=? AND dateFermeture=? AND id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={
                    promotion.getNomPromotion(),
                    promotion.getDateOuverture(),
                    promotion.getDateFermeture(),
                    promotion.getId()
            };
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public void activePromotion(Promotion promotion, int etat) throws Exception {
        String sql="UPDATE promotion SET etat=? WHERE id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={etat,promotion.getId()};
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void desactivePromotion(Promotion promotion, int etat) throws Exception {
        String sql="UPDATE promotion SET etat=? WHERE id=?";
        try {
            dbc.myPrepardQuery(sql);
            Object [] param={etat,promotion.getId()};
            dbc.addParameters(param);
            dbc.myExecuteUpdate();
        } catch (Exception e) {
            throw e;
        }

    }

    @Override
    public List<Promotion> findAllByDesactive() {
        List<Promotion> promotions = new ArrayList<>();
        String sql="SELECT * FROM promotion WHERE etat=0";
        try {
            dbc.myPrepardQuery(sql);
            ResultSet resultat = dbc.myExecuteQuery();
            while (resultat.next()){
                Promotion promotion=new Promotion();
                promotion.setId(resultat.getInt(1));
                promotion.setNomPromotion(resultat.getString(2));
                promotion.setDateOuverture(resultat.getDate(3).toLocalDate());
                promotion.setDateFermeture(resultat.getDate(4).toLocalDate());
                promotions.add(promotion);
            }
            resultat.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return promotions;
    }
}
