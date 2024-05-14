#language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit geben zu prüfen ob ein Frosch beim Anlegen
  nach Zug 1 an eine bestehende Fläche angrenzt.

  1:1

  Szenario: Der Benutzer möchte einen Frosch auf das Spielfeld legen
    Angenommen ein Spiel läuft
    Und Spieler Blau ist am Zug
    Und es liegt mindestens 1 Frosch auf dem Spielfeld
    Und auf Feld 1,1 liegt kein Frosch
    Und Feld 1,1 hat keine Grenze
    Wenn Spieler Blau einen blauen Frosch auf Feld 1,1 legen möchte
    Dann ist kein blauer Frosch auf Feld 1,1
    Und es wird eine Fehlermeldung ausgegeben

  Szenario: Der Benutzer möchte einen Frosch auf das Spielfeld legen
    Angenommen ein Spiel läuft
    Und Spieler Blau ist am Zug
    Und es liegt mindestens 1 Frosch auf dem Spielfeld
    Und auf Feld 1,1 liegt kein Frosch
    Und Feld 1,1 hat mindestens eine Grenze
    Wenn Spieler Blau einen blauen Frosch auf Feld 1,1 legen möchte
    Dann ist ein blauer Frosch auf Feld 1,1