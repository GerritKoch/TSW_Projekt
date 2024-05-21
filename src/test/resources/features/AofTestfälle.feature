#language:de
Funktionalität: (AoF-Al-101)Das Plugin muss dem GUI ermöglichen, zu überprüfen,
  ob es Steine der eigenen Farbe auf dem Feld gibt.

  Szenario: Peter möchte überprüfen, ob Steine der eigenen Farbe auf dem Feld gibt
  Testfall: 42-1
    Angenommen ein Spiel läuft
    Und ein Spiel hat ein Stein von der ziehenden Farbe angelegt
    Wenn Der GUI überprüft, ob ein Stein der ziehenden Farbe angelegt ist.
    Dann gibt es mindestens ein Stein der ziehenden Farbe auf dem Feld


  Szenario: Peter möchte überprüfen, ob Steine der eigenen Farbe auf dem Feld gibt
  Testfall: 42-2
    Angenommen ein Spiel läuft
    Und es noch kein Stein angelegt wurde
    Wenn Der GUI überprüft, ob ein Stein der ziehenden Farbe angelegt ist.
    Dann gibt es kein Stein der ziehenden Farbe auf dem Feld

Funktionalität: (AoF-Al-102) Die Anzahl der Spieler soll abfragbar sein.

    Szenario:Peter möchte die Anzahl der Spieler abfragen
    Testfall:42-3
      Angenommen das Spiel ist mit <Spieler> Spielern gestartet
      Wenn die Anzahl der Spieler abgefragt wird
    Dann wurde das Spiel mit <Spieler> Spieler gestartet

Funktionalität: (AoF-Al-103) Die Anzahl der Spieler muss zwischen 2 und 4 liegen.

  Szenario:Peter möchte das Spiel starten
  Testfall:
    Angenommen die Anzahl der Spieler <Spieler> ist kleiner als 2
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet

  Szenario:Peter möchte das Spiel starten
  Testfall:
    Angenommen die Anzahl der Spieler <Spieler> ist großer als 4
    Wenn das Spiel gestartet wird
    Dann wird das Spiel beendet

Funktionalität: (AoF-Al-104) Die Anzahl an Spielfarben ist die Anzahl der Spieler.

  Szenario: Peter möchte die Spielfarben zählen
  Testfall:
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Wenn die Anzahl der Spieler abgefragt wird
    Dann gibt es <Spieler> Spielfarben auf dem Spielfeld

Funktionalität: (AoF-Al-105) Die Anzahl der Spielsteine einer Farbe beträgt 10 .

  Szenario: Peter möchte nachzählen, wie viele Spielsteine einer Farbe es gibt
  Testfall:
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Und es sind <Züge> Züge durchgeführt worden
    Wenn die Anzahl der Spielsteine von Farbe < beliebige Farbe> abgefragt wird
    Dann gibt es 10 Spielsteine der Farbe < beliebige Farbe>

Funktionalität: (AoF-Al-106) Bei Spielbeginn befinden sich alle Spielsteine im Beutel.

  Szenario:Peter möchte bei Spielbeginn die Anzahl der Steine im Beutel nachzählen
  Testfall:
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Und Es wurde kein Spielsteine auf dem Spielfeld gelegt
    Und Es wurde kein Spielsteine aus der Beutel gezogen
    Wenn die Anzahl der Spielsteine abgefragt wird
    Dann sind im Beutel <Spielsteine im Beutel> Steine

Funktionalität: (AoF-Al-107)  Durch (Nach)ziehen wird vor Spielstart <Anzahl der Spieler>-mal abwechselnd zufällig ein Stein
  aus dem Beutel gezogen, beginnend bei Spieler1.

  Szenario:Peter möchte nachzählen, wie viele Steine gezogen wurde, bevor das erste Stein angelegt wird
  Testfall:
    Angenommen das Spiel ist mit <Spieler> Spielern gestartet
    Und Es wurde kein Spielsteine auf dem Spielfeld gelegt
    Und jeder Spieler hat 2 Steine in der Hand
    Wenn die Anzahl der Spielsteine in der Hand abgefragt wird
    Dann wurden <Anzahl der Spielsteine in der Hand> gezogen

Funktionalität: (AoF-Al-108) Beim Anlegen muss an einen anderen Stein angelegt werden (außer im ersten Zug).

  Szenario:Peter möchte ein Stein in der Hand anlegen.
  Testfall:
    Angenommen Es gibt mindestens ein Spielstein auf dem Spielfeld
    Und eine Position hat kein Nachbarn
    Wenn ein Stein auf diese Position angelegt
    Dann wird das Anlegen rückgängig gemacht

Funktionalität: (AoF-Al-109) Die GUI muss die Anzahl der Verbindungen eines jeden Steins abfragen können.

  Szenario: Peter möchte wie viele Verbindung hat ein Stein
  Testfall:
    Angenommen es sind <Züge> Züge durchgeführt worden
    Wenn die Anzahl der Verbindungen von einem <beliebigem Stein> abgefragt wird
    Dann hat der <beliebeiger Stein>  <Anzahl der Verbindungen> Verbindungen

Funktionalität: (AoF-Al-110) Die GUI muss abfragen können, welche Farbe am Zug ist.

  Szenario:Peter möchte fragen, welche Farbe am Zug ist
  Testfall:
    Angenommen das Spiel ist mit 3 Spielern gestartet
    Und der erste Spieler ist mit einem Zug fertig
    Und Gabriel der dritte Spieler ist
    Wenn es gefragt wird, ob der dritte Spieler am Zug ist
    Dann der dritter Spieler ist nicht am Zug




Funktionalität: (AoF-Al-111) Nach einer Bewegung muss jeder Stein mindestens eine Verbindung haben.

  Szenario:Peter möchte ein Stein vom eigenen Farbe bewegen.
  Testfall:
    Angenommen es sind <Züge> Züge durchgeführt worden
    Und der Spieler <beliebiger Spieler> ist am Zug
    Und der Spieler <beliebiger Spieler> hat nachgezogen
    Und der Spieler <beliebiger Spieler> hat sein Stein bewegt
    Und die Anzahl der Verbindungen von dem <beliebigem Stein> wurde abgefragt
    Und die <Anzahl der Verbindung> gleich null ist
    Wenn der Stein bewegt wird.
    Dann soll diese Bewegung rückgängig gemacht werden


Funktionalität: (AoF-Al-113) Die erste Spielphase ist Nachziehen, dann Bewegen, dann Anlegen.

  Szenario:
  Testfall:
    Angenommen a
    Wenn b
    Dann

Funktionalität: (AoF-Al-114) Spielphasen können nur übersprungen werden, wenn sie nicht möglich sind.

  Szenario:
  Testfall:
    Angenommen a
    Wenn b
    Dann

Funktionalität: (AoF-Al-115) Eine Farbe gewinnt, wenn mindestens 7 Steine der Farbe zusammenhängen.

  Szenario:
  Testfall:
    Angenommen a
    Wenn b
    Dann

Funktionalität: (AoF-Al-116)  Wenn mehr als 7 Steine einer Farbe im Spiel sind, müssen sie alle zusammenhängen, um zu gewinnen.

  Szenario:
  Testfall:
    Angenommen a
    Wenn b
    Dann

Funktionalität: (AoF-Al-117) Ein Spielstein hat zwei Einzelverbindungen, wenn er genau zwei angrenzende Spielsteine hat, die nicht miteinander verbunden sind.

  Szenario:
  Testfall:
    Angenommen a
    Wenn b
  Dann

Funktionalität: (AoF-Al-118) Der jüngste Spieler beginnt.

  Szenario:
  Testfall:
    Angenommen a
    Wenn b
  Dann

Funktionalität: (AoF-Al-119) Jeder Spieler hat Einsicht in die Hände aller Spieler.


  Szenario:
  Testfall:
    Angenommen a
    Wenn b
  Dann

Funktionalität: (AoF-Al-120) Der Beutelinhalt darf sichtbar sein, solange eine random-Funktion Steine zieht.

  Szenario:
  Testfall:
    Angenommen a
    Wenn b
  Dann


  Funktionalität: (AoF-B-201) Eine Bewegung erfolgt über mindestens einen Stein.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
   Dann

  Funktionalität: (AoF-B-202) Eine Bewegung erfolgt in einer geraden Linie.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-B-203) Eine Bewegung erfolgt nur mit Steinen der ziehenden Farbe.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-B-204) Ein Stein kann mehrere Bewegungen in einem Zug ausführen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
  Dann

  Funktionalität: (AoF-B-205) Ein eingekreister Stein darf bewegt werden.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
   Dann

  Funktionalität: (AoF-B-206) Ein Stein darf seinen Zug nicht auf seiner Startposition beenden.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
   Dann

  Funktionalität: (AoF-An-301) Die GUI muss die Position der Spielsteine abfragen können.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-An-302) Die GUI darf Anlegen nur innerhalb der Positionen x[-50 bis 50] und y[-50 bis 50] zulassen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-An-303) Eine Bewegung kann nicht nach einem Anlegen erfolgen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-N-401) Wenn keine Steine im Beutel sind, wird das Nachziehen übersprungen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-N-402) Wenn die Spielhand voll ist, wird das Nachziehen übersprungen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-N-403) Die gezogene Farbe ist zufällig aus den noch vorhandenen Steinen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-N-404) Nach dem Nachziehen ist ein Stein der gezogenen Farbe weniger im Beutel.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-N-405) Nach dem Nachziehen ist ein Stein der gezogenen Farbe mehr auf der Hand.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
  Dann

  Funktionalität: (AoF-KS-501) Nach jeder Bewegung müssen alle angelegten Steine auf der Fläche zusammenhängen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-KS-502) Ein Stein darf am Ende des Anlegens oder Bewegens keine Kette entstehen lassen.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-KS-503) Am Ende des Bewegens oder Anlegens muss jeder Stein mindestens eine Verbindung haben.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-KS-504) Froschsteine dürfen nicht so bewegt oder angelegt werden,
  dass eine Kette aus Froschsteinen mit drei oder mehr Einzelverbindungen und offenem Ende entsteht.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann

  Funktionalität: (AoF-KS-505) Ein offenes Ende bedeutet, dass der letzte Froschstein einer Kette nur an einen einzigen Froschstein angrenzt.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann


  Funktionalität: (AoF-KS-506) Der erste Stein wird auf Position (0,0) gesetzt.

    Szenario:
    Testfall:
      Angenommen a
      Wenn b
    Dann
