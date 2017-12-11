Feature: Kun käyttäjä listaa vinkin tyypin perusteella, vain valitun tyyppiset vinkit tulostuvat

  Scenario: Kirjavinkit listataan
    Given Komento listaa valitaan
    When Valitaan listattavaksi kirjat
    Then Vain kirjat näytetään

  Scenario: Podcastvinkit listataan
    Given Komento listaa valitaan
    When Valitaan listattavaksi podcastit
    Then Vain podcastit näytetään
