Feature: Vinkin lisäämisen yhteydessä käyttäjä voi lisätä useita tageja, tyhjä merkkijono lopettaa.

  Scenario: Kirjan lisäys tageilla toimii
    Given Komento lisää kirja valitaan
    When Kirjoittaja "Kirjailija", kirjan nimi "Kirja" ja seuraavat tagit annetaan:
      | tagi1 | tagi2 |
    Then Sovellus vastaa "Vinkki lisätty!"

  Scenario: Videon lisäys tageilla toimii
    Given Komento lisää video valitaan
    When Nimi "Video", url "Url" annetaan ja  seuraavat tagit annetaan:
      | tagi1 | tagi2 |
    Then Sovellus vastaa "Vinkki lisätty!"

  Scenario: Podcastin lisäys tageilla toimii
    Given Komento lisää podcast valitaan
    When Nimi "Podcast", url "Url" annetaan ja  seuraavat tagit annetaan:
      | tagi1 | tagi2 |
    Then Sovellus vastaa "Vinkki lisätty!"

  Scenario: Podcastin lisäys tageilla toimii
    Given Komento lisää blogi valitaan
    When Nimi "Blogi", url "Url" annetaan ja  seuraavat tagit annetaan:
      | tagi1 | tagi2 |
    Then Sovellus vastaa "Vinkki lisätty!"
