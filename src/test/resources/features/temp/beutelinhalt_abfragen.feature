# language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit bieten die Anzahl der Frösche im Beutel abzufragen.
  Das Interface Game muss die Funktionalität von 'frogsInBag' bereitstellen.

  Szenario: Kai möchte sehen ob alle Frösche noch vorhanden sind
    Angenommen kein Spiel läuft
    Wenn die Anzahl der Frösche im Beutel abgefragt wird
    Dann sind im Beutel 40 Frösche

  Szenario: Eike möchte nachzählen wie viele Frösche im laufenden Spiel noch im Beutel sind
    Angenommen das Spiel ist mit 2 Spielern gestartet
    Und es sind 3 Züge durchgeführt worden
    Und der erste Spieler hat 2 Frösche gezogen
    Und der zweite Spieler hat 1 Frosch gezogen
    Wenn die Anzahl der Frösche im Beutel abgefragt wird
    Dann sind im Beutel 13 Frösche

  Szenario: Jens möchte nachzählen wie viele Frösche im laufenden Spiel noch im Beutel sind
    Angenommen das Spiel ist mit 4 Spielern gestartet
    Und es sind 0 Züge durchgeführt worden
    Und der erste Spieler hat 0 Frösche gezogen
    Und der zweite Spieler hat 0 Frosch gezogen
    Und der dritte Spieler hat 0 Frosch gezogen
    Und der vierte Spieler hat 0 Frosch gezogen
    Wenn die Anzahl der Frösche im Beutel abgefragt wird
    Dann sind im Beutel 32 Frösche

  Szenariogrundriss: Eike möchte nachzählen wie viele Frösche im laufenden Spiel noch im Beutel sind
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Und es sind <Züge> Züge durchgeführt worden
    Und der erste Spieler hat <erster Spieler> Frösche gezogen
    Und der zweite Spieler hat <zweiter Spieler> Frosch gezogen
    Und der dritte Spieler hat <dritter Spieler> Frosch gezogen
    Und der vierte Spieler hat <vierter Spieler> Frosch gezogen
    Wenn die Anzahl der Frösche im Beutel abgefragt wird
    Dann sind im Beutel <Frösche im Beutel> Frösche

    Beispiele:
      | Spieler | Züge | erster Spieler | zweiter Spieler | dritter Spieler | vierter Spieler | Frösche im Beutel |
      | 2       | 3    | 2              | 1               | 0               | 0               | 13                |
      | 4       | 0    | 0              | 0               | 0               | 0               | 32                |
      | 4       | 40   | 8              | 8               | 8               | 8               | 0                 |

