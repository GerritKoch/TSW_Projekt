#language:de

Funktionalität: (AoF-Al-115) Spielphasen können nur übersprungen werden, wenn sie nicht möglich sind.

  Szenario: Peter möchte überprüfen, ob Nachziehen möglich ist.
  Testfall:
    Angenommen der erste Spieler ist am Zug
    Und es ist die Nachziehphase
    Und der erste Spieler hat zwei Frösche in der Hand
    Und es sind mehr als null Frösche im Beutel
    Wenn der erste Spieler prüft, ob er Nachziehen muss
    Dann überspringt er Nachziehphase

  Szenario: Peter möchte überprüfen, ob Anlegen möglich ist.
  Testfall:
    Angenommen der erste Spieler ist am Zug
    Und es ist die Anlegephase
    Und es sind null Frösche im Beutel
    Und kein Spieler hat Frösche in der Hand
    Wenn der erste Spieler prüft, ob Anlegen möglich ist
    Dann überspringt er Anlegephase

  Szenario: Peter möchte überprüfen, ob Bewegen möglich ist.
  Testfall:
    Angenommen der erste Spieler ist am Zug
    Und es ist die Bewegenphase
    Und keine gültige Bewegung wird gefunden
    Wenn der erste Spieler prüft, ob Bewegen möglich ist
    Dann überspringt er Bewegungphase