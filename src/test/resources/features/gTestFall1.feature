#language:de
Funktionalität: (AoF-Al-101)Das Plugin muss dem GUI ermöglichen, zu überprüfen,
  ob es Steine der eigenen Farbe auf dem Feld gibt.

  Szenario: Peter möchte überprüfen, ob Steine der eigenen Farbe auf dem Feld gibt
  Testfall: 42-1
    Angenommen ein Spiel läuft
    Und ein Spieler hat ein Stein von der ziehenden Farbe angelegt
    Wenn Der GUI überprüft, ob ein Stein der ziehenden Farbe angelegt ist.
    Dann gibt es mindestens ein Stein der ziehenden Farbe auf dem Feld

  Szenario: Peter möchte überprüfen, ob Steine der eigenen Farbe auf dem Feld gibt
  Testfall: 42-2
    Angenommen ein Spiel läuft
    Und es noch kein Stein angelegt wurde
    Wenn Der GUI überprüft, ob ein Stein der ziehenden Farbe angelegt ist.
    Dann gibt es kein Stein der ziehenden Farbe auf dem Feld