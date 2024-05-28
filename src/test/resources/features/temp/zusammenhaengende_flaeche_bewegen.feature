#language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit geben zu prüfen ob ein Frosch nach
  dem letzten Sprung einer Bewegung an eine bestehende Flaeche angrenzt.

  Szenario: Der Benutzer möchte eine Bewegung auf zusammenhängende Flächen prüfen
    Angenommen Spieler Blau ist am Zug
    Und es liegen 3 Frösche auf dem Feld
    Und ein blauer Frosch liegt auf Feld 0,0
    Und ein roter Frosch liegt auf Feld 1,0
    Und ein blauer Frosch liegt auf Feld -1,0
    Wenn Spieler Blau einen blauen Frosch auf Feld 2,0 bewegen möchte
    Dann ist kein blauer Frosch auf Feld 2,0
    Und es wird eine Fehlermeldung ausgegeben

  Szenario: Der Benutzer möchte eine Bewegung auf zusammenhängende Flächen prüfen
    Angenommen Spieler Blau ist am Zug
    Und es liegen 3 Frösche auf dem Feld
    Und ein blauer Frosch liegt auf Feld 0,0
    Und ein roter Frosch liegt auf Feld 1,0
    Und ein blauer Frosch liegt auf Feld 0,-1
    Wenn Spieler Blau einen blauen Frosch auf Feld 2,0 bewegen möchte
    Dann istein blauer Frosch auf Feld 2,0
