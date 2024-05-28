#language: de


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
