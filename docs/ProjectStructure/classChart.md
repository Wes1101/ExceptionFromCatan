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

    class NetworkManager {
        <<abstract>>
        +start()
        +stop()
        +send(data)
        +receive() data
    }

    class HostServer {
        +start()
        +acceptClients()
        +broadcast(data)
        +handleMove(data)
    }

    class ClientConnection {
        +connectToHost(address)
        +sendToHost(data)
        +receiveFromHost() data
    }

    GameController --> NetworkManager
    NetworkManager <|-- HostServer
    NetworkManager <|-- ClientConnection


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

%%Priority
    style Init fill: red
    style GUI fill: red
    style GameController fill: red
    style Rules fill: red
    style CatanBoard fill: red
    style Edge fill: red
    style Vertex fill: red
    style HexTile fill: red
    style Desert fill: red
    style Forest fill: red
    style SheepField fill: red
    style WheatField fill: red
    style BricksField fill: red
    style StoneField fill: red
    style Harbor fill: red
    style Resources fill: red
    style Wood fill: red
    style Sheep fill: red
    style Wheat fill: red
    style Bricks fill: red
    style Stone fill: red
    style Development fill: none
    style Knight fill: none
    style Progress fill: none
    style Monopoly fill: none
    style StreetConstructing fill: none
    style Invention fill: none
    style VictoryPoints fill: none
    style Special fill: none
    style LongestStreet fill: none
    style LargestKnightPower fill: none
    style GamePieces fill: red
    style Buildings fill: red
    style City fill: none
    style Settelment fill: red
    style Street fill: red
    style Bandit fill: red
    style Player fill: red
    style Human fill: red
    style Bot fill: none
    style Bank fill: red
    style NetworkManager fill: green
    style HostServer fill: green
    style ClientConnection fill: green

```