    Feature: Käyttäjä voi lisätä podcastin (nimen ja urlin)

        Scenario: Lisää nimellä ja urlilla
        Given Komento lisää podcast valitaan
        When  Nimi "Podcast" ja url "Url" annetaan
        Then Sovellus vastaa "Vinkki lisätty!"

#        Scenario: Kirjaa ei voi lisätä pelkällä nimellä
#        Given Komento lisää valitaan
#        When Kirjoittaja "" ja kirjan nimi "Kirja" annetaan
#        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."
#
#        Scenario: Kirjaa ei voi lisätä pelkällä kirjoittajalla
#        Given Komento lisää valitaan
#        When Kirjoittaja "Kirjailija" ja kirjan nimi "" annetaan
#        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."
#
#        Scenario: Tyhjää kirjaa ei voi lisätä
#        Given Komento lisää valitaan
#        When Kirjoittaja "" ja kirjan nimi "" annetaan
#        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."