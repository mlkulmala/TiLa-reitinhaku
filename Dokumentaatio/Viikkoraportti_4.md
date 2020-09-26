# Viikko 4

## Mitä olen tehnyt
Olen edistänyt käyttöliittymää niin pitkälle, että reitin alku- ja päätepisteet voi nyt hakea
sekä kartalta että syöttää tekstikenttiin koordinaatteina. Myös kartalta valittaessa koordinaatit 
tulevat näkyviin tekstikenttiin. Reitinhaku käynnistyy Hae-nappulaa painamalla. Oletusarvona on nyt
Dijkstran algoritmi eli hakualgoritmin valinta ei vielä toimi, koska erilaisia hakualgoritmeja
ei ole vielä luotu.

Ohjelma myös tarkistaa ovatko annetut koordinaatit oikeassa muodossa (eli positiivisia kokonaislukuja),
ovatko annetut pisteet kartalla, ovatko ne kuljettavissa (eivätkä esimerkiksi rakennuksen
tai seinän sisällä) ja antaa virheilmoituksen, jos koordinaatit ovat virheellisiä tai jokin niistä puuttuu.
Jos annettua reittiä ei löydy esimerkiksi tapauksessa, jossa toinen pisteistä sijaitsee suljetulla pihalla, 
ohjelma ilmoittaa, ettei reittiä löydy.

Kun haettu reitti on löytynyt, se piirtyy punaisena kartalle.

Tyhjennä kentät -nappia painamalla tekstikentät tyhjennetään ja vanha reitti poistetaan kartalta.


## Mikä on jäänyt epäselväksi tai tuottanut vaikeuksia
En ole vielä ymmärtänyt täysin Fringe Searchin toimintaa. Ymmärrän jotakuinkin periaatteen, mutta en pseudokoodia, joten mietin,
voisinko lähteä toteuttamaan sitä omalla tavallani (eri tietorakenteita käyttäen). Mietin myös, kuinka paljon eri hakualgoritmit eroavat toisistaan,
ja pystynkö yhdistämään niiden toimintaa osittain samoihin metodeihin ja samoja tietorakenteita käyttäen.

## Mitä seuraavaksi
Seuraavana on hakualgoritmien valmiiksi saattaminen ja valintamahdollisuuden lisääminen myös käyttöliittymään.
Sen jälkeen aloitan yksikkötestien kirjoittamisen.

Viikon työtunnit:
16
