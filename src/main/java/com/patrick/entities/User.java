package com.patrick.entities;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.patrick.entities.Enums.Role;


import javax.persistence.OneToOne;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "users")
public class User extends AbstractEntity {
  
    private String login;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role ;
    @OneToOne
    @JoinColumn(name ="client_id",nullable=true)
    private Client client;
    
    
}
