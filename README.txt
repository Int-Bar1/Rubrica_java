README - Rubrica Telefonica (Java + MySQL)

Descrizione
Applicazione Java Swing che gestisce una rubrica telefonica con salvataggio dati su database MySQL.
Permette di aggiungere, modificare ed eliminare contatti. Ogni contatto contiene:
- Nome
- Cognome
- Indirizzo
- Telefono
- Età

Include una finestra di login (Non lato database per ora, utente: admin/pass: admin).

Requisiti
- Java 11 o superiore (consigliato Java 17)
- MySQL 8.x installato e in esecuzione
- MySQL Connector/J (già incluso nel JAR)

Contenuto del pacchetto
- Rubrica.jar
- credenziali_database.properties
- schema_database.sql
- README.txt

Installazione
1. Installare MySQL se non presente.
2. Creare il database e le tabelle eseguendo lo script contenuto in "schema_database.sql";
3. Configurare le credenziali di connessione aprendo il file credenziali_database.properties:
   db.host=127.0.0.1
   db.port=3306
   db.name=rubrica
   db.user=root
   db.password=**MODIFICA_con_la_tua_password**
   Il file deve trovarsi nella stessa cartella del file Rubrica.jar.

Avvio
Aprire un terminale nella cartella del progetto ed eseguire:
   java -jar Rubrica.jar
Oppure, se il sistema è configurato correttamente, fare doppio clic su Rubrica.jar.

Login
Credenziali di accesso predefinite:
   Utente: admin
   Password: admin

Struttura del database
Database: rubrica

Tabella persone
id INT AUTO_INCREMENT PRIMARY KEY
nome VARCHAR(80) NOT NULL
cognome VARCHAR(80) NOT NULL
indirizzo VARCHAR(255)
telefono VARCHAR(40) UNIQUE
eta INT

Note
- Tutti i dati vengono salvati in modo permanente nel database MySQL.
- Per l’avvio su altri computer, copiare l’intera cartella, configurare MySQL ed aggiornare credenziali_database.properties.
- Se il database non esiste, eseguire schema_database.sql creando un databse su MySQL Workbench prima dell’avvio.
