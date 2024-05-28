# language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit bieten die Anzahl der Frösche im Beutel abzufragen
  Das Interface Game muss die Funktionalität von 'frogsInBag' bereitstellen.

  Szenario: Peter möchte vor dem Spiel die Anzahl der Frösche im Beutel feststellen

    Angenommen es läuft kein Spiel
    Wenn der Beutelinhalt abgefragt wird
    Dann sind 40 Frösche im Beutel

  Szenario: Peter möchte im Spiel die Anzahl der Frösche im Beutel feststellen

    Angenommen das Spiel ist mit 2 Spielern gestartet
    Und der erste Spieler hat 2 Frösche gezogen
    Und der zweite Spieler hat 1 Frösche gezogen
    Wenn der Beutelinhalt abgefragt wird
    Dann sind 13 Frösche im Beutel

  Szenariogrundriss: Peter möchte im Spiel die Anzahl der Frösche im Beutel feststellen

    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Und der erste Spieler hat <erster Spieler> Frösche gezogen
    Und der zweite Spieler hat <zweiter Spieler> Frösche gezogen
    Und der dritte Spieler hat <dritter Spieler> Frösche gezogen
    Und der vierte Spieler hat <vierter Spieler> Frösche gezogen
    Wenn der Beutelinhalt abgefragt wird
    Dann sind <Anzahl der Frösche> Frösche im Beutel

    Beispiele:
      | Spieler | erster Spieler | zweiter Spieler | dritter Spieler | vierter Spieler | Anzahl der Frösche |
      | 2       | 2              | 1               | 0               | 0               | 13                 |
      | 4       | 2              | 2               | 3               | 3               | 22                 |
      | 3       | 10             | 10              | 10              | 0               | 0                  |

