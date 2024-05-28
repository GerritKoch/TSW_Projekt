# language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit geben abzufragen ob eine Aktion möglich ist oder übersprungen werden muss

  Szenario: Gerrit möchte Bewegen auf Erlaubtheit prüfen
    Angenommen Es gibt Frösche auf dem Feld
    Und blauer Spieler ist am Zug
    Und blauer Frosch ist auf Feld 1,1
    Wenn Bewegung des blauen Frosch auf Feld 1,1 geprüft wird
    Dann Feld 1,1 kann geklickt werden

  Szenario: Gerrit möchte Anlegen auf Erlaubtheit prüfen
    Angenommen Spieler 1 hat gerade Bewegen beendet
    Und Spieler 1 hat 0 Frösche auf der Hand
    Wenn Anlegen geprüft wird
    Dann ist Anlegen nicht möglich
    Und Fehlermeldung wird angezeigt

  Szenario: Gerrit möchte Nachziehen auf Erlaubtheit prüfen
    Angenommen Spieler 1 hat gerade Anlegen beendet
    Und es sind 0 Frösche im Beutel
    Wenn Nachziehen geprüft wird
    Dann ist Nachziehen nicht möglich
    Und Nachziehen wird übersprungen
    Und Fehlermeldung wird angezeigt