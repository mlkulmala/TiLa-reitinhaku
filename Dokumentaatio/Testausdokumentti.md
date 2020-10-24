# Testausdokumentti

## Yksikkötestit

Omien tietorakenteiden toiminta on testattu yksikkötesteillä, jotka voi suorittaa komennolla 
```
mvn test
```
Testikattavuusraportti luodaan komennolla
```
mvn jacoco:report
```
Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto *target/site/jacoco/index.html.*

### Testauskattavuus

Käyttöliittymäluokalle ei ole tehty yksikkötestejä. Testit jäivät puuttumaan myös algoritmeilta. 
Toisaalta niiden toimintaa on testattu manuaalisesti erilaisilla syötteillä matkan varrella.

<img src="https://raw.githubusercontent.com/mlkulmala/TiLa-reitinhaku/master/Dokumentaatio/kuvat/yksikkotestit.PNG" width="800">


