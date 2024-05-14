#language: de

Funktionalität: (AoF-Al-102)Das Plugin muss dem GUI ermöglichen, die Anzahl der Spieler abzufragen

  Szenario: Peter möchte die Anzahl der Spieler abfragen
  Testfall:  Die Anzahl der Spieler soll abfragbar sein.
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Wenn die Anzahl der Spieler abgefragt wird
    Dann wurde das Spiel mit <Spieler> Spieler gestartet

  Szenario:Peter möchte das Spiel starten
  Testfall: Die Anzahl der Spieler muss zwischen 2 und 4 liegen.
    Angenommen die Anzahl der Spieler <Spieler> ist kleiner als 2
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet

  Szenario:Peter möchte das Spiel starten
  Testfall: Die Anzahl der Spieler muss zwischen 2 und 4 liegen.
    Angenommen die Anzahl der Spieler <Spieler> ist großer als 4
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet

  Szenario: Peter möchte die Spielfarben zählen
  Testfall: Die Anzahl an Spielfarben ist die Anzahl der Spieler.
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Wenn die Anzahl der Spieler abgefragt wird
    Dann gibt es <Spieler> Spielfarben auf dem Spielfeld

  Szenario:Peter möchte fragen, welche Farbe am Zug ist
  Testfall:Die GUI muss abfragen können, welche Farbe am Zug ist.
    Angenommen das Spiel ist mit 3 Spielern gestartet
    Und der erste Spieler ist mit einem Zug fertig
    Und Gabriel der dritte Spieler ist
    Wenn es gefragt wird, ob der dritte Spieler am Zug ist
    Dann der dritter Spieler ist nicht am Zug