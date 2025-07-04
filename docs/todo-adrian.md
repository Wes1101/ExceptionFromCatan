# ToDo für Adrian

## Spieler
- [ ] `activePlayer(Player player)` Methode um in der GUI anzuzeigen welcher der aktive Spieler ist. Nummer Abfragen mit
`Int playerNumber = player.getId();`, wenn name vorhanden ist mit 
```java
if (player instanceof de.dhbw.player.Human)
{
    String name = player.getName();
}
```
abrufbar. Am besten den Aktiven spieler auch in lokaer Variablen speichern, könnte man später noch mal gebrauchen

## Würfel
- [ ] `startRollDiceAnimation()` Methode um eine Animation zum wüfeln zu starten

- [ ] `showDice(Int dice1, Int dice2)` Methode mit der die Würfel in der GUI angezeigt werden können. Entweder
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

# Reihenfolge
1. Bauen
2. Spieler
2. Bandit
4. Resourcen
5. Würfel