#language:de

Funktionalität: (AoF-Al-204) Ein Stein kann mehrere Bewegungen in einem Zug ausführen.

  Szenario: Peter möchte überprüfen, ob man zwei Bewegungen in einem Zug ausführen kann.
  Testfall:
    Angenommen der erste Spieler ist am Zug
    Und es ist die Bewegungsphase
    Und der erste Spieler hat eine Bewegung auf Position minus eins, eins ausgeführt
    Und eine Bewegung auf Position null, eins ist möglich
    Wenn der erste Spieler Position null, eins klickt
    Dann ist Position minus eins, eins leer
    Und ein Frosch ist auf Position null, eins