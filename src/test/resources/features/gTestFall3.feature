#language:de
Funktionalität: (AoF-Al-103) Die Anzahl der Spieler muss zwischen 2 und 4 liegen.

  Szenariogrundriss: Peter möchte das Spiel starten
    Angenommen die Anzahl der Spieler <Spieler> ist kleiner als zwei
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet


    Beispiele:
    | Spieler |
    | 1       |
    | 0       |
    | -1      |
    | -2      |
    | -3      |

  Szenariogrundriss: Peter möchte das Spiel starten
    Angenommen die Anzahl der Spieler <Spieler> ist großer als vier
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet


    Beispiele:
    | Spieler |
    | 5       |
    | 6       |
    | 7       |
    | 8       |

