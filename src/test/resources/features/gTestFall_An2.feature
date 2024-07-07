#language:de

Funktionalität: (AoF-An-302) Die GUI darf Anlegen nur innerhalb der Positionen x[-50 bis 50] und y[-50 bis 50] zulassen.

  Szenario: Peter versucht ein Stein  auf einer ungültigen Position zu platzieren.

    Angenommen Das Spiel ist mit zwei Spielern gestartet.
    Wenn Peter versucht ein Stein auf einer Position x[-51] und y[0] zu platzieren.
    Dann dieses Stein wird nicht platziert.