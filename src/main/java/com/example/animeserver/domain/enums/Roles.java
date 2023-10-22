package com.example.animeserver.domain.enums;

import com.example.animeserver.rest.services.exceptions.CustomException;

public enum Roles {
    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private String codeName;

    private Roles(String codeName){
        this.codeName = codeName;
    }

    public String getCodeName(){
        return codeName;
    }

    public Roles nameof(String codeName){
        for(Roles x : Roles.values()){
            if(x.getCodeName() == codeName){
                return x;
            }
        }
        throw new CustomException("Invalid Role name");
    }
}
