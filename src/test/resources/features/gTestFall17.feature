#language:de

Funktionalität: (AoF-Al-117) Wenn mehr als 7 Steine einer Farbe im Spiel sind, müssen sie alle zusammenhängen, um zu gewinnen.

  Szenario: Peter möchte überprüfen, ob es einen Gewinner gibt bei mehr als 7 Fröschen einer Spielfarbe.
  Testfall:
  Angenommen der erste Spieler hat 8 Frösche der eigenen Farbe auf dem Feld
  Und alle Frösche des ersten Spielers grenzen an mindestens einen Frosch des ersten Spielers an
  Wenn abgefragt wird, ob es einen Gewinner gibt
  Dann ist der Gewinner der erste Spieler