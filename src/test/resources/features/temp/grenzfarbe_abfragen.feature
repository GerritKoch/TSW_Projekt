# language: de
Funktionalität: Das Plugin muss der GUI die Möglichkeit geben abzufragen ob ein Frosch auf einem Feld platziert werden kann

  Szenario: Gerrit möchte überprüfen ob er Frosch der eigenen Farbe auf ein Feld legen kann
    Angenommen es gibt mindestens 1 Frosch auf dem Spielfeld
    Und Feld 1,1 hat keinen Frosch
    Und Feld 1,1 hat eine Grenze
    Und Feld 1,1 hat keine blaue Grenze
    Wenn Spieler Blau blauen Frosch auf Feld 1,1 anlegen möchte
    Dann ist das Anlegen möglich

  Szenario: Gerrit möchte überprüfen ob er Frosch der eigenen Farbe auf ein Feld legen kann
    Angenommen es gibt mindestens 1 Frosch auf dem Spielfeld
    Und Feld 1,1 hat keinen Frosch
    Und Feld 1,1 hat eine Grenze
    Und Feld 1,1 hat eine blaue Grenze
    Wenn Spieler Blau blauen Frosch auf Feld 1,1 anlegen möchte
    Dann ist das Anlegen nicht möglich
    Und es wird eine Fehlermeldung "gleichfarbige Grenze" ausgegeben