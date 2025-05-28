package org.example.execptionfromcatan;

/**
 * This Class is administrating all main functions of the Buildings.
 *
 * @Atussa Mehrawari
 * @Version 1.0
 *
 */

public abstract class Building extends GamePieces {
    protected Player owner;

    public Building(Player owner, Hextile location) {
        super(location);
        this.owner = owner;
    }
    public Player getOwner() {
        return owner;
    }

    public abstract Map<ResourceType, Integer> getBuildCost(); //** Methode, die zurückgibt, was das Gebäude kostet*/

    public abstract String getType();  //** Gebäude Typ*/
}
