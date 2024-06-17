#language:de

Funktionalität: (AoF-Al-113) Die Spielphasen werden in einer festen Reihenfolge durchlaufen.

  Szenario: Peter möchte überprüfen, mit welcher Spielphase das Spiel beginnt.
  Testfall:
    Angenommen das Spiel ist mit 2 Spielern  gestartet
    Und es sind null Züge durchgeführt worden
    Wenn die Spielphase abgefragt wird
    Dann ist die Spielphase Anlegephase

  Szenario: Peter möchte überprüfen, welche Spielphase nach der Anlegephase kommt.
  Testfall:
    Angenommen das Spiel ist mit 2 Spielern  gestartet
    Und es sind null Züge durchgeführt worden
    Und der erste Spieler hat einen Stein angelegt
    Wenn die Spielphase abgefragt wird
    Dann ist die Spielphase Nachziehphase