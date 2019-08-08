package com.android.assessment.currencycalculator;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

import com.android.assessment.currencycalculator.interfaces.SymbolService;
import com.android.assessment.currencycalculator.models.SymbolEventBus;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/*Signature: Uche Emmanuel
 * Developersunesis*/

public class SyncSymbolService extends IntentService {

    public SyncSymbolService() {
        super("SyncSymbolService");
    }

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    @Override
    protected void onHandleIntent(Intent intent) {
        SymbolService symbolService = retrofit.create(SymbolService.class);
        Call<String> symbols = symbolService.obtainSymbols();

        symbols.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        EventBus.getDefault().post(new SymbolEventBus(true, response.body()));
                    }
                } else {
                    EventBus.getDefault().post(new SymbolEventBus(false, "Ops! Something went wrong"));
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                EventBus.getDefault().post(new SymbolEventBus(false, "Ops! Something went wrong"));
            }
        });
    }

}
