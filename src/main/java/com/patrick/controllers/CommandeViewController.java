package com.patrick.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import com.patrick.core.factory.FactoryServ;
import com.patrick.entities.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CommandeViewController {
    private FactoryServ factoryServ = new FactoryServ();
    private ObservableList<PanierItem> panierItems = FXCollections.observableArrayList();
    private Client currentClient;

    @FXML private TextField phoneNumberField;
    @FXML private TextField nomField;
    @FXML private TextField adresseField;
    @FXML private VBox articleSection;
    @FXML private ComboBox<Article> articleComboBox;
    @FXML private TextField quantityField;
    @FXML private TextField prixField;
    @FXML private TableView<PanierItem> panierTable;
    @FXML private TableColumn<PanierItem, String> articleColumn;
    @FXML private TableColumn<PanierItem, Double> prixUnitColumn;
    @FXML private TableColumn<PanierItem, Integer> quantiteColumn;
    @FXML private TableColumn<PanierItem, Double> montantColumn;
    @FXML private Label totalLabel;

    public class PanierItem {
        private Article article;
        private int quantite;
        private double prixUnit;
        private double montant;

        public PanierItem(Article article, int quantite) {
            this.article = article;
            this.quantite = quantite;
            this.prixUnit = article.getPrix();
            this.montant = prixUnit * quantite;
        }

        // Getters
        public Article getArticle() { return article; }
        public int getQuantite() { return quantite; }
        public double getPrixUnit() { return prixUnit; }
        public double getMontant() { return montant; }
        public String getArticleName() { return article.getLibelle(); }
    }

    @FXML
    public void initialize() {
        setupPhoneNumberListener();
        setupTableColumns();
        setupArticleComboBox();
        setupQuantityListener();
        updateTotal();
    }

    private void setupPhoneNumberListener() {
        phoneNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                Client client = factoryServ.getInstanceClientService().search(newValue);
                if (client != null) {
                    currentClient = client;
                    nomField.setText(client.getNom());
                    adresseField.setText(client.getAdresse());
                    articleSection.setVisible(true);
                } else {
                    currentClient = null;
                    nomField.clear();
                    adresseField.clear();
                    articleSection.setVisible(false);
                }
            }
        });
    }

    private void setupTableColumns() {
        articleColumn.setCellValueFactory(data -> 
            new SimpleStringProperty(data.getValue().getArticleName()));
        prixUnitColumn.setCellValueFactory(data -> 
            new SimpleDoubleProperty(data.getValue().getPrixUnit()).asObject());
        quantiteColumn.setCellValueFactory(data -> 
            new SimpleIntegerProperty(data.getValue().getQuantite()).asObject());
        montantColumn.setCellValueFactory(data -> 
            new SimpleDoubleProperty(data.getValue().getMontant()).asObject());

        panierTable.setItems(panierItems);
    }

    private void setupArticleComboBox() {
        List<Article> articles = factoryServ.getInstanceArticleServiceImpl().findAll();
        articleComboBox.setItems(FXCollections.observableArrayList(articles));
        articleComboBox.setConverter(new StringConverter<Article>() {
            @Override
            public String toString(Article article) {
                return article != null ? article.getLibelle() : "";
            }

            @Override
            public Article fromString(String string) {
                return null;
            }
        });
    }

    private void setupQuantityListener() {
        quantityField.textProperty().addListener((observable, oldValue, newValue) -> {
            updatePrixField();
        });

        articleComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            updatePrixField();
        });
    }

    private void updatePrixField() {
        Article selectedArticle = articleComboBox.getValue();
        String quantityText = quantityField.getText();
        
        if (selectedArticle != null && !quantityText.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityText);
                double prix = selectedArticle.getPrix() * quantity;
                prixField.setText(String.format("%.2f", prix));
            } catch (NumberFormatException e) {
                prixField.setText("0.0");
            }
        } else {
            prixField.setText("0.0");
        }
    }

    @FXML
    private void onAddArticle() {
        Article selectedArticle = articleComboBox.getValue();
        if (selectedArticle == null) {
            showAlert("Erreur", "Veuillez sélectionner un article");
            return;
        }

        try {
            int quantite = Integer.parseInt(quantityField.getText());
            if (quantite <= 0) {
                showAlert("Erreur", "La quantité doit être supérieure à 0");
                return;
            }

            if (quantite > selectedArticle.getQtestock()) {
                showAlert("Erreur", "Stock insuffisant");
                return;
            }

            PanierItem item = new PanierItem(selectedArticle, quantite);
            panierItems.add(item);
            updateTotal();

            // Clear inputs
            articleComboBox.setValue(null);
            quantityField.clear();
            prixField.setText("0.0");

        } catch (NumberFormatException e) {
            showAlert("Erreur", "Veuillez entrer une quantité valide");
        }
    }

    private void updateTotal() {
        double total = panierItems.stream()
            .mapToDouble(PanierItem::getMontant)
            .sum();
        totalLabel.setText(String.format("%.2f", total));
    }

    @FXML
    private void onSaveDebt() {
        if (currentClient == null || panierItems.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs nécessaires");
            return;
        }

        try {
            Commande nouvellecmd = new Commande();
            nouvellecmd.setClient(currentClient);
            nouvellecmd.setDate(LocalDate.now());
            
            double montantTotal = Double.parseDouble(totalLabel.getText());
            nouvellecmd.setMontantTotal(montantTotal);

            List<Detail> details = new ArrayList<>();
            for (PanierItem item : panierItems) {
                Detail detail = new Detail();
                detail.setArticle(item.getArticle());
                detail.setPrixVente(item.getPrixUnit());
                detail.setQtecommande(item.getQuantite());
                
                // Mise à jour du stock
                Article article = item.getArticle();
                article.setQtestock(article.getQtestock() - item.getQuantite());
                factoryServ.getInstanceArticleServiceImpl().update(article);
                
                details.add(detail);
                factoryServ.getInstanceDetailServiceImpl().create(detail);
            }
            
            nouvellecmd.setDetails(details);
            factoryServ.getInstanceCommandeServiceImpl().create(nouvellecmd);

            // Clear all
            panierItems.clear();
            phoneNumberField.clear();
            nomField.clear();
            adresseField.clear();
            articleSection.setVisible(false);
            updateTotal();

            showAlert("Succès", "La dette a été enregistrée avec succès");

        } catch (Exception e) {
            showAlert("Erreur", "Une erreur est survenue lors de l'enregistrement");
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}