package com.omar.og.myapplication;

/**
 * Created by OG on 11/11/2015.
 */
public class Movie {
    private String id;
    private String title;
    private String releaseDateTheatre;
    private int audienceScore;
    private String Synasys;
    private String urlThumbnail;

    public String getReleaseDateTheatre() {
        return releaseDateTheatre;
    }

    public void setReleaseDateTheatre(String releaseDateTheatre) {
        this.releaseDateTheatre = releaseDateTheatre;
    }

    public int getAudienceScore() {
        return audienceScore;
    }

    public void setAudienceScore(int audienceScore) {
        this.audienceScore = audienceScore;
    }

    public String getSynasys() {
        return Synasys;
    }

    public void setSynasys(String synasys) {
        Synasys = synasys;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }

    public String getTitle() {

        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public Movie(String urlSimilar, String id, String title, String releaseDateTheatre, int audienceScore, String synasys, String urlThumbnail, String urlSelf, String urlCast, String urlReviews) {
        this.urlSimilar = urlSimilar;
        this.id = id;
        this.title = title;
        this.releaseDateTheatre = releaseDateTheatre;
        this.audienceScore = audienceScore;
        Synasys = synasys;
        this.urlThumbnail = urlThumbnail;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;

}
