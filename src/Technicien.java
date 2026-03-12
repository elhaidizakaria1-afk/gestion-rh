// Class fille Technicien herite de Employe
public class Technicien extends Employe implements PerformanceEvaluator{
    private double indemniteRisque;

    // Constructeur complet :
    public Technicien(String id, String nom, double salaireBase, double indemniteRisque){
        super(id,nom,salaireBase);
        setIndemniteRisque(indemniteRisque);
    }

    // Les setters et les getters :
    public void setIndemniteRisque(double indemniteRisque){
        if(indemniteRisque >= 0)
            this.indemniteRisque = indemniteRisque;
        else
            System.out.println("L'indemnite risque ne peut pas etre negatif!");
    }
    public double getIndemniteRisque(){
        return indemniteRisque;
    }

    // Redefinition de la methode calculerSalaireNet() :
    @Override
    public double calculerSalaireNet(){
        return (getSalaireBase() * 0.75) + getIndemniteRisque();
    }

    // Redefinition de la methode abstract evaluerPerformance() de l'interface evaluerPerformance
    @Override
    public int evaluerPerformance(int note) {
            System.out.println("Performance évaluée pour le Technicien: " + note);
        return note;
    }

    // Redefinition de toString() pour le Technicien :
    @Override
    public String toString() {
        return getClass().getSimpleName() +" | ID: " + getId() + " | Nom: " + getNom() +
                " | Salaire de base : "+getSalaireBase() +
                " | Indemnité de risque : "+getIndemniteRisque()+" | Salaire Net: " + calculerSalaireNet() + " DH";
    }
}
