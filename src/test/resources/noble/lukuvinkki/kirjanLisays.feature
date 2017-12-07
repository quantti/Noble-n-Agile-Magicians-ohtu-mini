    Feature: Käyttäjä voi lisätä kirjan (nimen ja kirjoittajan)

        Scenario: Lisää kirja nimellä ja kirjoittajalla
        Given Komento lisää kirja valitaan
        When  Kirjoittaja "Kirjailija" ja kirjan nimi "Kirja" annetaan
        Then Sovellus vastaa "Vinkki lisätty!"

        Scenario: Kirjaa ei voi lisätä pelkällä nimellä
        Given Komento lisää kirja valitaan
        When Kirjoittaja "" ja kirjan nimi "Kirja" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."

        Scenario: Kirjaa ei voi lisätä pelkällä kirjoittajalla
        Given Komento lisää kirja valitaan
        When Kirjoittaja "Kirjailija" ja kirjan nimi "" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."

        Scenario: Tyhjää kirjaa ei voi lisätä
        Given Komento lisää kirja valitaan
        When Kirjoittaja "" ja kirjan nimi "" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."