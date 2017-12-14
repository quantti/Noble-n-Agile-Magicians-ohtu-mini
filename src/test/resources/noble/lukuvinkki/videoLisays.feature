    Feature: Käyttäjä voi lisätä videon (nimen ja urlin)

        Scenario: Lisää nimellä ja urlilla
        Given Komento lisää video valitaan
        When  Nimi "Video" ja url "Url" annetaan
        Then Sovellus vastaa "Vinkki lisätty!"

        Scenario: Videoa ei voi lisätä pelkällä nimellä
        Given Komento lisää video valitaan
        When  Nimi "Video" ja url "" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."

        Scenario: Videoa ei voi lisätä pelkällä urlilla
        Given Komento lisää video valitaan
        When  Nimi "" ja url "Url" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."

        Scenario: Tyhjää videoa ei voi lisätä
        Given Komento lisää video valitaan
        When  Nimi "" ja url "" annetaan
        Then Sovellus vastaa "Vinkin lisääminen epäonnistui."
