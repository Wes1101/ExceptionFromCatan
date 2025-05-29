package org.example.execptionfromcatan;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player extends Bank {

    private final List<Resource> resources = new ArrayList<>();
    private String name;

    public Player() {
        super(0);
    }

    public void buyStreet() {

    }

    public void buySettlement() {

    }

    public void buyCity() {

    }

    public void buyDevelopmentCard() {

    }

    public void trade() {

    }

    public void rollDice() {

    }

    public void endTurn() {

    }

}
