
CREATE TABLE IF NOT EXISTS Objet (
Code_objet varchar(255) not null,
Nom_objet varchar(255),
FMutualisable boolean,
Version varchar(255) not null,
Code_type varchar(255),
primary key (Code_objet),
foreign key (Code_type) references TypesObjets (Code_type)
);


CREATE TABLE IF NOT EXISTS TypesObjets (
	Code_type varchar(255) not null,
	Nom_typeObjet varchar(255) not null,
	Code_Regex varchar(255) not null,
	ContentModel varchar(255) not null,
	Erreur_descritpion varchar(255) not null,
	primary key (Code_type)
);


CREATE TABLE IF NOT EXISTS Descripteur_champs (
Id varchar(255) not null,
Nom_champ varchar(255) not null,
Description_champ varchar(255) not null,
Type_contenu varchar(255) not null,
Code_type varchar(255),
primary key (Id),
foreign key (Code_type) references TypesObjets (Code_type)
);

CREATE TABLE IF NOT EXISTS Personne (

Login varchar(255) not null,
Password varchar(255) not null,
Nom varchar(255) not null,
Prenom varchar(255) not null,
Mail varchar(255) not null,
primary key (Login)
);

CREATE TABLE IF NOT EXISTS Formation (
Code_objet varchar(255) not null,
Error_number Integer,
Visible boolean,
primary key (Code_objet),
foreign key (Code_objet) references Objet (Code_objet)
);

CREATE TABLE IF NOT EXISTS Pere_fils (
Code_pere varchar(255) not null,
Code_fils varchar(255) not null,
primary key (Code_pere, Code_fils),
foreign key (Code_pere) references Objet (Code_objet),
foreign key (Code_fils) references Objet (Code_objet)
);

CREATE TABLE IF NOT EXISTS A_pour_champs (
Code_objet INTEGER not null,
Id_champ INTEGER not null,
Valeur varchar(255),
primary key (Code_objet, Id_champ),
foreign key (Code_objet) references Objet (Code_objet),
foreign key (Id_champ) references Descripteur_champs (Id_champ)
);

CREATE TABLE IF NOT EXISTS A_pour_contributeur (
Id_personne INTEGER not null,
Code_objet varchar(255) not null,
primary key (Code_objet),
foreign key (Code_objet) references Formation (Code_objet)
);


insert into Personne values('seddikLog','pass','ouiss','seddik','seddik@amu.fr') ;
insert into Objet values('111','FSI',false,'3.2',NULL) ;
insert into Formation values('111',11,true) ;
insert into TypesObjets values ('XX','XX','XX','XX','XX') ;
insert into Descripteur_champs values ('ZZ','ZZ','ZZ','STRING',NULL) ;
insert into Descripteur_champs values ('YY','YY','YY','INT',NULL) ;
