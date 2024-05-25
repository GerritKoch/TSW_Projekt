#language:de

Funktionalität: (AoF-Al-107)  Durch (Nach)ziehen wird vor Spielstart <Anzahl der Spieler>-mal abwechselnd zufällig ein Stein
  aus dem Beutel gezogen, beginnend bei Spieler1.

  Szenariogrundriss: Peter möchte nachzählen, wie viele Steine gezogen wurde, bevor das erste Stein angelegt wird
    Angenommen das Spiel ist mit <Spieler> Spielern  gestartet
    Wenn Es wurde kein Spielsteine auf dem Spielfeld gelegt
    Und jeder Spieler hat zweimal gezogen
    Und die Anzahl der Spielsteine in der Hand abgefragt wird
    Dann wurden <Anzahl der Spielsteine in der Hand> Spielsteine gezogen

    Beispiele:
    | Spieler | Anzahl der Spielsteine in der Hand |
    | 2       | 4                                |
    | 3       | 6                                |
    | 4       | 8                                |