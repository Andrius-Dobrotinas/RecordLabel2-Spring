insert into mediatypes (text) values ('LP');
insert into mediatypes (text) values ('CD');
insert into mediatypes (text) values ('FLAC');
insert into mediatypes (text) values ('AAC');

insert into metadata (text, type) values ('heavy metal', 1);
insert into metadata (text, type) values ('great', 0);
insert into metadata (text, type) values ('rock', 1);
insert into metadata (text, type) values ('artpop', 0);

insert into contentbase (id) values (1), (2), (3);
insert into artists (id, name, text) values (1, 'The Stooges', 'The greatest rock and roll band on earth?');
insert into artists (id, name, text) values (2, 'Iggy & The Stooges', 'Iggy and half of The Stooges plus one other guy');
insert into artists (id, name, text) values (3, 'Metallica', 'Nothing else matters!');

insert into contentbase (id) values (4), (5), (6), (7), (8);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (4, "EKS74051", 1969, 38, 0, "The Stooges", 1, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (5, "EKS74071", 1970, 40, 1, "Fun House", 1, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (6, "KC32111", 1973, 41, 0, "Raw Power", 2, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (7, "60439-1", 1986, 46, 0, "Master Of Puppets", 3, 1);
insert into releases (id, catalogueNumber, date, length, printStatus, title, artist_id, media_id) values (8, "UICY-20224", 2011, 46, 1, "Master Of Puppets", 3, 2);

insert into content_references (target, type, owner_id, item_order) values ('https://www.youtube.com/v/BJIqnXTqg8I', 2, 4, 0);
insert into content_references (target, type, owner_id, item_order) values ('https://www.youtube.com/v/JFAcOnhcpGA', 2, 4, 0);

insert into tracks (title, release_id, reference) values ('I Wanna Be Your Dog', 4, 'https://www.youtube.com/watch?v=BJIqnXTqg8I');
insert into tracks (title, release_id, reference) values ('No Fun', 4, 'https://www.youtube.com/watch?v=EDNzQ3CXspU');
insert into tracks (title, release_id) values ('1969', 4);

insert into contentbase_metadata (targets_id, metadata_id) values (4, 2), (4, 3);

insert into accounts (username, password) values ("asd", "pwd");