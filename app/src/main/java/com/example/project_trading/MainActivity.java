package com.example.project_trading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

import static com.example.project_trading.Pars1.generateURL;
import static com.example.project_trading.Pars1.getResponseFromURL;


public class MainActivity extends AppCompatActivity {

    private final List<Item> items = new ArrayList<>();
    private final RecyclerView.Adapter adapter = new ItemAdapter(this.items);
    private TextView result;

    static String key_alphavantage = "IIE0LYHWMPGBZFIB";
    static String key_finnhub = "c13njrv48v6qin45q270";
    private static HttpURLConnection connection;

    public URL url;


    private RecyclerView list;
    private String[] names = {"gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf", "gg", "gdf"};

    class Parsgg extends AsyncTask<URL, Void, String> {

        String response = null;

        @Override
        protected String doInBackground(URL... urls) {

            try {
                response = getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            String tiketCom = null;

            try {
                JSONObject jsonResponse = new JSONObject(s);
                JSONArray jsonArray = jsonResponse.getJSONArray("result");
                JSONObject tiket = jsonArray.getJSONObject(0);

                tiketCom = tiket.getString("displaySymbol");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            String resultStr = "Tiket: " + tiketCom;
            result.setText(resultStr);
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.textView123);

        RecyclerView recycler = findViewById(R.id.table1);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

    }

    public void add(View view) {
        EditText edit = this.findViewById(R.id.editTextTextPersonName2);
        this.items.add(new Item(edit.getText().toString()));
        edit.setText("");
        adapter.notifyItemInserted(this.items.size() - 1);
    }

    public void onStocksClick(View view) {
        System.out.println("click on Stockcss");

        URL generateURL = generateURL(key_finnhub);
        new Parsgg().execute(generateURL);
        //String gg = new

    }

    private static final class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final List<Item> items;

        public ItemAdapter(List<Item> items) {
            this.items = items;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false)) {
            };
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView name = holder.itemView.findViewById(R.id.name);
            name.setText(String.format("%s... %s", position, this.items.get(position).getName()));
        }

        @Override
        public int getItemCount() {
            return this.items.size();
        }
    }


}

