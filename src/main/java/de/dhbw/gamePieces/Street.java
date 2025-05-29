//package de.dhbw.gamePieces;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * This class manages all functionality related to Streets, which are a subclass of Building.
// *
// * @author Atussa Mehrawari
// * @version 1.0
// *
// */
//
//public class Street extends Building {
//    public Street(Player owner, HexTile location) {
//        super(owner, location);
//    }
//
//    @Override
//    public Map<ResourceType, Integer> getBuildCost() {
//        Map<ResourceType, Integer> cost = new HashMap<>();
//        cost.put(ResourceType.WOOD,1);
//        cost.put(ResourceType.BRICK,1);
//        return cost;
//    }
//
//    @Override
//    public String getType() {
//        return "Street";
//    }
//
//    @Override
//    public String toString() {
//        return "Street owned by " + owner.getName() + " at " + location;
//    }
//
//}
