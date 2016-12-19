insert into mediatypes (text) values ('LP');
insert into mediatypes (text) values ('CD');
insert into mediatypes (text) values ('FLAC');
insert into mediatypes (text) values ('AAC');

insert into metadata (text, type) values ('heavy metal', 1);
insert into metadata (text, type) values ('great', 0);
insert into metadata (text, type) values ('rock', 1);
insert into metadata (text, type) values ('artpop', 0);

insert into content (id) values (1), (2), (3);
insert into artists (id, name, text) values (1, 'The Stooges', 'The greatest rock and roll band on earth?');
insert into artists (id, name, text) values (2, 'Iggy & The Stooges', 'Iggy and half of The Stooges plus one other guy');
insert into artists (id, name, text) values (3, 'Metallica', 'Nothing else matters!');

insert into content (id) values (4), (5), (6), (7), (8);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (4, "EKS74051", 1969, 38, 0, "The Stooges", 1, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (5, "EKS74071", 1970, 40, 1, "The Stooges", 1, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (6, "KC32111", 1973, 41, 0, "Raw Power", 2, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (7, "60439-1", 1986, 46, 0, "Master Of Puppets", 3, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (8, "UICY-20224", 2011, 46, 1, "Master Of Puppets", 3, 2);