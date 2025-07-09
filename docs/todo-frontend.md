# ToDo für Adrian

## Spieler
- [DONE ] `activePlayer(Player player)` Methode um in der GUI anzuzeigen welcher der aktive Spieler ist. Nummer Abfragen mit
`Int playerNumber = player.getId();`, wenn name vorhanden ist mit 
```java
@Override
public void setactivePlayer(Player player) {
  this.activePlayer = player; // Den übergebenen Spieler in der Instanzvariablen speichern
  int ID = player.getId();

  //LABEL NUR DAS setText funktioniert!!!!!!!!!!!
  Label aktiverSpieler = new Label("Aktiver Spieler: " + ID);

  //Hier GUI aktualisieren
  aktiverSpieler.setText("Aktiver Spieler: " + ID);

  // Log-Ausgabe zur Überprüfung
  log.info("Aktiver Spieler in SceneBoardController gesetzt: ID = {}", player.getId());

}
```


## Würfel
- [ DONE ] `startRollDiceAnimation()` Methode um eine Animation zum wüfeln zu starten

- [ Done ] `showDice(Int dice1, Int dice2)` Methode mit der die Würfel in der GUI angezeigt werden können. Entweder
tatsächlich einzeln oder einfach zusammenrechnen und in einem Textfeld anzeigen

## Bauen / Ressourcen
- [ ] `buildSettlement()` Methode um eine Siedlung zu bauen. Gibt zurück wo eine Siedlung gebaut wird. Maybe so lösbar,
dass du so ne Info box machst in der steht "Bitte auswählen wo eine Siedlung gebaut werden soll" (auch gerne in
Englisch) und dann die Koordinaten zurückgeben, wo das sein soll. GENAUERE DEFINITION DES DATENTYPS FOLGT

- [ ] `buildStreet()` Methode um eine Straßw zu bauen. Gibt zurück wo eine Straße gebaut wird. Maybe so lösbar,
  dass du so ne Info box machst in der steht "Bitte auswählen wo eine Straße gebaut werden soll" (auch gerne in
  Englisch) und dann die Koordinaten zurückgeben, wo das sein soll. GENAUERE DEFINITION DES DATENTYPS FOLGT

- [ ] `buildCity()` Methode um eine Stadt zu bauen. Gibt zurück wo eine Stadt gebaut wird. Maybe so lösbar,
  dass du so ne Info box machst in der steht "Bitte auswählen wo eine Stadt gebaut werden soll" (auch gerne in
  Englisch) und dann die Koordinaten zurückgeben, wo das sein soll. GENAUERE DEFINITION DES DATENTYPS FOLGT

- [ ] `updatePlayerResources(Player[] players)` Methode, mit welcher ich dir alle Spieler übergebe, dass du in der GUI
anzeigen kannst wer jetzt wie viele Ressource hat.

## Bandit

- [ ] `activateBandit(Player[] players)`

ODER

- [ ] `activateBandit()` UND `getRobbedPlayer(Player[] players)`

Methoden die zurückgeben wo der Bandit als nächstes stehen soll und wer beklaut wird. (Übergabewert players optional,
musst du schauen ob du den brauchst)

## Sonstiges
- [ ] finish turn button
- [ ] buttons aktivieren/deaktivieren
- [ ] ein/ausblenden von feldern (wie viele spieler)
- [ ] setter von vicory points
- [ ] Auswahlmenü welche karten abgeben (man hat zu viele und bandit wird aktiv)

# Reihenfolge
1. Bauen
2. Spieler
2. Bandit
4. Resourcen
5. Würfel
