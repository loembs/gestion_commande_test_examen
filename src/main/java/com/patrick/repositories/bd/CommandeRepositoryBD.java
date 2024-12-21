package com.patrick.repositories.bd;

import java.util.List;

import com.patrick.entities.Detail;
import com.patrick.entities.Commande;
import com.patrick.repositories.list.CommandeRepository;

public class CommandeRepositoryBD extends RepositoryJpaImpl<Commande> implements CommandeRepository {
    String SQL_GET_DETTE_NONSOLDEES="SELECT d FROM Commande d WHERE d.client.id = :clientId AND (d.montant - d.montantVerser)!=0";
    public CommandeRepositoryBD() {
        super(Commande.class);
    }
    
    @Override
    public List<Detail> getDetailsPourDette(int detteId) {
        Commande dette = em.find(Commande.class, detteId);
            return dette.getDetails(); 
    }
   
}
