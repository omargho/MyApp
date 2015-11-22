package com.omar.og.myapplication.fragments;


import android.content.Intent;
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
import com.omar.og.myapplication.R;
import com.omar.og.myapplication.SampleActivity;
import com.omar.og.myapplication.VolleySingelton;
import com.omar.og.myapplication.toParse.Accomodation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class FragmentBoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String BOXOFFICE_URL = "http://51.255.40.20/accommodation/";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingelton volleySingelton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private ArrayList<Accomodation> myAcco=new ArrayList<>();
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

    public static String getRequestUrl(String city) {
        return BOXOFFICE_URL + city;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
       // Toast.makeText(getActivity(),"faragment refresh box office",Toast.LENGTH_SHORT).show();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        volleySingelton = VolleySingelton.getInstance();
        requestQueue = volleySingelton.getRequestQueue();
        sendJsonRequest();


    }

    public void sendJsonRequest() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getRequestUrl(""), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
              myAcco=parseJsonResponse(response);
               adapterBoxOffice.setMovies(myAcco);
             //   Toast.makeText(MyApplication,response.toString(),Toast.LENGTH_LONG).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "failed ", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(request);

    }

    private ArrayList<Accomodation> parseJsonResponse(JSONObject response) {
        ArrayList<Accomodation> myMov=new ArrayList<Accomodation>();

        try {
             ss = new StringBuilder();
            JSONArray accommodations = response.getJSONArray("accommodation");
            for (int i = 0; i < accommodations.length(); i++) {
                JSONObject accommodation = (JSONObject) accommodations.get(i);
                String address = accommodation.getString("address");

                String city = accommodation.getString("city");
               // Toast.makeText(MyApplication.getAppContext(),city,Toast.LENGTH_LONG).show();
                String name = accommodation.getString("name");
                String website = accommodation.getString("website");
                String type = accommodation.getString("type");
                boolean availability=accommodation.getString("availability").equals("1");
                JSONObject location=accommodation.getJSONObject("location");
                String lonng=location.getString("long");
                String lat=location.getString("lat");
                String contact = accommodation.getString("contact");
                String id = accommodation.getString("id");
//                JSONObject objectreleaseDate = accommodation.getJSONObject("release_dates");
//                String relaseDate;
//                if (objectreleaseDate.has("theater"))
//                    relaseDate = objectreleaseDate.getString("theater");
//                else
//                    relaseDate = "no available data";
//                JSONObject ratingObject = accommodation.getJSONObject("ratings");
//                int rating = -1;
//                if (ratingObject.has("audience_score"))
//                    rating = ratingObject.getInt("audience_score");
//                else if (ratingObject.has("critics_score"))
//                    rating = ratingObject.getInt("critics_score");
//                String synapsis =movie.getString("synopsis");
//                JSONObject urls=movie.getJSONObject("posters");
//                String url=urls.getString("thumbnail");
                //Toast.makeText(getActivity(),url,Toast.LENGTH_SHORT).show();
                //java.util.Date rlaseDate=dateFormat.parse(relaseDate);

                myMov.add(new Accomodation(type,name,lonng,lat,contact,id,city,address,website));



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
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent map = new Intent(getActivity(), SampleActivity.class);
                      //  map.putExtra("ACCO", (Parcelable) myAcco.get(position));

                        startActivity(map);
                       // Toast.makeText(getActivity(),myAcco.get(position),Toast.LENGTH_SHORT).show();
                    }
                })
        );
        return mView;

    }

//    private void sortMovies(ArrayList<Movie> x){
//        Collections.sort(x, new Comparator<Movie>() {
//            @Override
//            public int compare(Movie t0, Movie t1) {
//                return (t1.getAudienceScore() <= t0.getAudienceScore()) ? -1 : 1;
//            }
//        });
//        adapterBoxOffice.setMovies(x);
//    }


//    @Override
////    public void sortMe() {
////        sortMovies(myMovies);
////
////    }
}


