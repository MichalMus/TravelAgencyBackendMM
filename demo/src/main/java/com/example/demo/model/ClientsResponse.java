package com.example.demo.model;

import java.util.Arrays;
import java.util.List;

public class ClientsResponse {
    private List<PersonsIdModel> clients;
    private int totalCount;

    public ClientsResponse(List<PersonsIdModel> clients, int count) {
        this.clients = clients;
        this.totalCount = count;
    }

    public List<PersonsIdModel> getClients() {
        return clients;
    }

    public void setClients(List<PersonsIdModel> clients) {
        this.clients = clients;
    }

    public int gettotalCount() {
        return totalCount;
    }

    public void settotalCount(int count) {
        this.totalCount = count;
    }

    @Override
    public String toString() {
        return "ClientsResponse{" +
                "clients=" + clients +
                ", count=" + totalCount +
                '}';
    }
}
