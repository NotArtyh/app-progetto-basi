# App Basi di dati

The following repository contains the application developed for the **Basi di dati** course.

To better understand the content of the project we suggest reading the *Project Thesis* beforehand.

The application has been developer with the `jdk 21` in mind, support for different versions hasn't been tested. `gradle` is used for the build system
further instructions on how to run / build the application.

## How to run
Before running the application ensure to have installed the java jdk.

In order to run the application two options are provided:
- using the built `app-all.jar`
- using the gradle wrapper `./gradlew run`

## Come utilizzare l'app

Per eseguire l'applicativo in maniera corretta:

1. Nel app/src/main/java/org/example/database/DatabaseConnection.java cambiare url

2. buildare il database tramite il file "database-progetto-2732.sql"

3. importare i dati del file "database-dump-2732.sql" (necessario per caricare i media registrati)

4. via ./gradlew run è possibile avviare l'app nell rispettive ambiente di sviluppo
oppure è possibile generare un app-all via ./gradlew shadowJar è poi reperire il jar eseguibile
in app/build/libs/app-all.jar

5. Accedere con le credenziali 

Username: admin
Password: admin

(possibile accedere anche con altri users per verificare la correttezza dell'applicativo)

*Note*
Questi step sono necessari per eseguire l'applicazione con un server mysql in locale
siccome in fase di sviluppo e come specificato nella relazione, il server è stato gestito
da remote mediante docker container su un server privato gestendo gli accessi per noi sviluppatori
via una vps.