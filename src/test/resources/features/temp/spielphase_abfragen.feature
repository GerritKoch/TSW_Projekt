# language: de
  Funktionalität: Das Plugin muss der GUI die Möglichkeit geben abzufragen welche Spielphase gerade aktiv ist.

    Szenario: Gerrit möchte die aktuelle Spielphase wissen
      Angenommen Spieler 1 hat gerade Nachziehen beendet
      Wenn die Spielphase abfragt wird
      Dann ist die Spielphase Bewegen
      Und nur Frösche auf dem Feld können geklickt werden

    Szenario: Gerrit möchte die aktuelle Spielphase wissen
      Angenommen Spieler 1 hat gerade Bewegen beendet
      Wenn die Spielphase abfragt wird
      Dann ist die Spielphase Anlegen
      Und nur Frösche auf der Hand können geklickt werden

    Szenario: Gerrit möchte die aktuelle Spielphase wissen
      Angenommen Spieler 1 hat gerade Anlegen beendet
      Wenn die Spielphase abfragt wird
      Dann ist die Spielphase Nachziehen
      Und nur der Nachziehen Button kann geklickt werden