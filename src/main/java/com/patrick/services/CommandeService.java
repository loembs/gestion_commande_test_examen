package com.patrick.services;

import java.util.List;

import com.patrick.entities.Detail;
import com.patrick.entities.Commande;


public interface CommandeService {
    void create(Commande commande);
    List<Commande>findAll();
    Commande searchById(int id); 
    List<Commande> getDettesNonSoldees();
    List<Detail> getDetailsPourDette(int detteId);
}
