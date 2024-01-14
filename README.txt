# COMPILAZIONE
Per compilare il software è necessario avere installato sulla macchina la versione della JDK 17 o superiore, inoltre è necessaria una connessione internet.
Per effettuare la compilazione si utilizza Maven, si può averlo installato sulla macchina o in alternativa utilizzare il wrapper:

Build con Maven installato
>> mvn clean package
Build senza Maven installato, con wrapper
>> ./mvnw clean package

Questo genera un file .jar eseguibile nella cartella target e una folder libs/ con all'interno le dipendenze già referenziate dal .jar

P.S. Se si vuole spostare il .jar bisogna necessariamente spostare anche la cartella libs.

# ESECUZIONE

Per eseguire l'applicazione è necessario una JRE 17 o superiore, una volta individuato il .jar con la certella libs/ è necessario avviarlo con il comando

>> java -jar cartella-sanitaria-0.0.1-SNAPSHOT.jar

P.S. Per caricare i dati di sessione è necessario posizionare nella stessa crtella del .jar il file saves.json
