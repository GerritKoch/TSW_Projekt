#language:de
Funktionalität: (AoF-Al-103) Die Anzahl der Spieler muss zwischen 2 und 4 liegen.

  Szenario:Peter möchte das Spiel starten
  Testfall:
    Angenommen die Anzahl der Spieler <Spieler> ist kleiner als 2
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet

  Szenario:Peter möchte das Spiel starten
  Testfall:
    Angenommen die Anzahl der Spieler <Spieler> ist großer als 4
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet