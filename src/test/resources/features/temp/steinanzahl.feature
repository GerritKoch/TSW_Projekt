#language: de

  Funktionalität: (AoF-Al-101)Das Plugin muss dem GUI ermöglichen, die Anzahl der Steine abzufragen


    Szenario: Peter möchte nachzählen, wie viele Spielsteine einer Farbe es gibt
    Testfall: Die Anzahl der Spielsteine einer Farbe beträgt 10 .
      Angenommen das Spiel ist mit <Spieler> Spielern gestartet
      Und es sind <Züge> Züge durchgeführt worden
      Wenn die Anzahl der Spielsteine von Farbe < beliebige Farbe> abgefragt wird
      Dann gibt es 10 Spielsteine der Farbe < beliebige Farbe>


    Szenario: Peter möchte überprüfen, ob Steine der ziehenden Farbe auf dem Feld gibt
    Testfall: 42-1 Überprüfen,ob es Steine der ziehenden Farbe auf dem Feld gibt.
      Angenommen ein Spiel läuft
      Und ein Spiel hat ein Stein von der ziehenden Farbe angelegt
      Wenn Der GUI überprüft, ob ein Stein der ziehenden Farbe angelegt ist.
      Dann gibt es mindestens ein Stein der ziehenden Farbe auf dem Feld

    Szenario: Peter möchte überprüfen, ob Steine der eigenen Farbe auf dem Feld gibt
    Testfall: 42-2 Überprüfen,ob es Steine der ziehenden Farbe auf dem Feld gibt.
      Angenommen ein Spiel läuft
      Und es noch kein Stein angelegt wurde
      Wenn Der GUI überprüft, ob ein Stein der ziehenden Farbe angelegt ist.
      Dann gibt es kein Stein der ziehenden Farbe auf dem Feld



    Szenario:Peter möchte bei Spielbeginn die Anzahl der Steine im Beutel nachzählen
    Testfall:
      Angenommen das Spiel ist mit <Spieler> Spielern gestartet
      Und Es wurde kein Spielsteine auf dem Spielfeld gelegt
      Und Es wurde kein Spielsteine aus der Beutel gezogen
      Wenn die Anzahl der Spielsteine abgefragt wird
      Dann sind im Beutel <Spielsteine im Beutel> Steine


    Szenario:Peter möchte nachzählen, wie viele Steine gezogen wurde, bevor das erste Stein angelegt wird
    Testfall:Durch (Nach)ziehen wird vor Spielstart <Anzahl der Spieler>-mal abwechselnd zufällig ein Stein
    aus dem Beutel gezogen, beginnend bei Spieler1.
      Angenommen das Spiel ist mit <Spieler> Spielern gestartet
      Und Es wurde kein Spielsteine auf dem Spielfeld gelegt
      Und jeder Spieler hat 2 Steine in der Hand
      Wenn die Anzahl der Spielsteine in der Hand abgefragt wird
      Dann wurden <Anzahl der Spielsteine in der Hand> gezogen

    Szenario: Peter möchte wie viele Verbindung hat ein Stein
    Testfall:
      Angenommen es sind <Züge> Züge durchgeführt worden
      Wenn die Anzahl der Verbindungen von einem <beliebigem Stein> abgefragt wird
      Dann hat der <beliebeiger Stein>  <Anzahl der Verbindungen> Verbindungen