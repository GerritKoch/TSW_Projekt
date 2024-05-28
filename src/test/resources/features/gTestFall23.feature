#language:de

Funktionalität: (AoF-B-201) Eine Bewegung erfolgt über mindestens einen Stein.

  Szenario: Peter möchte überprüfen, ob eine Bewegung über einen Stein möglich ist.
  Testfall:
    Angenommen der erste Spieler ist am Zug
    Und es ist die Bewegegenphase
    Und der erste Spieler hat einen Frosch seiner Farbe auf Position null, eins geklickt
    Und es gibt einen Frosch auf Position null, null
    Und es gibt keinen Frosch auf Position minus eins, eins
    Wenn eine optionale Bewegung nach Position minus eins, eins abgefragt wird
    Dann ist die Bewegung möglich