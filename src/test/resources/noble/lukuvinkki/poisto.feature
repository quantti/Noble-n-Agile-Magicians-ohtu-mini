Feature: Käyttäjä voi poistaa vinkin

    Scenario: Käyttäjä poistaa vinkin
    Given Komento poista valitaan
    When Poistetaan vinkki id:llä "1"
    Then Sovellus vastaa "Vinkki poistettu"
    

    