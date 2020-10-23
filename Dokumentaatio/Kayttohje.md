# Käyttöohje


## Reitinhaun käyttö

<img src="https://raw.githubusercontent.com/mlkulmala/TiLa-reitinhaku/master/Dokumentaatio/kuvat/aloitusnakyma.png" width="700">

Ohjelma avautuu graafiseen käyttöliittymään, jossa on kartta. Lyhimmän reitin hakua voi kokeilla
joko klikkaamalla alku- ja päätepisteen kartalta tai syöttämällä niiden koordinaatit 
tekstikenttiin. Koordinaatit annetaan kokonaislukuina. Haluttu hakualgoritmi (Dijkstra, A* tai Fringe Search)
valitaan nappia painamalla, ja haku käynnistyy Hae-nappia painamalla. Algoritmin löytämä lyhin reitti 
piirtyy kartalle punaisena. Karttaan värittyvät myös kaikki ne ruudut, joissa algoritmi käy reitinhaun aikana.
Reitinhakuun kulunut aika tulee näkyviin algoritmin valintanappien alle.

<img src="https://raw.githubusercontent.com/mlkulmala/TiLa-reitinhaku/master/Dokumentaatio/kuvat/haettu_reitti.png" width="700">

Jos valittu alku- tai päätepiste osuu kartta-alueen ulkopuolelle tai rakennuksen sisään, ohjelma antaa
virheilmoituksen. Ohjelma ilmoittaa myös, jos reittiä ei löydy (esimerkiksi jos jompikumpi pisteistä
sijaitsee suljetussa korttelissa).

Nappi 'Tyhjennä kentät' poistaa jo piirretyn reitin ja tyhjentää tekstikentät.
