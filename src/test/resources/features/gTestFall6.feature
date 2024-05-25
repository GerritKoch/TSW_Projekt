#language:de
  Funktionalität: (AoF-Al-106) Bei Spielbeginn befinden sich alle Spielsteine im Beutel.

  Szenariogrundriss:Peter möchte bei Spielbeginn die Anzahl der Steine im Beutel nachzählen
  Testfall:

  Angenommen das Spiel ist mit <Spieler> Spielern  gestartet
  Wenn Es wurde kein Spielsteine auf dem Spielfeld gelegt
  Und Es wurde kein Spielsteine aus der Beutel gezogen
  Und die Anzahl der Spielsteine abgefragt wird
  Dann sind im Beutel <Spielsteine im Beutel> Steine

  Beispiele:
    | Spieler | Spielsteine im Beutel |
    | 2       | 20                   |
    | 3       | 30                   |
    | 4       | 40                   |