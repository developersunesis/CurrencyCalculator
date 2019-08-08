package com.android.assessment.currencycalculator.interfaces;

import retrofit2.Call;
import retrofit2.http.Query;
import retrofit2.http.GET;

/*Signature: Uche Emmanuel
 * Developersunesis*/

public interface ConversionService {

    /**
     *
     * @param accessKey = your access key obtained for fixer.io dashboard
     * @param fromCurrency = the currency converting from
     * @param toCurrency = the currency converting to
     * @param value = the amount/value to be converted
     * @return = return Call<String> of the api result
     */

    @GET("api/convert")
    Call<String> convertCurrency(@Query("access_key") String accessKey, @Query("from") String fromCurrency, @Query("to") String toCurrency, @Query("amount") String value);
}
