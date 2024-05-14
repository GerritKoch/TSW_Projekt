# language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit geben, die Farbe des Gewinners abzufragen.
  Da die Logik die Schnittstelle 'de.fhkiel.tsw.armyoffrogs.Game' implementieren muss,
  muss die Anzahl der Frösche im Beutel abfragbar sein.

  Szenario: Gerrit möchte sehen, ob es einen Gewinner gibt
    Angenommen ein Spiel läuft
    Und Spieler Blau mitspielt
    Und mindestens 7 blaue Frösche auf dem Feld sind
    Und alle blauen Frösche an einen blauen Frosch grenzen
    Wenn die Farbe des Gewinners abgefragt wird
    Dann ist der Gewinner Spieler Blau