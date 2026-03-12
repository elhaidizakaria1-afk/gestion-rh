// Classe abstraite
public abstract class Employe {
    private String id;
    private String nom;
    private double salaireBase;

    // Constructeur complet :
    public Employe(String id, String nom, double salaireBase){
        setId(id);
        setNom(nom);
        setSalaireBase(salaireBase);
    }

    // Les setters et les getters :
    public void setId(String id){
        if(id != null && !id.trim().isEmpty())
            this.id = id;
        else
            System.out.println("Id ne peut pas etre vide!");
    }
    public String getId(){
        return id;
    }

    public void setNom(String nom){
        if(nom != null && !nom.trim().isEmpty())
            this.nom = nom;
        else
            System.out.println("Nom ne peut pas etre vide!");
    }
    public String getNom(){
        return nom;
    }

    public void setSalaireBase(double salaireBase){
        if(salaireBase >= 0)
            this.salaireBase = salaireBase;
        else
            System.out.println("Salaire ne peut pas etre negatif!");
    }

    public double getSalaireBase() {
        return salaireBase;
    }

    // Methode abstraite calculerSalaireNet() :
    public abstract double calculerSalaireNet();

    // Redefinition de toString() pour l'affichage
    @Override
    public String toString() {
        return getClass().getSimpleName() +" | ID: " + id + " | Nom: " + nom + " | Salaire Net: " + calculerSalaireNet() + " DH";
    }
}
