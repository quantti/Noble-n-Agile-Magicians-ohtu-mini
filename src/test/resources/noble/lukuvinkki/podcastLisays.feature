    Feature: Käyttäjä voi lisätä podcastin (nimen ja urlin)

        Scenario: Lisää nimellä ja urlilla
        Given Komento lisää podcast valitaan
        When  Nimi "Podcast" ja url "Url" annetaan
        Then Sovellus vastaa "Vinkki lisätty!"

        Scenario: Podcastia ei voi lisätä pelkällä nimellä
        Given Komento lisää podcast valitaan
        When  Nimi "Podcast" ja url "" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."

        Scenario: Podcastia ei voi lisätä pelkällä urlilla
        Given Komento lisää podcast valitaan
        When  Nimi "" ja url "Url" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."

        Scenario: Tyhjää podcastia ei voi lisätä
        Given Komento lisää podcast valitaan
        When  Nimi "" ja url "" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."
