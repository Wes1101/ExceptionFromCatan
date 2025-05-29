package de.dhbw.resources;

import lombok.Getter;

@Getter
public class Resource {
    public String name;
    public int amount;

    public Resource(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public void addAmount(int amount) {
        this.amount += amount;
    }

    public void removeAmount(int amount) {
        this.amount -= amount;
    }
}