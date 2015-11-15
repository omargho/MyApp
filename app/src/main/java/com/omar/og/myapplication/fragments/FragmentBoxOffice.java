package com.omar.og.myapplication.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.omar.og.myapplication.MainActivity;
import com.omar.og.myapplication.Movie;
import com.omar.og.myapplication.MyApplication;
import com.omar.og.myapplication.R;
import com.omar.og.myapplication.VolleySingelton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class FragmentBoxOffice extends Fragment implements MovieSort {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String BOXOFFICE_URL = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingelton volleySingelton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Movie> myMovies=new ArrayList<>();
    private DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
    private static AdapterBoxOffice adapterBoxOffice;
private RecyclerView recyclerView;
    public StringBuilder ss;

    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static String getRequestUrl(int limit) {
        return BOXOFFICE_URL + "?apikey=" + MyApplication.KEY_TOMATO + "&limit=" + limit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Toast.makeText(getActivity(),"faragment refresh box office",Toast.LENGTH_SHORT).show();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingelton = VolleySingelton.getInstance();
        requestQueue = volleySingelton.getRequestQueue();
        sendJsonRequest();


    }

    public void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(20), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                myMovies=parseJsonResponse(response);
                adapterBoxOffice.setMovies(myMovies);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "failed ", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }

    private ArrayList<Movie> parseJsonResponse(JSONObject response) {
        ArrayList<Movie> myMov=new ArrayList<Movie>();

        try {
             ss = new StringBuilder();
            JSONArray arrayMovies = response.getJSONArray("movies");
            for (int i = 0; i < arrayMovies.length(); i++) {
                JSONObject movie = (JSONObject) arrayMovies.get(i);
                String id = movie.getString("id");
                String title = movie.getString("title");
                JSONObject objectreleaseDate = movie.getJSONObject("release_dates");
                String relaseDate;
                if (objectreleaseDate.has("theater"))
                    relaseDate = objectreleaseDate.getString("theater");
                else
                    relaseDate = "no available data";
                JSONObject ratingObject = movie.getJSONObject("ratings");
                int rating = -1;
                if (ratingObject.has("audience_score"))
                    rating = ratingObject.getInt("audience_score");
                else if (ratingObject.has("critics_score"))
                    rating = ratingObject.getInt("critics_score");
                String synapsis =movie.getString("synopsis");
                JSONObject urls=movie.getJSONObject("posters");
                String url=urls.getString("thumbnail");
                //Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();
                //java.util.Date rlaseDate=dateFormat.parse(relaseDate);
                myMov.add(new Movie("", id, title, relaseDate, rating, synapsis, url, "", "", ""));



            }
           // Toast.makeText(getActivity(), ss.toString(), Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
return myMov;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
View mView=inflater.inflate(R.layout.fragment_box_office, container, false);
        adapterBoxOffice=new AdapterBoxOffice(getActivity());

recyclerView= (RecyclerView) mView.findViewById(R.id.moviesList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())) ;
        recyclerView.setAdapter(adapterBoxOffice);
        final LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                boolean enable = llm.findFirstCompletelyVisibleItemPosition() == 0;
                MainActivity.mWaveSwipeRefreshLayout.setEnabled(enable);
                super.onScrollStateChanged(recyclerView, newState);
            }

        });
        sendJsonRequest();
        return mView;

    }

    private void sortMovies(ArrayList<Movie> x){
        Collections.sort(x, new Comparator<Movie>() {
            @Override
            public int compare(Movie t0, Movie t1) {
                return (t1.getAudienceScore() <= t0.getAudienceScore()) ? -1 : 1;
            }
        });
        adapterBoxOffice.setMovies(x);
    }


    @Override
    public void sortMe() {
       sortMovies(myMovies);

    }
}


