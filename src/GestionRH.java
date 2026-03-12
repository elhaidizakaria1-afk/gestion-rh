import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// Class contient les tableaux dynamiques 'ArrayList', 'HashMap'
public class GestionRH {
    // Creation de ArrayList et Map :
    private ArrayList<Employe> listeEmploye; // Pour stocker les employees
    private Map<Employe, String> affectationProjets; // pour affecter les projet a les employes
    Scanner  sc = new Scanner(System.in);

    // Constructeur complet pour l'initialisation :
    public GestionRH() {
        this.listeEmploye = new ArrayList<>();
        this.affectationProjets = new HashMap<>();
    }

    // Verification de id si il existe deja :
    public boolean idExiste(String id){
        for(Employe e : listeEmploye){
            if(e.getId().equals(id))
                return true;
        }
        return false;
    }

    // Ajouter un Emoloye a la liste de employes :
    public void ajouterEmploye(Employe e){
            listeEmploye.add(e);
            System.out.println("--> Employé ajouté avec succès !");
        }

    // L'affichage de la liste des employes :
    public void afficherEmployes(){
        if(listeEmploye.isEmpty())
            System.out.println("Aucun Employe dans la liste.");
        else{
            System.out.println("--- Liste des Employes ---");
            for(Employe e : listeEmploye){
                System.out.println(e.toString());
            }
        }
    }

    // Chercher un Employe dans la liste des employes avec son ID :
    public Employe chercherEmploye(String id){
        for(Employe e : listeEmploye){
            if(e.getId().equals(id)){
                return e;
            }
        }
        return null;
    }

    // Supprimer un Eploye de la liste des employes :
    public void supprimerEmploye(String id) {
        Employe e = chercherEmploye(id);

        if (e != null) {
            // Si e n'est pas null, ça veut dire qu'on l'a trouvé
            listeEmploye.remove(e);
            affectationProjets.remove(e); // On supprime aussi du projet
            System.out.println("--> Employé supprimé avec succès.");
        } else {
            // Si e est null, l'employé n'existe pas
            System.out.println("ERREUR : Impossible de supprimer, ID introuvable.");
        }
    }

    // Affecter un projet a un employe :
    public void affecterEmployeAProjet(String id, String nomProjet) {
        Employe e = chercherEmploye(id);

        if (e != null) {
            // Employé trouvé -> on affecte
            affectationProjets.put(e, nomProjet);
            System.out.println("--> Succès : " + e.getNom() + " affecté au projet " + nomProjet);
        } else {
            // Employé non trouvé
            System.out.println("ERREUR : ID introuvable, affectation impossible.");
        }
    }

    // Afficher les affectation du projet :
    public void afficherAffectations() {
        if (affectationProjets.isEmpty()) {
            System.out.println("Aucune affectation de projet en cours.");
        } else {
            System.out.println("--- Affectations aux Projets ---");
            for (Map.Entry<Employe, String> entry : affectationProjets.entrySet()) {
                Employe e = entry.getKey();
                String projet = entry.getValue();
                System.out.println("Employé : " + e.getNom() + " (" + e.getId() + ") --> Projet : " + projet);
            }
        }
    }

    // Methode pour evaluer un Employe :
    public void evaluerEmploye(String  id){
        Employe empEval = chercherEmploye(id);

        if (empEval != null) {
            if (empEval instanceof PerformanceEvaluator) {
                boolean saisieValide = false;
                int note;
                while (!saisieValide) {
                    System.out.print("Entrez la note (sur 20) : ");

                    // On demande au scanner : "Est-ce que le prochain truc tapé est un entier ?"
                    if (sc.hasNextInt()) {
                        note = sc.nextInt(); // Oui c'est un entier, on le lit
                        // Verification de la note
                        if(note < 0 || note > 20)
                            System.out.println("Erreur : La note doit etre entre o et 20!");
                        else{
                            // La classe mère 'Employe' ne possède pas la méthode 'evaluerPerformance'.
                            // On doit donc convertir (caster) l'objet en 'PerformanceEvaluator' pour pouvoir appeler cette méthode.
                            ((PerformanceEvaluator) empEval).evaluerPerformance(note);
                            saisieValide = true; // On valide pour sortir de la boucle
                        }
                    } else {
                        // Non ce n'est pas un entier (c'est du texte comme "dix")
                        String mauvaiseSaisie = sc.nextLine(); // On lit le texte pour le "jeter" et vider le scanner
                        System.out.println("Erreur : '" + mauvaiseSaisie + "' n'est pas un nombre. Recommencez.");
                    }
                }
                sc.nextLine(); // Pour Consommer le saut de ligne restant à la fin
            } else {
                System.out.println("ERREUR : Un Stagiaire ne peut pas être évalué.");
            }
        } else {
            // si empEval est vide c'est a dire n'existe pas
            System.out.println("Employé introuvable.");
        }
    }
}