//package de.dhbw.gamePieces;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * This class administers all functions related to Settlements, which are a subclass of Building.
// *
// * @author Atussa Mehrawari
// * @version 1.0
// *
// */
//
//public class Settlement extends Building {
//
//    public Settlement(Player owner, HexTile location) {
//        super(owner, location);
//    }
//
//    @Override
//    public Map<ResourceType, Integer> getBuildCost() {
//        Map<ResourceType, Integer> cost = new HashMap<>();
//        cost.put(ResourceType.WOOD, 1);
//        cost.put(ResourceType.BRICK, 1);
//        cost.put(ResourceType.SHEEP, 1);
//        cost.put(ResourceType.WHEAT, 1);
//        return cost;
//    }
//
//    @Override
//    public String getType() {
//        return "Settlement";
//    }
//
//    @Override
//    public String toString() {
//        return "Settlement by " + owner.getName() + " at " + location;
//    }
//
//}
