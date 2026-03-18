# App Gestionale Basi di Dati

Repository dell'applicazione sviluppata per il corso di **Basi di Dati**.

> Consultare la *Relazione di Progetto* per una panoramica completa dell'architettura e delle funzionalità implementate.

## Tecnologie

- **Java** `JDK 21`
- **Build System**: Gradle
- **Database**: MySQL (via Docker/VPS)
- **GUI**: Java Swing

## Prerequisiti

- Java JDK 21 installato
- Server MySQL in esecuzione (locale o remoto)

## Setup

### 1. Configurazione Database

Modificare i parametri di connessione in:
```
app/src/main/java/org/example/database/DatabaseConnection.java
```

### 2. Creazione Schema

Eseguire lo script di creazione del database:
```bash
mysql -u <user> -p < database-progetto-2732.sql
```

### 3. Importazione Dati

Caricare i dati di test (inclusi media):
```bash
mysql -u <user> -p < database-dump-2732.sql
```

## Esecuzione

### Via Gradle
```bash
./gradlew run
```

### Via JAR
```bash
./gradlew shadowJar
java -jar app/build/libs/app-all.jar
```

## Credenziali di Accesso

| Utente  | Password |
|---------|----------|
| admin   | admin    |

> Sono disponibili altri utenti di test per verificare le diverse funzionalità dell'applicativo.

## Struttura del Progetto

```
app/
├── src/main/java/org/example/
│   ├── database/      # Connessione e query al database
│   ├── model/         # Entità e oggetti di dominio
│   ├── view/          # Interfaccia grafica Swing
│   └── controller/    # Logica di business
└── build.gradle
```

## Note

Lo sviluppo ha previsto l'utilizzo di un server MySQL remoto gestito via Docker su VPS. Per l'esecuzione locale è necessario configurare un'istanza MySQL seguendo i passaggi descritti nella sezione *Setup*.
