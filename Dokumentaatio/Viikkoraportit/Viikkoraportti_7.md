# Viikko 7

## Mitä olen tehnyt
Jatkoin vielä FringeSearchin työstämistä. Sain reitinhaun toimimaan niin, että se toimii yksinkertaisilla 
hauilla, mutta vähänkään pidemmillä väleillä se toimii aivan liian hitaasti. Luultavasti vika on solmujen 
poistamisessa listalta, mutta en tiedä tarkalleen, mitä pitäisi korjata. Olen siis tavallaan edelleen jumissa 
koko algoritmin kanssa. Nykyistä versiota ei voi pitää toimivana.
 
Olen ottanut omat tietorakenteet käyttöön kaikkialla koodissa. Löysin vielä virheitä
MinimiKeosta niin koodista kuin testeistä ja ne on nyt korjattu. Omat listat ja keot saattavat sisältää metodeja
joita ei käytetä, mutta annan niiden olla ainakin toistaiseksi.

Käyttöliittymää on paranneltu niin, että klikatessa alku- ja loppupisteen paikat kartalta 
ne näkyvät myös punaisina pisteinä. Myös algoritmin valinta toimii nyt. Reitinhaun kesto 
(millisekunteina) tulee nyt näkyviin. Hakualgoritmin läpikäymät ruudut värittyvät myös karttaan, mikä
tekee algoritmien vertailusta käyttäjälle mielenkiintoisempaa.

Kirjoitin Javadoc-kommentit luokille ja metodeille, sekä korjasin checkstylevirheitä. Hakualgoritmit ovat 
pitkiä, mutta tuntuu liian riskaabelilta lähteä hajottamaan niitä osiin.

Olen myös jakanut koodia useampiin luokkiin. Hakualgoritmeista Dijkstra ja AStar sijaitsevat nyt samassa
luokassa ja jopa samassa metodissa, koska ne poikkeavat toisistaan vain parin rivin verran ja käyttävät tismalleen
samoja tietorakenteita.


## Mikä on jäänyt epäselväksi tai tuottanut vaikeuksia
Fringe Search. 

## Mitä seuraavaksi
Kaikki se mitä tekemättä on. Testejä ainakin puuttuu vielä. Dokumentointi on tosi vajavaista. Koodin siistimistä
ja jakamista useampiin metodeihin pitää vielä jatkaa.


Viikon työtunnit: 20
