#language:de

Funktionalität: (AoF-Al-105) Die Anzahl der Spielsteine einer Farbe beträgt 10 .

  Szenariogrundriss: Peter möchte nachzählen, wie viele Spielsteine einer Farbe es gibt


    Angenommen das Spiel ist mit <Spieler> Spielern  gestartet
    Wenn es sind <Zuege> Züge durchgeführt worden gab
    Wenn die Anzahl der Spielsteine von Farbe <beliebige Farbe> abgefragt wird
    Dann gibt es 10 Spielsteine der Farbe <beliebige Farbe>


  #1 = rot
    #2 = blue
    #3 = grün
    #4 = schwarz

    Beispiele:
    | Spieler | Zuege | beliebige Farbe |
    | 2       | 0     | 1               |
    | 3       | 0     | 2               |
    | 4       | 0     | 3               |

