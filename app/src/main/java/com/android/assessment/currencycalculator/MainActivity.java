package com.android.assessment.currencycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.assessment.currencycalculator.database.AppDatabaseClient;
import com.android.assessment.currencycalculator.interfaces.ConversionService;
import com.android.assessment.currencycalculator.interfaces.SymbolService;
import com.android.assessment.currencycalculator.models.SymbolEventBus;
import com.android.assessment.currencycalculator.models.Symbols;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IFillFormatter;
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.navigation.NavigationView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/*Signature: Uche Emmanuel
 * Developersunesis*/

/**
 * The galaxy class
 */

public class MainActivity extends AppCompatActivity {

    PrefManager prefManager;

    /*The ints below is to control which step to take onContextMenu*/
    private static final int FROM = 0;
    private static final int TO = 1;

    /*nav_state controls the visibility of the NavView*/
    int nav_state = 0;

    /*Views declaration - start*/
    Button convert;

    ImageButton nav_button, close, closeChart;

    LinearLayout fromCurrency, toCurrency;

    NavigationView chartView;
    NavigationView navigationView;

    /*EditText*/
    EditText fromEditText, toEditText, subscribeEmail;

    /*TextViews*/
    TextView fromCurrencyText, fromCurrencyText1;
    TextView toCurrencyText, toCurrencyText1;

    /* Chart View */
    private LineChart chart;

    /*Views declaration - end*/

    //Declare the symbol variable to hold data from database
    List<Symbols> symbolsList;



    //Set up Retrofit
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://data.fixer.io/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();


    @Override
    protected void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop(){
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void onEvent(SymbolEventBus symbolEventBus){
        Log.e("Event Response", symbolEventBus.getResponse());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialize the PrefManager.class
        prefManager = new PrefManager(this);

        /*Initialize all view elements - start*/
        convert = findViewById(R.id.convert);
        nav_button = findViewById(R.id.nav_button);
        close = findViewById(R.id.close);
        closeChart = findViewById(R.id.closeChart);

        fromEditText = findViewById(R.id.fromEditText);
        toEditText = findViewById(R.id.toEditText);
        subscribeEmail = findViewById(R.id.subscribeEmail);

        navigationView = findViewById(R.id.navigationView);

        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        chartView = findViewById(R.id.chartView);

        /*initialize TextView - Start*/
        fromCurrencyText = findViewById(R.id.fromCurrencyText);
        fromCurrencyText1 = findViewById(R.id.fromCurrencyText1);

        toCurrencyText = findViewById(R.id.toCurrencyText);
        toCurrencyText1 = findViewById(R.id.toCurrencyText1);
        /*initialize TextView - End*/

        chart = findViewById(R.id.chart);
        /*Initialize all view elements - stop*/


        initViews();
    }



    private void navStateTrigger(){
        if(nav_state == 0){
            navigationView.setVisibility(View.VISIBLE);
            nav_state = 1;
        } else {
            navigationView.setVisibility(View.GONE);
            nav_state = 0;
        }
    }



    private void initViews(){
        //if first launch disable the convert button until symbols are ready
        if(prefManager.isFirstLaunch()){
            convert.setText(getString(R.string.loading));
            convert.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg_disabled));
            convert.setEnabled(false);
        }

        nav_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navStateTrigger();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navStateTrigger();
            }
        });

        closeChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chartView.setVisibility(View.GONE);
            }
        });


        findViewById(R.id.subscribeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!subscribeEmail.getText().toString().isEmpty()){
                    if(ValidateEmail.isValidEmail(subscribeEmail.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Subscription completed!", Toast.LENGTH_LONG).show();
                        subscribeEmail.setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), "Please enter a valid email address!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please enter your email address!", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.chartViewButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navStateTrigger();
                chartView.setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.documentation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://github.com/developersunesis/Currency-Calculator";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.signup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
            }
        });

        fromCurrencyText1.setText(prefManager.getLastSavedFrom());
        fromCurrencyText.setText(prefManager.getLastSavedFrom());

        toCurrencyText1.setText(prefManager.getLastSavedTo());
        toCurrencyText.setText(prefManager.getLastSavedTo());

        fromCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextMenuDisplay(view, FROM);
            }
        });

        toCurrency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contextMenuDisplay(view, TO);
            }
        });

        convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertCurrency(fromEditText, toEditText);
            }
        });


        SymbolService symbolService = retrofit.create(SymbolService.class);
        Call<String> symbols = symbolService.obtainSymbols();

        symbols.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
                    if(response.body() != null){
                        new LoadSymbols(response.body()).execute();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Ops! Something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ops! Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

        //finally populate the chart
        setChartData();
    }



    private void contextMenuDisplay(View view, final int which){
        if(symbolsList.size() > 0) {
            final PopupMenu popupMenu = new PopupMenu(this, view);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                popupMenu.setGravity(Gravity.BOTTOM);
            }

            for (Symbols symbol : symbolsList) {
                popupMenu.getMenu().add(symbol.getName());
            }

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {

                    if(which == FROM) {
                        fromCurrencyText.setText(menuItem.getTitle());
                        fromCurrencyText1.setText(menuItem.getTitle());

                        prefManager.saveLastFromCurrency(String.valueOf(menuItem.getTitle()));
                    } else {
                        toCurrencyText.setText(menuItem.getTitle());
                        toCurrencyText1.setText(menuItem.getTitle());
                        prefManager.saveLasToCurrency(String.valueOf(menuItem.getTitle()));
                    }

                    return false;
                }
            });

            popupMenu.show();
        }
    }


    /**
     * This method is to convertCurrency
     * @param inputEditText = from currency symbol
     * @param outputEditText = to currency symbol
     */
    public void convertCurrency(EditText inputEditText, final EditText outputEditText){
        String fromString = fromCurrencyText.getText().toString();
        String toString = toCurrencyText.getText().toString();
        String value =  inputEditText.getText().toString();

        //provided value != empty please proceed
        if(!value.isEmpty()) {
            ConversionService symbolService = retrofit.create(ConversionService.class);
            Call<String> symbols = symbolService.convertCurrency(
                    Constants.ACCESS_KEY, fromString, toString, value
            );

            symbols.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            String result = "No response!";
                            try {
                                JSONObject obj = new JSONObject(response.body());

                                result = String.valueOf(obj.getDouble("result"));
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Ops! Something went wrong", Toast.LENGTH_LONG).show();
                            }

                            outputEditText.setText(result);
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Ops! Something went wrong", Toast.LENGTH_LONG).show();
                }
            });
        } else {

            //gracefully toast the user
            Toast.makeText(getApplicationContext(),"Please enter an amount to be converted!", Toast.LENGTH_LONG).show();
        }
    }


    /*This method is to populate the chart with demo random data*/
    private void setChartData() {
        chart.setViewPortOffsets(0, 0, 0, 0);
        chart.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

        //Disable description
        chart.getDescription().setEnabled(false);

        //Enable the chartView touch
        chart.setTouchEnabled(true);

        //Enable the chartView Scale and Drag
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        //Enable pinch zoom
        chart.setPinchZoom(true);

        //Disable GridBackground
        chart.setDrawGridBackground(false);
        chart.setMaxHighlightDistance(300);

        chart.getLegend().setEnabled(false);

        //Smooth animate data on the chart
        chart.animateXY(2000, 2000);

        //Refresh the view
        chart.invalidate();


        
        //Generate random values for the chart
        /*
        * This is just for the demo
        * On a live production the values are to be obtained from fixer.io
        * You would have to subscribe for an above Basic package to get the api enabled
        * to serve the values
         */
        ArrayList<Entry> values = new ArrayList<>();

        //Limit to 30 points/dots
        for (int i = 0; i < 30; i++) {
            float val = (float) (Math.random() * (11)) + 20;
            values.add(new Entry(i, val));
        }

        LineDataSet set;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {

            set = (LineDataSet) chart.getData().getDataSetByIndex(0);
            set.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();

        } else {
            // create a new data set and give it a type
            set = new LineDataSet(values, "Currency Chart");

            //define custom attributes
            set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set.setCubicIntensity(0.2f);
            set.setDrawFilled(true);
            set.setDrawCircles(true);
            set.setLineWidth(0.5f);
            set.setCircleRadius(5f);
            set.setCircleHoleRadius(3f);
            set.setCircleColor(getResources().getColor(R.color.white));
            set.setCircleHoleColor(getResources().getColor(R.color.colorAccent));
            set.setHighLightColor(getResources().getColor(R.color.chartIndicator));
            set.setColor(getResources().getColor(R.color.chartIndicator));
            set.setFillColor(getResources().getColor(R.color.chartIndicator));
            set.setFillAlpha(100);
            set.setDrawHorizontalHighlightIndicator(true);
            
            set.setFillFormatter(new IFillFormatter() {
                @Override
                public float getFillLinePosition(ILineDataSet dataSet, LineDataProvider dataProvider) {
                    return chart.getAxisLeft().getAxisMinimum();
                }
            });

            //create a data object with the data sets
            LineData data = new LineData(set);
            data.setValueTextColor(getResources().getColor(R.color.white));
            data.setValueTextSize(9f);
            data.setDrawValues(true);

            //set data to chart
            chart.setData(data);
        }
    }




    /**
     * LoadSymbols AsyncTask to obtain and refresh the currencies symbols
     */
    class LoadSymbols extends AsyncTask<Void, Void, Void> {

        String responseBody;

        /**
         * @param responseBody: Load the response parameter to be used to populate the dropdown menus
         */
        private LoadSymbols(String responseBody) {
            this.responseBody = responseBody;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONObject obj = new JSONObject(responseBody).getJSONObject("symbols");

                JSONArray objNames = obj.names();
                for (int i = 0; i < (objNames != null ? objNames.length() : 0); i++) {
                    String name = objNames.getString(i);
                    String abbr = obj.getString(name);

                    Symbols symbol = new Symbols();
                    symbol.setName(name);
                    symbol.setSymbol(abbr);
                    AppDatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                            .symbolsDao().insertSymbol(symbol);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            symbolsList = AppDatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                    .symbolsDao().getAllSymbols();

            return null;
        }

        @Override
        public void onPostExecute(Void avoid){
            super.onPostExecute(avoid);

            prefManager.setIsFirstLaunch(false);

            convert.setText(getString(R.string.convert));
            convert.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_bg));
            convert.setEnabled(true);
        }
    }
}
