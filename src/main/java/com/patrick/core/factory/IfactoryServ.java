package com.patrick.core.factory;

import com.patrick.services.ClientService;
import com.patrick.services.Impl.ArticleServiceImpl;
import com.patrick.services.Impl.CommandeServiceImpl;
import com.patrick.services.Impl.UserServiceImpl;

public interface IfactoryServ {
    UserServiceImpl getInstanceUserServiceImpl();
    ClientService  getInstanceClientService();
    ArticleServiceImpl getInstanceArticleServiceImpl();
    CommandeServiceImpl getInstanceCommandeServiceImpl();
    
    
}
