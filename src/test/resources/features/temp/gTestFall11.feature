#language:de

Funktionalität: (AoF-Al-111) Die GUI soll den ersten Zug gesondert behandeln.


  Szenario: Peter möchte überprüfen, ob der allererste Stein auf das Feld gelegt werden kann.
  Testfall:
    Angenommen ein Spiel läuft
    Und das Spiel ist mit 4 Spielern gestartet
    Und es sind 0 Züge durchgeführt worden
    Wenn Spieler 1 einen Frosch zum Anlegen gewählt hat
    Dann ist 1 Frosch auf Position null, null