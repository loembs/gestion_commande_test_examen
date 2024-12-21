package com.patrick.services.Impl;

import java.util.List;

import com.patrick.entities.Detail;
import com.patrick.entities.Commande;
import com.patrick.repositories.list.DetailRepository;
import com.patrick.repositories.list.CommandeRepository;

import com.patrick.repositories.bd.RepositoryJpaImpl;
import com.patrick.services.CommandeService;

public class CommandeServiceImpl extends RepositoryJpaImpl<Commande> implements CommandeService {
    CommandeRepository commandeReposity;
    DetailRepository detailRepository;
    

    public CommandeServiceImpl(CommandeRepository commandeReposity) {
        super(Commande.class);
        this.commandeReposity = commandeReposity;
    }
    @Override
    public void create(Commande commande) {
        commandeReposity.insert(commande); 
    }

    @Override
    public List<Commande> findAll() {
        return  commandeReposity.selectAll();
    }

    @Override
    public Commande searchById(int id) {
        return commandeReposity.selectById(id);
    }
    @Override
    public List<Commande> getDettesNonSoldees() {
        return  commandeReposity.selectAll();
    }
    @Override
    public List<Detail> getDetailsPourDette(int detteId) {
        return  detailRepository.selectAll();
    }
   

    
}
