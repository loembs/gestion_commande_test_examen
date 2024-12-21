package com.patrick.core.factory.Impl;
import com.patrick.core.factory.IfactoryRepo;
import com.patrick.entities.User;
import com.patrick.repositories.bd.ArticleRepositoryBD;
import com.patrick.repositories.bd.ClientRepositoryBD;
import com.patrick.repositories.bd.DetailRepositoryBD;
import com.patrick.repositories.bd.CommandeRepositoryBD;
import com.patrick.repositories.bd.UserRepositoryBD;
import com.patrick.repositories.list.ArticleRepository;
import com.patrick.repositories.list.ClientRepository;
import com.patrick.repositories.list.DetailRepository;
import com.patrick.repositories.list.CommandeRepository;
import com.patrick.repositories.list.UserRepository;

public class FactoryRepoImpl implements IfactoryRepo{
        private  ArticleRepository articleRepository=null;
        private  ClientRepository clientRepo=null;
        private  CommandeRepository commandeRepo=null;
        private  UserRepository<User> userRepository=null;
        private  DetailRepository detailRepository=null;
        private IfactoryRepo fIfactoryRepo ;
        
    public FactoryRepoImpl(IfactoryRepo fIfactoryRepo) {
        this.fIfactoryRepo=fIfactoryRepo;
        }
    public  ClientRepository getInstanceClientRepository(){
        if(clientRepo==null){
            clientRepo=new ClientRepositoryBD();
        }
        return clientRepo;
    }
    public  CommandeRepository getInstanceCommandeRepository(){
        if(commandeRepo==null){
            commandeRepo=new  CommandeRepositoryBD();
        }
        return commandeRepo;
    }
    public  DetailRepository getInstanceDetailRepository(){
        if(detailRepository==null){
            detailRepository=new DetailRepositoryBD();
        }
        return detailRepository;
    }
    public  UserRepository<User>  getInstanceUserRepository(){
        ClientRepositoryBD clientRepositoryBD = new ClientRepositoryBD();
        if( userRepository==null){
            userRepository= new UserRepositoryBD(clientRepositoryBD);
        }
        return  userRepository;
    }
     public  ArticleRepository  getInstanceArticleRepository(){
        if(articleRepository==null){
            articleRepository= new ArticleRepositoryBD();
        }
        return  articleRepository;
    }

   
    


    //UserRepository<User> userRepository= new UserRepositoryBD(clientRepositoryBD);
    
}
