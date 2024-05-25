#language:de

Funktionalität: (AoF-B-202) Eine Bewegung erfolgt in einer geraden Linie.

  Szenario: Peter möchte überprüfen, ob eine Bewegung in einer nicht geraden Linie möglich ist.
  Testfall:
    Angenommen der erste Spieler ist am Zug
    Und es ist die Bewegegenphase
    Und der erste Spieler hat einen Frosch seiner Farbe auf Position null, eins geklickt
    Und es gibt einen Frosch auf Position null, null
    Und es gibt keinen Frosch auf Position null, minus eins
    Wenn eine optionale Bewegung nach Position null, minus eins abgefragt wird
    Dann ist die Bewegung nicht möglich