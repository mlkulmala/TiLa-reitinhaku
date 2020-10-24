# Määrittelydokumentti

## Aihe 

Aiheena on reitinhakualgoritmien vertailu. Tarkoituksena on toteuttaa ohjelma, joka löytää 
lyhin reitti verkossa olevien solmujen välillä. Ohjelma saa syötteekseen lähtöpisteen 
ja päätepisteen, jolloin ohjelma hakee lyhimmän reitin vertailtavia algoritmeja käyttäen. 

## Vertailussa käytetyt algoritmit 
Ohjelmassa käytetään Dijkstran, A* ja Fringe Search -algoritmeja. 

Dijkstran algoritmi hyödyntää minimikekoa ja tallentaa kekoon solmuja, joilla on tunnus (sijainti kartalla 
koordinaatein ilmaistuna) ja etäisyys, jonka mukaan solmut järjestetään pienimmästä suurimpaan. Kun keosta otetaan 
solmu, käydään läpi kaikki sen naapurisolmut, ja tarkastetaan, voidaanko etäisyyttä parantaa niiden avulla. Jos 
voidaan, lisätään uusi solmu uudella etäisyydellä kekoon.

A* on Dijkstran paranneltu versio, jossa käytetään apuna heuristiikkaa. Alkupisteen ja solmun välisen etäisyyden 
sijaan käytetään heuristista funktiota, jossa kuljettuun etäisyyteen lisätään päätepisteen ja solmun välinen euklidinen 
etäisyys, joka voidaan laskea koordinaattipisteiden avulla Pythagoraan lausetta käyttäen. A* algoritmissa solmut 
järjestetään kekoon heuristisen funktion mukaan, jolloin algoritmi käsittelee ensin ne naapurisolmut, jotka ovat 
maalipisteen suunnassa.

Fringe Search -algoritmi käyttää linkitettyä listaa. Siinä käsittelyjonossa olevia solmuja ei pidetä suuruusjärjestyksessä, 
jolloin säästetään keon järjestämisessä kuluva aika, mutta toisaalta Fringe Search käy samoja solmuja useampaan kertaan läpi.


## Ohjelmassa käytetyt tietorakenteet (suluissa Javan vastaava rakenne)
Tietorakenteista käytetään listaa (ArrayList), minimikekoa (PriorityQueue) ja linkitettyä listaa (LinkedList).



## Aika- ja tilavaativuudet

|V| on solmujen määrä, |E| kaarien määrä.

| Algoritmi | Aikavaativuus | Tilavaativuus |
| :--------:|:-------------:|:-------------:|
| Dijkstra  | O(|E|+|V|log|V|) | O(|V|)   |
| A*        | O(|E|)     | O(|V|) |
| Fringe Search   |       |  |



## Muuta

- ohjelmointikieli: Java
- projektin kieli: suomi
- koulutusohjelma: tietojenkäsittelytieteen kandiohjelma

## Käytetyt lähteet

[https://en.wikipedia.org/wiki/A*_search_algorithm]

[https://en.wikipedia.org/wiki/Fringe_search]

Y. Björnsson, M. Enzenberger, R.C. Holte and J. Schaeffer: Fringe Search: Beating A* at Pathfinding on Game Maps. 
[https://webdocs.cs.ualberta.ca/~holte/Publications/fringe.pdf]

Antti Laaksonen: Tietorakenteet ja algoritmit, luentomoniste, 22.8.2019.

Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein: Introduction to Algorithms, Third Edition.
