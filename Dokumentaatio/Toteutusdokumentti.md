# Toteutusdokumentti

## Ohjelman rakenne

Ohjelman pakkausrakenne on seuraava:

- `tiralabra.reitinhaku.ui`: JavaFX:llä toteutettu käyttöliittymä
- `tiralabra.reitinhaku.verkko`: kartta ja sen käsittelyssä tarvittavat komponentit
- `tiralabra.reitinhaku.tietorakenteet`: itse toteutetut tietorakenteet
- `tiralabra.reitinhaku.algoritmit`: vertailussa käytetyt hakualgoritmit

## Saavutetut aika- ja tilavaativuudet

#### Aikavaativuudet

Kunkin algoritmin alustus toimii ajassa O(|V|), koska kaikki solmut käydään kerran läpi.

Reitinhaku Dijkstran algoritmilla toimii ajassa O(|E|+|V|log|V|). Algoritmia toistetaan enimmillään yhtä monta kertaa kuin solmuja on. 
Jokaista käytyä solmua kohden algoritmi käy naapurisolmut läpi (kaarien määrä). Jokainen käsiteltävä solmu kulkee
keon kautta, joka tarkistaa kekoehdon jokaisen solmun lisäyksen jälkeen. Koska keko on rakenteeltaan binääripuu, 
tähän kuluu aikaa enintään log(|V|). Samoin solmujen poistamiseen keosta kuluu aikaa log(|V|). Kokonaisaikavaativuus 
on siis O(|E|+|V|log|V|).

A*:n aikavaativuus on O(|E|) (Wikipedia).


#### Tilavaativuudet

Dijkstran ja A* algoritmien tilavaativuus on O(|V|).


## Suorituskyky- ja O-analyysivertailut

Toteuttamistani algoritmeista A* löytää reitin nopeimmin. A* ja Dijkstra ovat käytännössä sama algoritmi sillä 
erolla, että A* käyttää heuristista funktiota, jonka mukaan solmuja valitaan keosta. Dijkstra hakee reittiä tasaisesti
joka suunnasta, kun A* poimii keosta ensin etäisyysarvion perusteella ne solmut, jotka ovat samassa suunnassa kuin reitin
päätepiste.

Fringe Search -algoritmia en saanut toimimaan tehokkaasti. Reitti löytyy, mutta aikaero kahteen muuhun algoritmiin
kasvaa sitä suuremmaksi, mitä pidempi ja monimutkaisempi reitti on. En ole siis varma, olenko ymmärtänyt alkuunkaan
Fringe Searchin toimintaperiaatetta.
En tiedä kuinka paljon asiaan vaikuttaa, että Fringe Search käyttää pääasiallisena tietorakenteenaan linkitettyä listaa, 
joka ei Tietorakenteet ja algoritmit -luentomonisteen mukaan ole juuri missään käytössä tehokkain ratkaisu johtuen 
nykyaikaisten tietokoneiden tavasta tallentaa tietoa. Fringe Searchin toteuttamista olisi voinut yrittää jollain muulla 
tietorakenteella, mutta kaikki aikani kului yrityksiin koodata toimiva algoritmi pseudokoodin mukaan.

### Tietorakenteet

#### MinimiKeko

MimimiKeko on rakenteeltaan binäärikeko, jossa pienimmän alkion haku vie aikaa O(1) (pelkkä haku ei ole käytössä koodissa). 
Alkion lisääminen tai poistaminen vie aikaa O(log n), koska lisäyksen tai poiston jälkeen pitää varmistaa kekoehdon 
voimassaolo ja siirtää mahdollisesti alkioita alimmalta tasolta juureen. Tasoja on yhteensä log(n).

#### Lista

Luokka lista on toteutettu taulukkolistana. Käytetyimmät toiminnot, kuten alkion lisääminen ja indeksistä hakeminen vievät 
aikaa vain O(1). Metodi `kaannaJarjestys` vaatii aikaa O(n), mutta se tehdään vain kerran kunkin reitinhaun yhteydessä.

#### Linkitetty lista

Linkitetty lista muodostuu solmuista, joista on viittaus edelliseen ja seuraavaan solmuun. Listan keskellä olevan solmun 
poistaminen on saatu tehokkaaksi käyttämällä aputaulukkoa, jossa on kunkin solmun viittauksen edeltäjään ja seuraajaan, 
joten kaikki käytetyt toiminnot toimivat ajassa O(1).


## Työn mahdolliset puutteet

Suurin puute lienee Fringe Searchin hitaus. Koska kaikki aika meni Fringe Searchin parissa, jäi muu koodi vähemmälle huomiolle.
Koodi olisi varmasti kannattanut jakaa useampiin luokkiin, mutta aika loppui lopussa kesken. Kartan käsittely olisi kannattanut
sijoittaa omaan luokkaansa. Myös jokin Math-luokan metodi jäi toteuttamatta omana versiona. 

Ohjelmaan jäi myös muutamia bugeja. Esimerkiksi valittaessa alku- ja päätepisteen kartalta pisteitä piirtyy useampia, vaikka 
tekstikenttien koordinaatit vaihtuvat oikein.

Algoritmeilta puuttuu yksikkötestit.

En myöskään onnistunut generoimaan jar-pakettia enkä selvittämään, mistä tämä johtuu.

## Lähteet
Kartta [Moving AI Lab](https://www.movingai.com/benchmarks/street/index.html)

https://en.wikipedia.org/wiki/A*_search_algorithm

https://en.wikipedia.org/wiki/Fringe_search

Y. Björnsson, M. Enzenberger, R.C. Holte and J. Schaeffer: Fringe Search: Beating A* at Pathfinding on Game Maps. 
https://webdocs.cs.ualberta.ca/~holte/Publications/fringe.pdf

Antti Laaksonen: Tietorakenteet ja algoritmit, luentomoniste, 22.8.2019.

Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein: Introduction to Algorithms, Third Edition.





