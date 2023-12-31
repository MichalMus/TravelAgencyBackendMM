package com.example.demo.model;

import java.util.List;

public class HotelResponse {
    private List<HotelModel> hotels;
    private int totalCount;

    public HotelResponse(List<HotelModel> hotels, int totalCount) {
        this.hotels = hotels;
        this.totalCount = totalCount;
    }

    public List<HotelModel> getHotels() {
        return hotels;
    }

    public void setHotels(List<HotelModel> hotels) {
        this.hotels = hotels;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "HotelResponse{" +
                "hotels=" + hotels +
                ", totalCount=" + totalCount +
                '}';
    }
}
