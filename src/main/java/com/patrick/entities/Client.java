package com.patrick.entities;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.OneToOne;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false,of ={"nom"})
@Entity
@Table(name = "client")
public class Client extends AbstractEntity {
    private String nom;
    private String prenom;
    private String telephone;
    private String adresse;
    @OneToOne(fetch =FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user; 
    @OneToMany(mappedBy = "client")
    private List<Commande> commandes;




public void setCommandes(Commande commande) {
    commandes.add(commande);
 }




}


