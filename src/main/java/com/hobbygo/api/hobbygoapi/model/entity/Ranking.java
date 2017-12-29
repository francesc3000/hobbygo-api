package com.hobbygo.api.hobbygoapi.model.entity;

public class Ranking {

    private Double rating;
    private Integer totalRating;

    public Ranking(){
        rating = 0.0;
        totalRating =1;
    }

    public Double getRating() {
        return rating;
    }

    private void setRating(Double rating) {
        this.rating = rating;
    }

    private Integer getTotalRating() {
        return totalRating;
    }

    private void setTotalRating(Integer totalRating) {
        this.totalRating = totalRating;
    }

    public Boolean addRating(Double newRating) {
        Double myRating = getRating();

        myRating = ( myRating + newRating ) / getTotalRating();

        setRating(myRating);

        return true;
    }
}
