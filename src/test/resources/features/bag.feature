# language: de
Funktionalität: (AoF-L-240) Das Plugin muss der GUI die Möglichkeit bieten die Anzahl der Frösche im Beutel abzufragen.
  Diese Funktionalität bezieht sich auf die Funktion 'frogsInBag' im Interface 'Game'.

  Szenario: Tim möchte vor dem Spiel den Beutelinhalt überprüfen
  Testfall: 24-1
    Angenommen es läuft kein Spiel
    Wenn der Beutelinhalt abgefragt wird
    Dann sind 40 Frösche im Beutel

  Szenariogrundriss: Eike möchte nach einem Zug den Beutelinhalt überprüfen
    Angenommen das Spiel wird mit <Spieleranzahl> Spielern gestartet
    Und der erste Spieler hat <1SZ> Frösche gezogen
    Und der zweite Spieler hat <2SZ> Frösche gezogen
    Und der dritte Spieler hat <3SZ> Frösche gezogen
    Und der vierte Spieler hat <4SZ> Frösche gezogen
    Wenn der Beutelinhalt abgefragt wird
    Dann sind <Frösche im Beutel>

    Beispiele:
      | Spieleranzahl | 1SZ | 2SZ | 3SZ | 4SZ | Frösche im Beutel |
      | 2             | 2   | 1   | 0   | 0   | 13                |
      | 2             | 0   | 0   | 0   | 0   | 16                |
      | 3             | 10  | 10  | 10  | 0   | 0                 |

