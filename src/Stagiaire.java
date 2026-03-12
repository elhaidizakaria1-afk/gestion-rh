// Class fille Stagiaire herite de Employe
public class Stagiaire extends Employe {
    // Le stagiaire a un montant fixe (forfait), on utilise salaireBase comme montant du forfait
    // Constructeur complet :
    public Stagiaire(String id, String nom, double montantForfait) {
        super(id, nom, montantForfait);
    }

    // Redefinition de la methode calculerSalaireNet() :
    @Override
    public double calculerSalaireNet(){
        return getSalaireBase();
    }

    // Redefinition de toString() pour le Manager :
    @Override
    public String toString() {
        return getClass().getSimpleName() +"  | ID: " + getId() + " | Nom: " + getNom() +
                " | Forfait Fixe : "+getSalaireBase() +" | Salaire Net: " + calculerSalaireNet() + " DH";
    }
}
