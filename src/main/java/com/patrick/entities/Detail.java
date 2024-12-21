package com.patrick.entities;

import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "details")
public class Detail extends AbstractEntity {
    private Integer qtecommande;
    private Double prixVente;
    @ManyToOne
    private Article article;
    @ManyToOne
    private Commande commande; 
}
