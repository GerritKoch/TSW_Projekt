#language: de
  Funktionalität: Das Plugin muss der GUI die Möglichkeit geben zu prüfen ob ein Frosch beim Anlegen
    die Grenzen des Spielfeldes einhält.

    Szenario: Der Benutzer möchte einen Frosch auf das Spielfeld legen
      Angenommen ein Spiel läuft
      Und Spieler Blau ist am Zug
      Und die x-Koordinate des Feldes ist zwischen -50 und 50
      Und die y-Koordinate des Feldes ist zwischen -50 und 50
      Wenn Spieler Blau einen blauen Frosch auf Feld 10,100 legen möchte
      Dann wird eine Fehlermeldung angezeigt

    Szenario: Der Benutzer möchte einen Frosch auf das Spielfeld legen
      Angenommen ein Spiel läuft
      Und Spieler Blau ist am Zug
      Und die x-Koordinate des Feldes ist zwischen -50 und 50
      Und die y-Koordinate des Feldes ist zwischen -50 und 50
      Wenn Spieler Blau einen blauen Frosch auf Feld -50,50 legen möchte
      Dann ist ein blauer Frosch auf Feld -50,50