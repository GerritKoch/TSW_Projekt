# language: de
Funktionalität: (Aof-l-02) Die Logik muss es minimal zwei bis maximal vier Spielern erlauben das Spiel zu spielen.
  Die Funktionalität implementiert dabei die Funktion 'boolean newGame(int numberOfPlayers)' des Interfaces 'Game'.

  Szenariogrundriss: Eike startet ein Spiel
    Wenn das Spiel mit <Anzahl> Spielern gestartet wird
    Dann wird ein Spiel erstellt
    Und es spielen <Anzahl> Spieler
    Beispiele:
      | Anzahl |
      | 2      |
      | 3      |
      | 4      |

  Szenariogrundriss: Kai versucht ein Spiel zu starten
    Wenn das Spiel mit <Anzahl> Spielern gestartet wird
    Dann wird kein Spiel erstellt
    Und es spielen 0 Spieler
    Beispiele:
      | Anzahl |
      | -1     |
      | 1      |
      | 5      |
      | 99     |