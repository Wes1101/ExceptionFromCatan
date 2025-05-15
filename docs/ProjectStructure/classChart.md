```mermaid
classDiagram
    %% === Benutzer & Rollen ===    
    
    class GUI {
        
    }

    class GameController {
        
    }

    GUI <--> GameController
    GameController --> CatanBoard
    GameController --> Development
    GameController --> Special
    GameController --> Resources
    GameController --> GamePieces
    GameController --> Player
    GameController --> Bank

    class CatanBoard {
       <<abstract>>
    }

    

    class Edge {
        +id
        +list HexTile
        +EdgeA
        +EdgeB
        +boolean road
        +String player
    }

    class Vertex {
        +id
        +list HexTile
        +list Vertex
        +list Edge
        +String building
        +String player
    }

    class HexTile {
        +axialCoord
        +diceNumber
        +list Vertex
        +list Edges

        getCoord()
    }

    class Desert {
        +id
        +resource
    }

    class Landscape {
        +id
        +resource
    }

    class Forest {
        +id
        +resource
    }

    class SheepField {
        +id
        +resource
    }

    class WheatField {
        +id
        +resource
    }

    class BricksField {
        +id
        +resource
    }

    class StoneField {
        +id
        +resource
    }
    
    class Harbor {
        +id
        +resource
        
        trade()
    }
    
    HexTile <|-- Desert
    HexTile <|-- Landscape
    HexTile <|-- Forest
    HexTile <|-- SheepField
    HexTile <|-- WheatField
    HexTile <|-- BricksField
    HexTile <|-- StoneField
    HexTile <|-- Harbor

    CatanBoard <|-- HexTile
    CatanBoard <|-- Edge
    CatanBoard <|-- Vertex


    class Resources {
        <<abstract>>
    }
    
    
    class Development {
        <<abstract>>
    }
    
    class Knight {
   
    }
    
    class Progress {
   
    }
    
    class Monopoly {
   
    }
    
    class StreetConstructing {
   
    }
    
    class Invention {
   
    }
    
    class VictoryPoints {
     
    }
    
    Development <|-- Knight
    Development <|-- Progress
    Development <|-- Monopoly
    Development <|-- StreetConstructing
    Development <|-- Invention
    Development <|-- VictoryPoints
    
    
    class Special {
        <<abstract>>
    }
    
    class LongestStreet {
   
    }
    
    class LargestKnightPower {
   
    }
    
    Special <|-- LongestStreet
    Special <|-- LargestKnightPower
    
    

    class GamePieces {
        <<abstract>>
    }
    
    
    class Buildings {
        <<abstract>>
    }
    
    class City {
   
    }
    
    class Settelment {
   
    }
    
    class Street {
   
    }
    
    Buildings <|-- City
    Buildings <|-- Settelment
    Buildings <|-- Street

    
    class Bandit {
   
    }
    
    class NumberPieces {
   
    }
    
    GamePieces <|-- Buildings
    GamePieces <|-- Bandit
    GamePieces <|-- NumberPieces
    
    
    
    class Player {
        <<abstract>>
    }
    
    class Human {
   
    }
    
    class Bot {
   
    }
    
    Player <|-- Human
    Player <|-- Bot
    
    class Bank {
   
    }