```mermaid
classDiagram
    %% === Benutzer & Rollen ===    
    
    class GameField {
       <<abstract>>
    }
    
    class Desert {
        
    }

    class Landscape {
        
    }

    class Forest {
   
    }

    class SheepField {
   
    }

    class WheatField {
   
    }

    class BricksField {
   
    }

    class StoneField {
   
    }
    
    class Harbor {
   
    }
    
    GameField <|-- Desert
    GameField <|-- Landscape
    GameField <|-- Forest
    GameField <|-- SheepField
    GameField <|-- WheatField
    GameField <|-- BricksField
    GameField <|-- StoneField
    GameField <|-- Harbor


    class Resources {
        <<abstract>>
    }
    
    class Wood {
   
    }
    
    class Wool {
   
    }
    
    class Wheat {
   
    }
    
    class Clay {
   
    }
    
    class Stone {
   
    }
    
    Resources <|-- Wood
    Resources <|-- Wool
    Resources <|-- Wheat
    Resources <|-- Clay
    Resources <|-- Stone
    
    
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
    
    class Dice {
        <<static>>
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
    Player <|-- Bank
    
    
    class GUI {
        
    }
    
    class Controler {
        - rollDice()
    }
