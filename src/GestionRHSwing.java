import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

// Interface graphique principale pour le système de gestion RH
public class GestionRHSwing extends JFrame {
    private GestionRH gestion; // Instance de la classe métier
    private JTable tableEmployes; // Table pour afficher les employés
    private DefaultTableModel modelTable; // Modèle de données pour la table
    private JTextArea textAreaAffectations; // Zone de texte pour les affectations

    // Constructeur principal qui initialise l'interface
    public GestionRHSwing() {
        gestion = new GestionRH(); // Initialisation du système de gestion
        initComponents(); // Création des composants graphiques
        setTitle("Systeme de Gestion des Ressources Humaines"); // Titre de la fenêtre
        setSize(1200, 700); // Dimensions de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Fermeture de l'application
        setLocationRelativeTo(null); // Centrer la fenêtre à l'écran
        setResizable(true); // Permettre le redimensionnement
    }

    // Méthode d'initialisation de tous les composants graphiques
    private void initComponents() {
        // Création du panneau principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Marges autour du panneau
        mainPanel.setBackground(new Color(240, 248, 255)); // Couleur de fond bleu clair

        // Panneau d'en-tête avec titre et logo
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Panneau central avec onglets
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 14)); // Police pour les onglets

        // Onglet 1: Gestion des employés
        JPanel panelEmployes = createEmployesPanel();
        tabbedPane.addTab("Employes", panelEmployes);

        // Onglet 2: Gestion des projets
        JPanel panelProjets = createProjetsPanel();
        tabbedPane.addTab("Projets", panelProjets);

        // Onglet 3: Évaluation des performances
        JPanel panelEvaluation = createEvaluationPanel();
        tabbedPane.addTab("Evaluation", panelEvaluation);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Panneau de pied de page avec informations
        JPanel footerPanel = createFooterPanel();
        mainPanel.add(footerPanel, BorderLayout.SOUTH);

        add(mainPanel); // Ajout du panneau principal à la fenêtre
    }

    // Création du panneau d'en-tête avec titre stylisé
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(70, 130, 180)); // Couleur bleu acier
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marges internes

        // Titre principal
        JLabel lblTitle = new JLabel("SYSTEME DE GESTION RH");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28)); // Grande police en gras
        lblTitle.setForeground(Color.WHITE); // Couleur du texte
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER); // Centrage horizontal

        // Sous-titre descriptif
        JLabel lblSubtitle = new JLabel("Gestion complete des employes et des projets");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 14)); // Police italique
        lblSubtitle.setForeground(new Color(230, 230, 250)); // Couleur lavande
        lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);

        // Organisation verticale des labels
        JPanel titlePanel = new JPanel(new GridLayout(2, 1));
        titlePanel.setBackground(new Color(70, 130, 180));
        titlePanel.add(lblTitle);
        titlePanel.add(lblSubtitle);

        panel.add(titlePanel, BorderLayout.CENTER);
        return panel;
    }

    // Création du panneau de gestion des employés (onglet 1)
    private JPanel createEmployesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau supérieur avec boutons d'action
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBackground(Color.WHITE);

        // Bouton pour ajouter un employé
        JButton btnAjouter = createStyledButton("Ajouter", new Color(46, 204, 113));
        btnAjouter.addActionListener(e -> ajouterEmployeDialog()); // Action au clic

        // Bouton pour actualiser la liste
        JButton btnActualiser = createStyledButton("Actualiser", new Color(52, 152, 219));
        btnActualiser.addActionListener(e -> rafraichirTable()); // Recharge les données

        // Bouton pour supprimer un employé
        JButton btnSupprimer = createStyledButton("Supprimer", new Color(231, 76, 60));
        btnSupprimer.addActionListener(e -> supprimerEmployeSelectionne()); // Supprime l'employé sélectionné

        // Bouton pour rechercher un employé
        JButton btnChercher = createStyledButton("Chercher", new Color(155, 89, 182));
        btnChercher.addActionListener(e -> chercherEmployeDialog()); // Ouvre dialogue de recherche

        topPanel.add(btnAjouter);
        topPanel.add(btnActualiser);
        topPanel.add(btnSupprimer);
        topPanel.add(btnChercher);

        // Création de la table pour afficher les employés
        String[] colonnes = {"Type", "ID", "Nom", "Salaire Base", "Extra", "Salaire Net"}; // En-têtes des colonnes
        modelTable = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre toutes les cellules non éditables
            }
        };

        tableEmployes = new JTable(modelTable);
        tableEmployes.setFont(new Font("Arial", Font.PLAIN, 13)); // Police de la table
        tableEmployes.setRowHeight(30); // Hauteur des lignes
        tableEmployes.setBackground(Color.WHITE); // Fond blanc pour les cellules
        tableEmployes.setForeground(Color.BLACK); // Texte noir pour les cellules
        tableEmployes.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14)); // Police des en-têtes en gras
        tableEmployes.getTableHeader().setBackground(Color.BLACK); // Couleur d'en-tête noire
        tableEmployes.getTableHeader().setForeground(Color.WHITE); // Texte blanc en en-tête
        tableEmployes.setSelectionBackground(new Color(100, 149, 237)); // Couleur de sélection bleu royal
        tableEmployes.setSelectionForeground(Color.WHITE); // Texte blanc quand sélectionné
        tableEmployes.setGridColor(new Color(180, 180, 180)); // Couleur des grilles gris foncé

        JScrollPane scrollPane = new JScrollPane(tableEmployes); // Zone scrollable pour la table
        scrollPane.setBorder(BorderFactory.createTitledBorder("Liste des Employes")); // Titre du panneau

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Création du panneau de gestion des projets (onglet 2)
    private JPanel createProjetsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau de boutons pour les actions projet
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        btnPanel.setBackground(Color.WHITE);

        // Bouton pour affecter un employé à un projet
        JButton btnAffecter = createStyledButton("Affecter au Projet", new Color(52, 152, 219));
        btnAffecter.addActionListener(e -> affecterProjetDialog()); // Dialogue d'affectation

        // Bouton pour rafraîchir les affectations
        JButton btnActualiser = createStyledButton("Actualiser", new Color(46, 204, 113));
        btnActualiser.addActionListener(e -> afficherAffectations()); // Recharge les affectations

        btnPanel.add(btnAffecter);
        btnPanel.add(btnActualiser);

        // Zone de texte pour afficher les affectations
        textAreaAffectations = new JTextArea();
        textAreaAffectations.setEditable(false); // Non éditable
        textAreaAffectations.setFont(new Font("Monospaced", Font.PLAIN, 13)); // Police monospace
        textAreaAffectations.setBackground(new Color(250, 250, 250)); // Fond gris clair
        textAreaAffectations.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marges

        JScrollPane scrollPane = new JScrollPane(textAreaAffectations); // Scrollable
        scrollPane.setBorder(BorderFactory.createTitledBorder("Affectations en Cours")); // Titre

        panel.add(btnPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    // Création du panneau d'évaluation des performances (onglet 3)
    private JPanel createEvaluationPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau central avec formulaire d'évaluation
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder("Evaluer la Performance")); // Titre du formulaire
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espacement entre composants
        gbc.fill = GridBagConstraints.HORIZONTAL; // Remplissage horizontal

        // Label et champ pour l'ID de l'employé
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblId = new JLabel("ID Employe:");
        lblId.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblId, gbc);

        gbc.gridx = 1;
        JTextField txtId = new JTextField(20); // Champ de texte pour l'ID
        txtId.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(txtId, gbc);

        // Label et slider pour la note
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblNote = new JLabel("Note (0-20):");
        lblNote.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(lblNote, gbc);

        gbc.gridx = 1;
        JSlider sliderNote = new JSlider(0, 20, 10); // Slider de 0 à 20
        sliderNote.setMajorTickSpacing(5); // Espacement des marques principales
        sliderNote.setMinorTickSpacing(1); // Espacement des petites marques
        sliderNote.setPaintTicks(true); // Afficher les marques
        sliderNote.setPaintLabels(true); // Afficher les labels numériques
        sliderNote.setBackground(Color.WHITE);

        JLabel lblNoteValue = new JLabel("10"); // Label affichant la valeur actuelle
        lblNoteValue.setFont(new Font("Arial", Font.BOLD, 16));
        lblNoteValue.setForeground(new Color(52, 152, 219));

        sliderNote.addChangeListener(e -> {
            lblNoteValue.setText(String.valueOf(sliderNote.getValue())); // Mise à jour dynamique
        });

        JPanel notePanel = new JPanel(new BorderLayout(10, 0));
        notePanel.setBackground(Color.WHITE);
        notePanel.add(sliderNote, BorderLayout.CENTER);
        notePanel.add(lblNoteValue, BorderLayout.EAST);
        formPanel.add(notePanel, gbc);

        // Bouton pour soumettre l'évaluation
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Bouton sur 2 colonnes
        JButton btnEvaluer = createStyledButton("Evaluer", new Color(46, 204, 113));
        btnEvaluer.setPreferredSize(new Dimension(200, 40)); // Taille du bouton
        btnEvaluer.addActionListener(e -> {
            evaluerEmploye(txtId.getText(), sliderNote.getValue()); // Appel de l'évaluation
        });
        formPanel.add(btnEvaluer, gbc);

        panel.add(formPanel, BorderLayout.NORTH);

        // Zone d'information sur l'évaluation
        JTextArea infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Arial", Font.PLAIN, 13));
        infoArea.setBackground(new Color(250, 250, 250));
        infoArea.setText("\n  INFORMATIONS:\n\n" +
                "  - Seuls les Managers et Techniciens peuvent etre evalues\n" +
                "  - Les Stagiaires ne peuvent pas recevoir d'evaluation\n" +
                "  - La note doit etre entre 0 et 20\n" +
                "  - Saisissez l'ID de l'employe et ajustez la note\n");
        infoArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollInfo = new JScrollPane(infoArea);
        scrollInfo.setBorder(BorderFactory.createTitledBorder("Guide d'evaluation"));

        panel.add(scrollInfo, BorderLayout.CENTER);

        return panel;
    }

    // Création du panneau de pied de page
    private JPanel createFooterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setBackground(new Color(70, 130, 180));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Label avec informations de copyright
        JLabel lblFooter = new JLabel("2025 Systeme de Gestion RH - Version 1.0");
        lblFooter.setForeground(Color.WHITE);
        lblFooter.setFont(new Font("Arial", Font.PLAIN, 12));

        panel.add(lblFooter);
        return panel;
    }

    // Méthode utilitaire pour créer des boutons stylisés
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13)); // Police en gras
        button.setBackground(bgColor); // Couleur de fond
        button.setForeground(Color.WHITE); // Texte blanc
        button.setFocusPainted(false); // Pas de contour au focus
        button.setBorderPainted(false); // Pas de bordure
        button.setPreferredSize(new Dimension(150, 35)); // Taille fixe
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur main au survol
        button.setOpaque(true); // Rendre le bouton opaque

        return button;
    }

    // Dialogue pour ajouter un nouvel employé
    private void ajouterEmployeDialog() {
        JDialog dialog = new JDialog(this, "Ajouter un Employe", true); // Dialogue modal
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this); // Centrer par rapport à la fenêtre principale

        // Panneau de formulaire
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ComboBox pour sélectionner le type d'employé
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Type:"), gbc);
        gbc.gridx = 1;
        String[] types = {"Manager", "Technicien", "Stagiaire"}; // Types disponibles
        JComboBox<String> comboType = new JComboBox<String>(types);
        comboType.setFont(new Font("Arial", Font.PLAIN, 13));
        formPanel.add(comboType, gbc);

        // Champ pour l'ID
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        JTextField txtId = new JTextField(20);
        formPanel.add(txtId, gbc);

        // Champ pour le nom
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1;
        JTextField txtNom = new JTextField(20);
        formPanel.add(txtNom, gbc);

        // Champ pour le salaire de base
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Salaire Base:"), gbc);
        gbc.gridx = 1;
        JTextField txtSalaire = new JTextField(20);
        formPanel.add(txtSalaire, gbc);

        // Label et champ pour l'extra (prime ou indemnité)
        gbc.gridx = 0; gbc.gridy = 4;
        JLabel lblExtra = new JLabel("Prime Annuelle:"); // Label dynamique
        formPanel.add(lblExtra, gbc);
        gbc.gridx = 1;
        JTextField txtExtra = new JTextField(20);
        formPanel.add(txtExtra, gbc);

        // Listener pour changer le label selon le type sélectionné
        comboType.addActionListener(e -> {
            String selected = (String) comboType.getSelectedItem();
            if ("Manager".equals(selected)) {
                lblExtra.setText("Prime Annuelle:");
                txtExtra.setEnabled(true); // Activer le champ
            } else if ("Technicien".equals(selected)) {
                lblExtra.setText("Indemnite Risque:");
                txtExtra.setEnabled(true);
            } else {
                lblExtra.setText("(Non applicable)");
                txtExtra.setEnabled(false); // Désactiver pour Stagiaire
                txtExtra.setText("");
            }
        });

        // Panneau de boutons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Bouton Valider
        JButton btnValider = createStyledButton("Valider", new Color(46, 204, 113));
        btnValider.addActionListener(e -> {
            try {
                String id = txtId.getText().trim();
                String nom = txtNom.getText().trim();

                // Validation des champs vides
                if (id.isEmpty() || nom.isEmpty() || txtSalaire.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Tous les champs doivent etre remplis!",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Vérification si l'ID existe déjà
                if (gestion.idExiste(id)) {
                    JOptionPane.showMessageDialog(dialog, "Cet ID existe deja!",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double salaire = Double.parseDouble(txtSalaire.getText().trim()); // Conversion en double
                String type = (String) comboType.getSelectedItem();

                // Création de l'employé selon le type
                if ("Manager".equals(type)) {
                    double prime = Double.parseDouble(txtExtra.getText().trim());
                    gestion.ajouterEmploye(new Manager(id, nom, salaire, prime));
                } else if ("Technicien".equals(type)) {
                    double indemnite = Double.parseDouble(txtExtra.getText().trim());
                    gestion.ajouterEmploye(new Technicien(id, nom, salaire, indemnite));
                } else {
                    gestion.ajouterEmploye(new Stagiaire(id, nom, salaire));
                }

                rafraichirTable(); // Mise à jour de la table
                dialog.dispose(); // Fermer le dialogue
                JOptionPane.showMessageDialog(this, "Employe ajoute avec succes!",
                        "Succes", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Valeurs numeriques invalides!",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Bouton Annuler
        JButton btnAnnuler = createStyledButton("Annuler", new Color(231, 76, 60));
        btnAnnuler.addActionListener(e -> dialog.dispose()); // Fermer sans sauvegarder

        btnPanel.add(btnValider);
        btnPanel.add(btnAnnuler);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true); // Afficher le dialogue
    }

    // Méthode utilitaire pour obtenir la liste des employés
    private ArrayList<Employe> getListeEmployes() {
        ArrayList<Employe> liste = new ArrayList<Employe>();
        // Utiliser la réflexion pour accéder à la liste privée
        try {
            java.lang.reflect.Field field = gestion.getClass().getDeclaredField("listeEmploye");
            field.setAccessible(true);
            liste = (ArrayList<Employe>) field.get(gestion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    // Méthode utilitaire pour obtenir les affectations
    private Map<Employe, String> getAffectations() {
        Map<Employe, String> map = new java.util.HashMap<Employe, String>();
        try {
            java.lang.reflect.Field field = gestion.getClass().getDeclaredField("affectationProjets");
            field.setAccessible(true);
            map = (Map<Employe, String>) field.get(gestion);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return map;
    }

    // Rafraîchir la table des employés avec les données actuelles
    private void rafraichirTable() {
        modelTable.setRowCount(0); // Vider la table

        // Parcourir tous les employés et les ajouter à la table
        for (Employe emp : getListeEmployes()) {
            String type = emp.getClass().getSimpleName(); // Nom de la classe
            String id = emp.getId();
            String nom = emp.getNom();
            double salaireBase = emp.getSalaireBase();
            String extra = ""; // Colonne extra (prime/indemnité)

            // Récupérer les informations spécifiques selon le type
            if (emp instanceof Manager) {
                extra = "Prime: " + ((Manager) emp).getPrimeAnnuelle();
            } else if (emp instanceof Technicien) {
                extra = "Indemnite: " + ((Technicien) emp).getIndemniteRisque();
            } else {
                extra = "Forfait Fixe";
            }

            double salaireNet = emp.calculerSalaireNet();

            // Ajouter une ligne au modèle de table
            modelTable.addRow(new Object[]{type, id, nom, salaireBase, extra, salaireNet});
        }
    }

    // Supprimer l'employé sélectionné dans la table
    private void supprimerEmployeSelectionne() {
        int selectedRow = tableEmployes.getSelectedRow(); // Index de la ligne sélectionnée

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Veuillez selectionner un employe a supprimer!",
                    "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) modelTable.getValueAt(selectedRow, 1); // Récupérer l'ID

        // Confirmation de suppression
        int confirm = JOptionPane.showConfirmDialog(this,
                "Etes-vous sur de vouloir supprimer cet employe?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            gestion.supprimerEmploye(id); // Supprimer de la gestion
            rafraichirTable(); // Mettre à jour la table
            JOptionPane.showMessageDialog(this, "Employe supprime avec succes!",
                    "Succes", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Dialogue de recherche d'un employé par ID
    private void chercherEmployeDialog() {
        String id = JOptionPane.showInputDialog(this, "Entrez l'ID de l'employe:",
                "Rechercher un Employe",
                JOptionPane.QUESTION_MESSAGE);

        if (id != null && !id.trim().isEmpty()) {
            Employe emp = gestion.chercherEmploye(id.trim()); // Recherche dans la gestion

            if (emp != null) {
                // Afficher les informations de l'employé trouvé
                String info = "Employe trouve:\n\n" + emp.toString();
                JOptionPane.showMessageDialog(this, info, "Resultat", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Aucun employe trouve avec cet ID!",
                        "Non trouve", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    // Dialogue pour affecter un employé à un projet
    private void affecterProjetDialog() {
        JDialog dialog = new JDialog(this, "Affecter a un Projet", true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Champ pour l'ID de l'employé
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("ID Employe:"), gbc);
        gbc.gridx = 1;
        JTextField txtId = new JTextField(20);
        formPanel.add(txtId, gbc);

        // Champ pour le nom du projet
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Nom Projet:"), gbc);
        gbc.gridx = 1;
        JTextField txtProjet = new JTextField(20);
        formPanel.add(txtProjet, gbc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        // Bouton Affecter
        JButton btnAffecter = createStyledButton("Affecter", new Color(46, 204, 113));
        btnAffecter.addActionListener(e -> {
            String id = txtId.getText().trim();
            String projet = txtProjet.getText().trim();

            if (id.isEmpty() || projet.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Tous les champs doivent etre remplis!",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            gestion.affecterEmployeAProjet(id, projet); // Affectation dans la gestion
            afficherAffectations(); // Mise à jour de l'affichage
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Affectation reussie!",
                    "Succes", JOptionPane.INFORMATION_MESSAGE);
        });

        // Bouton Annuler
        JButton btnAnnuler = createStyledButton("Annuler", new Color(231, 76, 60));
        btnAnnuler.addActionListener(e -> dialog.dispose());

        btnPanel.add(btnAffecter);
        btnPanel.add(btnAnnuler);

        dialog.add(formPanel, BorderLayout.CENTER);
        dialog.add(btnPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    // Afficher toutes les affectations de projets dans la zone de texte
    private void afficherAffectations() {
        textAreaAffectations.setText(""); // Vider la zone de texte

        Map<Employe, String> affectations = getAffectations();

        if (affectations.isEmpty()) {
            textAreaAffectations.setText("\n  Aucune affectation de projet en cours.\n");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("\n  AFFECTATIONS AUX PROJETS:\n");
            sb.append("  ======================================================================\n\n");

            int count = 1;
            // Parcourir toutes les affectations
            for (Map.Entry<Employe, String> entry : affectations.entrySet()) {
                Employe emp = entry.getKey();
                String projet = entry.getValue();
                sb.append(String.format("  %d. Employe: %-20s (ID: %-8s) --> Projet: %s\n",
                        count++, emp.getNom(), emp.getId(), projet));
            }

            sb.append("\n  ======================================================================\n");
            textAreaAffectations.setText(sb.toString());
        }
    }

    // Évaluer la performance d'un employé
    private void evaluerEmploye(String id, int note) {
        if (id.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer un ID!",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Employe emp = gestion.chercherEmploye(id.trim()); // Rechercher l'employé

        if (emp == null) {
            JOptionPane.showMessageDialog(this, "Employe introuvable!",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si l'employé peut être évalué
        if (emp instanceof PerformanceEvaluator) {
            ((PerformanceEvaluator) emp).evaluerPerformance(note); // Évaluation

            // Message de succès
            JOptionPane.showMessageDialog(this,
                    "Evaluation enregistree avec succes!\n\n" +
                            "Employe: " + emp.getNom() + "\n" +
                            "Note: " + note + "/20",
                    "Succes",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Les Stagiaires ne peuvent pas etre evalues!",
                    "Non autorise",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Méthode principale pour lancer l'application
    public static void main(String[] args) {
        // Définir le look and feel du système pour une meilleure apparence
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace(); // Afficher les erreurs
        }

        // Lancer l'interface sur le thread EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GestionRHSwing frame = new GestionRHSwing(); // Créer la fenêtre
                frame.setVisible(true); // Afficher la fenêtre
            }
        });
    }
}