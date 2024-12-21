package com.patrick.entities;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)

@Entity
@Table(name = "commandes")
public class Commande extends AbstractEntity {

    private LocalDate date;
    private Double montantTotal ;
    @Transient
    private Double montantRestant;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable=true)
    private Client client ;
    @OneToMany(mappedBy = "commande")
    private List<Detail> details;
    
























    /*@ManyToMany
    private List<Article> articles;*/





}
