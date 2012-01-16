# --- !Ups

ALTER TABLE coursetype DROP COLUMN level;
ALTER TABLE coursetype DROP COLUMN price;
ALTER TABLE coursetype ADD COLUMN pricehigh real;
ALTER TABLE coursetype ADD COLUMN pricelow real;


UPDATE coursetype SET pricehigh = 30, pricelow = 20 where id = 2;
UPDATE coursetype SET pricehigh = 37.5, pricelow = 25 where id = 3;

INSERT INTO coursetype (id, name, pricehigh, pricelow) values (4, 'Khôlles seules en EUR TTC par heure', 20, 20);
INSERT INTO coursetype (id, name, pricehigh, pricelow) values (5, 'Accompagnement en formation à distance ou en tutorat sans TP noté ni mini projet en EUR TTC par heure', 20, 20);
INSERT INTO coursetype (id, name, pricehigh, pricelow) values (6, 'Khôlles seules en EUR TTC par heure', 25, 25);