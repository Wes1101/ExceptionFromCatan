```mermaid
classDiagram
    %% === Benutzer & Rollen ===
    
    class Catan {
       <<abstract>>
    }
    
    
    class GameField {
       <<abstract>>
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
    
    Catan <|-- GameField
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
    
    Catan <|-- Resources
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
    
    Catan <|-- Development
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
    
    Catan <|-- Special
    Special <|-- LongestStreet
    Special <|-- LargestKnightPower
    
    

    class GamePieces {
        <<abstract>>
    }
    
    
    class Buildings {
        
    }
    
    class City {
   
    }
    
    class Siege {
   
    }
    
    class Street {
   
    }
    
    Buildings <|-- City
    Buildings <|-- Siege
    Buildings <|-- Street

    
    class Bandit {
   
    }
    
    class Dice {
   
    }
    
    class NumberPieces {
   
    }
    
    Catan <|-- GamePieces
    GamePieces <|-- Buildings
    GamePieces <|-- Bandit
    GamePieces <|-- Dice
    GamePieces <|-- NumberPieces
    
    
    
    class Player {
        <<abstract>>
    }
    
    class Human {
   
    }
    
    class Bot {
   
    }
    
    class Bank {
    
    }
    
    Wood <|-- Bank
    Wool <|-- Bank
    Wheat <|-- Bank
    Clay <|-- Bank
    Stone <|-- Bank
    
    Catan <|-- Player
    Player <|-- Human
    Player <|-- Bot
    Player <|-- Bank
    
    
    class View {
        <<abstract>>
    }
    
    Catan <|-- View