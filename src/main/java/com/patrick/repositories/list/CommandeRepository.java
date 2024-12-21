package com.patrick.repositories.list;
import java.util.List;

import com.patrick.entities.Detail;
import com.patrick.entities.Commande;

public interface CommandeRepository extends Repository<Commande> {
    Commande selectById(int id);  
    List<Detail> getDetailsPourDette(int detteId);
}
