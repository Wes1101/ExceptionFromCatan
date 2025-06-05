package de.dhbw.player;

import de.dhbw.resources.Resources;
import lombok.Getter;
import de.dhbw.bank.Bank;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Player extends Bank {

    private final List<Resources> resources = new ArrayList<>();
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

    public void buyFirstSettlement() {
    }

    public void buyFirstStreet() {
    }
}
