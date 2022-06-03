package service;

import model.Referentiel;

import java.util.List;

public interface IReferentiel {
    public void addReferentiel(Referentiel referentiel) throws Exception;
    public List<Referentiel> findAll() throws Exception;
    public void updateReferentiel(Referentiel referentiel) throws Exception;
    public Referentiel getReferentielByNom(String nom) throws Exception;
    public void desactiveReferentiel(Referentiel referentiel,int etat) throws Exception;
    public void deleteReferentiel(Referentiel referentiel) throws Exception;
    public List<Referentiel> findByDesactive() throws Exception;
    public void activeReferentiel(Referentiel referentiel,int etat) throws Exception;
}
