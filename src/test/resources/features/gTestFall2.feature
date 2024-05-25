#language:de

Funktionalität: (AoF-Al-102) Die Anzahl der Spieler soll abfragbar sein.

  Szenariogrundriss:Peter möchte die Anzahl der Spieler abfragen
  Testfall:42-3
    Angenommen das Spiel ist mit <Spieler> Spielern  gestartet
    Wenn die Anzahl der Spieler abgefragt wird
    Dann wurde das Spiel mit <Spieleranzahl> Spieler  gestartet

  Beispiele:
    | Spieler | Spieleranzahl |
    | 2       | 2             |