#language: de
  Funktionalität: Das Plugin soll die Möglichkeit bieten, die Anzahl der Frösche
    auf der Hand eines Spielers zu abzufragen.

  Szenario: Der Benutzer möchte die Anzahl der Frösche auf seiner Hand abfragen
    Angenommen Spieler Blau hat 2 Frösche auf der Hand
    Wenn Spieler Blau die Anzahl der Frösche auf Hand des Spielers Blau abfragt
    Dann ist die Anzahl der Frösche auf der Hand des Spielers Blau 2

    Szenario: Der Benutzer möchte die Anzahl der Frösche auf seiner Hand abfragen
      Angenommen Spieler Blau hat 2 Frösche auf der Hand
      Und Spieler Blau legt einen Frosch an
      Und Spieler Blau die Anzahl der Frösche auf Hand des Spielers Blau abfragt
      Dann ist die Anzahl der Frösche auf der Hand des Spielers Blau 1
