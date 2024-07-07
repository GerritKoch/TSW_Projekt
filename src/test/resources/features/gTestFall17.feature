#language:de

Funktionalität: (AoF-Al-117) Wenn mehr als 7 Steine einer Farbe im Spiel sind, müssen sie alle zusammenhängen, um zu gewinnen.

  Szenario: Peter möchte überprüfen, ob eine Farbe gewinnt, wenn mehr als 7 Steine einer Farbe im Spiel sind.
  Testfall:
    Angenommen das Spiel läuft
    Wenn der erste Spieler acht Steine seiner Farbe nebeneinander hat
    Dann gewinnt der erste Spieler

  Szenario: Peter möchte überprüfen, ob eine Farbe gewinnt, wenn mehr als 7 Steine einer Farbe im Spiel sind.
  Testfall:
    Angenommen das Spiel läuft
    Wenn der zweite Spieler acht Steine seiner Farbe nebeneinander hat
    Und der erste Spieler sieben Steine der Farbe hat, die nicht zusammenhängen
    Dann gewinnt der zweite Spieler