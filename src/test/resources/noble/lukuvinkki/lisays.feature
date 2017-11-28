    Feature: Käyttäjä voi lisätä kirjan (nimen ja kirjoittajan)

        Scenario: Lisää kirja nimellä ja kirjoittajalla
        Given Komento lisää valitaan
        When Kirjan nimi "Kirja" ja kirjoittaja "Kirjailija" annetaan
        Then Sovellus vastaa "Lisätty"