package com.android.assessment.currencycalculator.interfaces;

import com.android.assessment.currencycalculator.Constants;

import retrofit2.Call;
import retrofit2.http.GET;

/*Signature: Uche Emmanuel
 * Developersunesis*/

public interface SymbolService {

    /**
     * This method is to obtain all currencies symbol
     * @return = return a JSONString
     */
    @GET("api/symbols?access_key="+ Constants.ACCESS_KEY)
    Call<String> obtainSymbols();

}

