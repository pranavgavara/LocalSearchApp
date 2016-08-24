package com.example.pranav.parcelableapp;

import android.content.Intent;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listView;
    ArrayList<Results> results;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        listView= (ListView) findViewById(R.id.listView);
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        String searchterm=getIntent().getStringExtra("searchterm");
        String zipcode=getIntent().getStringExtra("zipcode");
        Downloader downloader=new Downloader(this);
        downloader.execute(searchterm,zipcode);
    }
    public void displayProgressBar(){
        progressBar.setVisibility(View.VISIBLE);

    }
    public void setProgressBar(int progress){
        progressBar.setProgress(progress);
        if(progress==100){
            hideProgressBar();
        }

    }
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);

    }
    public void drawListView(ArrayList<Results> arrayList){
        results=new ArrayList<Results>();
        results=arrayList;
        ResultsAdapter resultsAdapter=new ResultsAdapter(this,arrayList);
        listView.setAdapter(resultsAdapter);
        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Results result=results.get(i);
        Intent intent=new Intent(this,DetailsActivity.class);
        intent.putExtra("results",result);


        startActivity(intent);
    }
}
