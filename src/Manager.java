// Classe fille Manager herite de Employe
public class Manager extends Employe implements PerformanceEvaluator{
    private double primeAnnuelle;

    // Constructeur complet :
    public Manager(String id, String nom, double salaireBase, double primeAnnuelle){
        super(id,nom,salaireBase);
        setPrimeAnnuelle(primeAnnuelle);
    }

    // Les setters et les getters :
    public void setPrimeAnnuelle(double primeAnnuelle){
        if(primeAnnuelle >= 0)
            this.primeAnnuelle = primeAnnuelle;
        else
            System.out.println("Le prime annuelle ne peut pas etre negatif!");
    }
    public double getPrimeAnnuelle(){
        return primeAnnuelle;
    }

    // Redefinition de la methode calculerSalaireNet() :
    @Override
    public double calculerSalaireNet(){
        return (getSalaireBase() * 0.75) + getPrimeAnnuelle();
    }

    // Redefinition de la methode abstract evaluerPerformance() de l'interface evaluerPerformance
    @Override
    public int evaluerPerformance(int note) {
        System.out.println("Performance évaluée pour le Manager: " + note);
        return note;
    }

    // Redefinition de toString() pour le Manager :
    @Override
    public String toString() {
        return getClass().getSimpleName()+"    | ID: "
                + getId() + " | Nom: " + getNom() + " | Salaire de base : "+getSalaireBase()
                +" | Prime Annuelle : "+getPrimeAnnuelle()+" | Salaire Net: " + calculerSalaireNet() + " DH";
    }
}
