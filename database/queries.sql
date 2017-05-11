
//media invoegen.
INSERT INTO Media ('name', 'URL') VALUES ('/*Media_naam*/', '/*Media_URL*/')

//Media verwijderen 

DELETE FROM Media 
WHERE URL = /*gekozen foto*/

//Media aan/uitzetten

//uitzetten
UPDATE Media 
SET active = ‘false’
WHERE URL = /*gekozen foto*/

//aanzetten
UPDATE Media 
SET active = ‘true’
WHERE URL = /*gekozen foto*/

//Thema toevoegen

INSERT INTO Thema ('name') VALUES ('" + ThemeName + "')

//Thema aan/uitzetten

//uitzetten
UPDATE Thema 
SET on/off = ‘false’
WHERE id = /*gekozen thema*/

//aanzetten
UPDATE Thema 
SET on/off = ‘true’
WHERE id = /*gekozen thema*/

//Thema verwijderen

DELETE FROM Thema
WHERE id = /*gekozen foto*/

//Media in thema

INSERT INTO theme_has_media VALUES (‘/*gekozen thema id*/’,’/*gekozen foto id*/’)

//Soundeffect toevoegen aan foto

INSERT INTO photo VALUES (‘/*gekozen foto id*/’,’/*gekozen soundeffect id*/’)
