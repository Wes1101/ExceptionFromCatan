package de.dhbw.player;

import lombok.Getter;


@Getter
public class Human extends Player {

    private String name;

    public Human(String name, int id) {
        super(0);
        this.name = name;
        this.id = id;
    }

    public void buyStreet() {

    }

    public void buySettlement() {
        return;
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
