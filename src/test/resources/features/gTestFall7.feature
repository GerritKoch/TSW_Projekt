#language:de

Funktionalität: (AoF-Al-107)  Durch (Nach)ziehen wird vor Spielstart <Anzahl der Spieler>-mal abwechselnd zufällig ein Stein
  aus dem Beutel gezogen, beginnend bei Spieler1.

  Szenario:Peter möchte nachzählen, wie viele Steine gezogen wurde, bevor das erste Stein angelegt wird
  Testfall:
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Und Es wurde kein Spielsteine auf dem Spielfeld gelegt
    Und jeder Spieler hat 2 Steine in der Hand
    Wenn die Anzahl der Spielsteine in der Hand abgefragt wird
    Dann wurden <Anzahl der Spielsteine in der Hand> gezogen