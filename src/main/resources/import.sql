insert into content (id) values (1);
insert into content (id) values (2);
insert into artists (id, name, text) values (1, 'The Stooges', 'The greatest rock and roll band on earth?');
insert into artists (id, name, text) values (2, 'Iggy & The Stooges', 'Iggy and half of The Stooges plus one other guy');

insert into mediatypes (text) values ('LP');
insert into mediatypes (text) values ('CD');
insert into mediatypes (text) values ('FLAC');
insert into mediatypes (text) values ('AAC');

insert into metadata (text, type) values ('heavy metal', 1);
insert into metadata (text, type) values ('great', 0);
insert into metadata (text, type) values ('rock', 1);
insert into metadata (text, type) values ('artpop', 0);