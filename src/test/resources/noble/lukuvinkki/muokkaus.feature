Feature: Käyttäjä voi muokata vinkkiä
    
        Scenario: Valitaan muokkaa komento ja tallennetaan vinkki vanhoilla tiedoilla
        Given Komenta muokkaa valitaan
        When Muokataan vinkkiä id:llä "1"
        And Annetaan tyhjät kentät
        Then Sovellus vastaa "Vinkkiä muokattu onnistuneesti!"

        Scenario: Valitaan muokkaa komento ja tallennetaan vinkki uusilla tiedoilla
        Given Komenta muokkaa valitaan
        When Muokataan vinkkiä id:llä "1"
        And Annetaan uudeksi nimeksi "Uusi kirja" ja kirjoittajaksi "Uusi kirjailija"
        Then Sovellus vastaa "Vinkkiä muokattu onnistuneesti!"