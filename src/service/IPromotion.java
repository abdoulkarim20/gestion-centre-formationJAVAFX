package service;

import model.Promotion;
import model.Referentiel;

import java.util.List;

public interface IPromotion {
    public void addPromotion(Promotion promotion) throws Exception;
    public List<Promotion> findAll();
    public Promotion findPromotionByNomPromotion(String nomPromotion) throws Exception;
    public void updatePromotion(Promotion promotion) throws Exception;
    public void deletePromotion(Promotion promotion) throws Exception;
    public void activePromotion(Promotion promotion, int etat) throws Exception;
    public void desactivePromotion(Promotion promotion, int etat) throws Exception;
    public List<Promotion> findAllByDesactive();


}
