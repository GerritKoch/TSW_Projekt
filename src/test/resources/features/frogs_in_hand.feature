#language: de
Funktionalität: Das Plugin soll die Möglichkeit bieten, die Farben der Frösche auf der Hand
  eines Spielers zu abzufragen. Das Interface Game muss die Funktionalität von 'frogsInHand' bereitstellen.

  Szenario: Der Benutzer möchte die Anzahl der Frösche auf seiner Hand abfragen
    Angenommen Spieler Blau hat zwei Frösche auf der Hand
    Wenn Spieler Blau die Anzahl der Frösche auf Hand des Spielers Blau abfragt
    Dann ist die Anzahl der Frösche auf der Hand des Spielers Blau 2

  Szenario: Der Benutzer möchte die Anzahl der Frösche auf seiner Hand abfragen
    Angenommen Spieler Blau hat zwei Frösche auf der Hand
    Und Spieler Blau legt einen Frosch an
    Und Spieler Blau die Anzahl der Frösche auf Hand des Spielers Blau abfragt
    Dann ist die Anzahl der Frösche auf der Hand des Spielers Blau 1