import java.util.Scanner;

// Class Main :  C'est l'interface utilisateur.
// Elle contient la boucle while pour afficher le menu tant que l'utilisateur ne choisit pas de quitter.
public class Main {
    public static void main(String[] args) {
        GestionRH gestion = new GestionRH();
        Scanner sc = new Scanner(System.in);
        // Menu Interactif:
        System.out.println("\n\n================================================================================");
        System.out.println("              SYSTÈME DE GESTION DES RESSOURCES HUMAINES (RH)");
        System.out.println("================================================================================");
        boolean run = true;
        while (run) {
            System.out.println("\n=================================== MENU =======================================");
            System.out.println("                        1. Afficher les employés");
            System.out.println("                        2. Ajouter un employé");
            System.out.println("                        3. Chercher un employé par ID");
            System.out.println("                        4. Supprimer un employé par ID");
            System.out.println("                        5. Affecter un employé à un projet");
            System.out.println("                        6. Afficher les projets en cours");
            System.out.println("                        7. Évaluer la performance");
            System.out.println("                        8. Log out");
            System.out.println("================================================================================");
            System.out.print("Choisissez une option (1-8): ");

            String choix = sc.nextLine();
            // Gestion des choix du Menu avec Switch:
            switch (choix) {
                case "1":
                    gestion.afficherEmployes();
                    break;

                case "2":
                    System.out.println("\n--- Type d'employé à ajouter ---");
                    System.out.println("a. Manager");
                    System.out.println("b. Technicien");
                    System.out.println("c. Stagiaire");
                    System.out.print("Choisissez (a/b/c): ");
                    String type = sc.nextLine();// choisir soit a, b, ou c
                    boolean running = true;
                    // on utilise la boucle while pour redemander a l'utilisateur de saisir si le choix invalide
                    while(running){
                        if(type.equalsIgnoreCase("a")  ||  type.equalsIgnoreCase("b")  || type.equalsIgnoreCase("c")) {
                            System.out.print("ID : ");
                            String id = sc.nextLine();
                            // Si id existe deja on sort ne complete pas la saisie
                            if(gestion.idExiste(id)){
                                System.out.println("ERREUR : Cet ID existe déjà !");
                                break;
                            }
                            System.out.print("Nom : ");
                            String nom = sc.nextLine();
                            System.out.print("Salaire Base (ou Forfait) : ");
                            double salaire = sc.nextDouble();
                            if (type.equalsIgnoreCase("a")) {
                                System.out.print("Prime Annuelle : ");
                                double prime = sc.nextDouble();
                                sc.nextLine();
                                gestion.ajouterEmploye(new Manager(id, nom, salaire, prime));
                            }
                            else if (type.equalsIgnoreCase("b")) {
                                System.out.print("Indemnité Risque : ");
                                double indemnite = sc.nextDouble();
                                sc.nextLine();
                                gestion.ajouterEmploye(new Technicien(id, nom, salaire, indemnite));
                            }
                            else if (type.equalsIgnoreCase("c")) {
                                gestion.ajouterEmploye(new Stagiaire(id, nom, salaire));
                            }
                            running = false;

                        }
                        // si le choix invalide on redemande a l'utilisateur de saisir le choix encore:
                        else{
                            System.out.println("Choix de type invalide !");
                            System.out.println("\n--- Type d'employé à ajouter ---");
                            System.out.println("a. Manager");
                            System.out.println("b. Technicien");
                            System.out.println("c. Stagiaire");
                            System.out.print("Choisissez (a/b/c): ");
                            type = sc.nextLine();// choisir soit a, b, ou c
                        }
                    }
                    break;

                case "3":
                    System.out.print("Entrez l'ID a chercher : ");
                    String idChercher = sc.nextLine();
                    Employe resultat = gestion.chercherEmploye(idChercher);
                    if(resultat != null) {
                        System.out.println("Employé trouvé : "+resultat);
                    }
                    else{
                        System.out.println("Aucun Employé trouvé pour ce ID.");
                    }
                    break;

                case "4":
                    System.out.print("Entrez l'ID a supprimer : ");
                    String idSupprimer = sc.nextLine();
                    gestion.supprimerEmploye(idSupprimer);
                    break;

                case "5":
                    System.out.print("Entrez l'ID de l'employé : ");
                    String idEmp = sc.nextLine();
                    System.out.print("Entrez le nom du projet : ");
                    String nomProjet = sc.nextLine();
                    gestion.affecterEmployeAProjet(idEmp, nomProjet);
                    break;

                case "6":
                    gestion.afficherAffectations();
                    break;

                case "7":
                    System.out.print("Entrez l'ID d'employé a evaluer : ");
                    String idEvaluer = sc.nextLine();
                    gestion.evaluerEmploye(idEvaluer);
                    break;

                case "8":
                    run = false;
                    System.out.println("Au revoir !");
                    break;

                default:
                    System.out.println("Choix invalide !");
            }
        }
    }
}
