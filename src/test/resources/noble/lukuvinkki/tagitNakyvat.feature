Feature: Käyttäjä näkee vinkkien tagit listauksessa.

    Scenario: Kayttaja listaa vinkit
    Given Komento listaa valitaan
    When Valitaan listattavaksi kaikki
    Then Vinkin kohdalla näytetään tagit