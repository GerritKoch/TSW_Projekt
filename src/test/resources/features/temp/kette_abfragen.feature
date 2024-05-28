# language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit geben abzufragen ob eine Kette entstehen wird

  Szenario: Gerrit möchte überprüfen ob sein Anlegen eine Kette erzeugt
    Angenommen es gibt mindestens 3 Frösche auf dem Spielfeld
    Und Feld 1,1 hat keinen Frosch
    Und Feld 1,1 hat mindestens 2 Grenzen
    Wenn abgefragt wird ob das Anlegen auf Feld 1,1 möglich ist
    Dann ist das Anlegen möglich

  Szenario: Gerrit möchte überprüfen ob sein Anlegen eine Kette erzeugt
    Angenommen es gibt mindestens 3 Frösche auf dem Spielfeld
    Und Feld 1,1 hat keinen Frosch
    Und Feld 1, 1 hat 1 Grenze zu Feld 1, 2
    Und Feld 1, 2 hat 2 Grenzen zu Feld 1, 1 und Feld 1, 3
    Und Feld 1, 3 hat 2 Grenzen zu Feld 1, 2 und Feld 1, 4
    Wenn abgefragt wird ob das Anlegen auf Feld 1,1 möglich ist
    Dann ist das Anlegen nicht möglich
    Und es wird eine Fehlermeldung "Kette" ausgegeben