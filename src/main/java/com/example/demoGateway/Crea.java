package com.example.demoGateway;

public class Crea {
    private final String operation;
    private final String nomDomaine;

    public Crea() {
        this.operation = "";
        this.nomDomaine = "";
    }

    public Crea(String operation, String nomDomaine) {
        this.operation = operation;
        this.nomDomaine = nomDomaine;
    }

    public String getOperation() {
        return operation;
    }

    public String getNomDomaine() {
        return nomDomaine;
    }

    public String setOperation(String operation) {
        return operation;
    }

    public String setNomDomaine(String nomDomaine) {
        return nomDomaine;
    }


}
