package service;

import model.Etudiant;

import java.util.List;

public interface IEtudiant {
    public void addEtudiant(Etudiant etudiant) throws Exception;
    public List<Etudiant> findAll() throws Exception;
    public Etudiant findEtudiantByEmail(String email) throws Exception;
    public void updateEtudiant(Etudiant etudiant) throws Exception;
    public void deleteEtudiant(Etudiant etudiant) throws Exception;

}
