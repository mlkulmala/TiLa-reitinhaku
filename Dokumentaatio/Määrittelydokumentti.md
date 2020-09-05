# Määrittelydokumentti

## Aihe ja käytetyt tietorakenteet
Tarkoituksena on suorittaa reitinhakualgoritmien vertailu. Reitinhaussa 
verrataan A* ja Fringe Search -algoritmeja. Tietorakenteista käytetään 
ainakin listaa (ArrayList) ja jonoa (PriorityQueue).

Tarkoituksena on löytää lyhin reitti verkossa olevien solmujen välillä. 
Ohjelma saa syötteekseen lähtöpisteen ja päätepisteen, jolloin ohjelma hakee lyhimmän reitin vertailtavia algoritmeja käyttäen. Solmut sijaitsevat joko karttapohjalla tai ruudukossa, lopullinen toteutustapa on vielä päättämättä.

## Aika- ja tilavaativuudet
A* algoritmin aikavaativuus on samaa luokkaa kuuin Dijkstran, O((|E|+|V|)log|V|), missä |V| on verkon solmujen määrä ja |E| verkon kaarien määrä. (Fringe Searchin pitäisi olla nopeampi, mutta en ole vielä tarkemmin tutustunut ko. algoritmiin.)

## Muuta
Työ toteutetaan Javalla ja dokumentointikielenä on suomi. Tekijä kuuluu tietojenkäsittelytieteen kandiohjelmaan.

## Käytetyt lähteet
Antti Laaksonen: Tietorakenteet ja algoritmit, luentomoniste, 22.8.2019. Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein: Introduction to Algorithms, Third Edition.

