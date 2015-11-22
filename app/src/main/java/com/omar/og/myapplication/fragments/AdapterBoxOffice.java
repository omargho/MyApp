package com.omar.og.myapplication.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.omar.og.myapplication.R;
import com.omar.og.myapplication.VolleySingelton;
import com.omar.og.myapplication.toParse.Accomodation;

import java.util.ArrayList;



/**
 * Created by OG on 12/11/2015.
 */
//we need this adpter to (afficher) movies parsed
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {
   public LayoutInflater layoutInflater;
    private ImageLoader imageLoader;
    private VolleySingelton volleySingleton;
    private ArrayList<Accomodation> listAcco = new ArrayList<>();
    public  AdapterBoxOffice(Context context){
layoutInflater=LayoutInflater.from(context);
        volleySingleton = VolleySingelton.getInstance();
        //imageLoader = volleySingleton.getImageLoader();
    }

    public void setMovies(ArrayList<Accomodation> listAcco) {
        this.listAcco = listAcco;
        notifyItemRangeChanged(0, listAcco.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.single_movie,parent,false);
        ViewHolderBoxOffice viewHolder=new ViewHolderBoxOffice(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderBoxOffice holder, int position) {
        Accomodation currentAcco = listAcco.get(position);
        holder.movieTitle.setText(currentAcco.name);

            holder.movieReleaseDate.setText(currentAcco.contact);


//        int audienceScore = currentMovie.getAudienceScore();
//        if (audienceScore == -1) {
//            holder.movieAudienceScore.setRating(0.0F);
//            holder.movieAudienceScore.setAlpha(0.5F);
//        } else {
//            holder.movieAudienceScore.setRating(audienceScore / 20.0F);
//            holder.movieAudienceScore.setAlpha(1.0F);
//        }

//     String url = currentMovie.getUrlThumbnail();
//        if(url!=null&&!url.equals(""))
//        { //Toast.makeText(MyApplication.getAppContext(), url, Toast.LENGTH_SHORT).show();
//      loadImages(url, holder);}

    }


    private void loadImages(String urlThumbnail, final ViewHolderBoxOffice holder) {
        if (!urlThumbnail.equals("")) {

            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                   // holder.movieThumbnail.setImageAlpha(R.mipmap.ic_launcher);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listAcco.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder {

        ImageView movieThumbnail;
        TextView movieTitle;
        TextView movieReleaseDate;
        RatingBar movieAudienceScore;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
          movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }
    }

}
