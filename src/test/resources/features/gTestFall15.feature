#language: de

Funktionalität: (AoF-AI-115) Spielphasen können nur übersprungen werden, wenn sie nicht möglich sind.

  Szenario: Peter möchte überprüfen, ob die Spielphase übersprungen werden kann.
  Testfall:
    Angenommen das Spiel ist mit 2 Spielern  gestartet
    Und es sind null Züge durchgeführt worden
    Wenn die Spielphase Bewegung übersprungen wird
    Dann ist die Spielphase Anlegephase


  Szenario: Peter möchte überprüfen, ob die Spielphase übersprungen werden kann.
  Testfall:
    Angenommen das Spiel ist mit 2 Spielern  gestartet
    Und es sind null Züge durchgeführt worden
    Und der erste Spieler hat einen Stein angelegt
    Wenn die nächste Spieler bewegen möchte
    Dann ist die Spielphase Anlegephase
