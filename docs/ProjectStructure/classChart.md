```mermaid
classDiagram
    %% === Benutzer & Rollen === 

    class Init {
        +static main(string args[])
    }   
    
    class GUI {
        
    }

    class GameController {
        -list~Player~ players
        -list~Player~ sequence
        -Bank bank
        -CatanBoard catanboard
        -int gameRound
        -int dice1
        -int dice2
        -int victoryPoints

        +GameController()
        +gameStart()
        +mainGame()
        +gameEnd()
        +rollDice()
    }

    class Rules {
        +checkCity()
        +checkSettlement()
        +checkStreet()
    }

    class CatanBoard {
       <<abstract>>

       +triggerBoard(diceNumber)
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
        +NumberPieces diceNumber

        +getCoord()
    }

    class Desert {
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
        
        +trade()
    }

    class Resources {
        <<abstract>>
    }

    class Wood {

    }

    class Sheep {
        
    }

    class Wheat {
        
    }

    class Bricks {
        
    }
    
    class Stone {
        
    }

    class Development {
        <<abstract>>
        +playCard()
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
    
    class Special {
        <<abstract>>
        +checkAchievement()
    }
    
    class LongestStreet {
   
    }
    
    class LargestKnightPower {
   
    }
    
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
    
    class Bandit {
        -HexTile location
        -discardTrigger

        moveBandit(player)
    }
    
    class Player {
        <<abstract>>
        -list~Resource~ resources
        -list~Development~ cards

        +buySettelment()
        +buyCity()
        +buyStreet()
        +buyCard()
        +discardRandomCard() Resource
        +trade()
    }
    
    class Human {
   
    }
    
    class Bot {
   
    }
    
    class Bank {
        -int wood
        -int SheepField
        -int wheat
        -int bricks
        -int stone
        -list~Development~ cards
        
        +buyDevelopmentCard() card
        +spendResources(player, resource, amount)
    }

    %% Relationshis
    
    %% Inheritance
    HexTile <|-- Desert
    HexTile <|-- Forest
    HexTile <|-- SheepField
    HexTile <|-- WheatField
    HexTile <|-- BricksField
    HexTile <|-- StoneField
    HexTile <|-- Harbor
    
    CatanBoard <|-- HexTile
    CatanBoard <|-- Edge
    CatanBoard <|-- Vertex
    
    Player <|-- Human
    Player <|-- Bot
    
    GamePieces <|-- Buildings
    GamePieces <|-- Bandit
    
    Buildings <|-- City
    Buildings <|-- Settelment
    Buildings <|-- Street
    
    Special <|-- LongestStreet
    Special <|-- LargestKnightPower
    
    Resources <|-- Wood
    Resources <|-- Sheep
    Resources <|-- Wheat
    Resources <|-- Bricks
    Resources <|-- Stone
    
    Development <|-- Knight
    Development <|-- Progress
    Development <|-- Monopoly
    Development <|-- StreetConstructing
    Development <|-- Invention
    Development <|-- VictoryPoints
    
    %% Association
    Init --> GameController
    Init --> GUI
    
    GUI <--> GameController
    
    GameController --> CatanBoard
    GameController --> Special
    GameController --> GamePieces
    GameController --> Player
    GameController --> Bank
    
    CatanBoard --> Bank
    
    Bank <--> Player
    Bank --> Development
    Bank --> Resources
    
    Player --> Buildings
    Player --> CatanBoard
    
```