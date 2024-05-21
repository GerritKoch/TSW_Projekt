# language: de
Funktionalität: (Aof-l-05) Die Logik muss für die gewählte Spieleranzahl die Farben auswählen. Die Reihenfolge der Wahl ist Rot, Grün, Blau und dann Weiß.
  Die Funktionalität implementiert dabei die Funktion 'Color[] players()' des Interfaces 'Game'.

  Szenario: Eike schaut sich die Farben im Spiel mit 2 Spielern an.
    Angenommen das Spiel ist mit 2 Spielern gestartet
    Wenn die Spielerfarben abgefragt werden
    Dann ist die erste Farbe Rot
    Und die zweite Farbe Grün

  Szenario: Eike schaut sich die Farben im Spiel mit 2 Spielern an.
    Angenommen das Spiel ist mit 3 Spielern gestartet
    Wenn die Spielerfarben abgefragt werden
    Dann ist die erste Farbe Rot
    Und die zweite Farbe Grün
    Und die dritte Farbe Blau

  Szenario: Eike schaut sich die Farben im Spiel mit 2 Spielern an.
    Angenommen das Spiel ist mit 4 Spielern gestartet
    Wenn die Spielerfarben abgefragt werden
    Dann ist die erste Farbe Rot
    Und die zweite Farbe Grün
    Und die dritte Farbe Blau
    Und die vierte Farbe Weiß