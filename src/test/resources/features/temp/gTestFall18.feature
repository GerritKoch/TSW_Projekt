#language:de

Funktionalität: (AoF-Al-118) Ein Spielstein hat zwei Einzelverbindungen, wenn er genau zwei angrenzende Spielsteine hat, die nicht miteinander verbunden sind.

  Szenario: Peter möchte überprüfen, ob ein Position zwei Einzelverbindungen hat
  Testfall:
  Angenommen es gibt einen Frosch auf Position null, null
  Und es gibt einen Frosch auf Position null, eins
  Und es gibt einen Frosch auf Position null, minus eins
  Wenn die Einzelverbindungen des Froschs auf Position null, null abgefragt werden
  Dann hat er zwei Einzelverbindungen