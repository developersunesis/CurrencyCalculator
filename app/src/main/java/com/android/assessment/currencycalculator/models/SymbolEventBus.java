package com.android.assessment.currencycalculator.models;

/*Signature: Uche Emmanuel
 * Developersunesis*/

public class SymbolEventBus {

    private String response;
    private boolean success;

    public SymbolEventBus(boolean success, String response){
        this.response = response;
        this.success = success;
    }

    public String getResponse(){
        return response;
    }

    public boolean isSuccess(){
        return success;
    }
}
