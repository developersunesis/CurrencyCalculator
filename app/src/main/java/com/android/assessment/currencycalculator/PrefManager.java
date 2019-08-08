package com.android.assessment.currencycalculator;

import android.content.Context;
import android.content.SharedPreferences;

/*Signature: Uche Emmanuel
 * Developersunesis*/

class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    PrefManager(Context context) {
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences("currencies_pref", PRIVATE_MODE);
        editor = pref.edit();
        editor.apply();
    }

    /**
     * Set the state is the app is launching foe the first  time
     * @param isFirstLaunch : This would be used to control the API request, it would gracefully pend the user operation to load the currencies symbols
     */
    void setIsFirstLaunch(boolean isFirstLaunch) {
        editor.putBoolean("isAppFirstLaunch", isFirstLaunch);
        editor.commit();
    }

    /**
     * Get the state if already launched before
     * @return = returns a boolean
     */
    boolean isFirstLaunch() {
        return pref.getBoolean("isAppFirstLaunch", true);
    }

    /**
     * Save the last from currency symbol
     * @param from = set from param
     */
    void saveLastFromCurrency(String from){
        editor.putString("lastFromCurrency", from);
        editor.commit();
    }

    /**
     * Get the last saved from currency symbol
     * @return = returns a string
     */
    String getLastSavedFrom(){
        return pref.getString("lastFromCurrency", "USD");
    }

    /**
     * Get the last saved to currency symbol
     * @return = returns a string
     */
    String getLastSavedTo(){
        return pref.getString("lastToCurrency", "NGN");
    }

    /**
     * Save the last to currency symbol
     * @param to = set from param
     */
    void saveLasToCurrency(String to){
        editor.putString("lastToCurrency", to);
        editor.commit();
    }
}