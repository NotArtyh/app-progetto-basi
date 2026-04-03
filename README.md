# 🎵 VinylVault — Music Collection Manager

> Applicazione sviluppata per il corso di **Basi di Dati** · Java · MySQL · Swing

VinylVault è un'applicazione desktop dedicata ai collezionisti di musica in formato fisico. Permette a ogni utente di costruire e gestire la propria libreria digitale di vinili, CD, cassette e altri supporti, con funzionalità di scambio tra utenti, recensioni, profili artisti e molto altro.

---

## 📋 Indice

- [Idea e Obiettivi](#-idea-e-obiettivi)
- [Tecnologie](#-tecnologie)
- [Architettura](#-architettura)
- [Funzionalità Implementate](#-funzionalità-implementate)
- [Prerequisiti](#-prerequisiti)
- [Setup e Configurazione](#-setup-e-configurazione)
- [Esecuzione](#-esecuzione)
- [Credenziali di Test](#-credenziali-di-test)
- [Struttura del Progetto](#-struttura-del-progetto)
- [Note Infrastrutturali](#-note-infrastrutturali)

---

## 💡 Idea e Obiettivi

L'obiettivo è offrire uno spazio digitale dove ogni collezionista possa:

- **Catalogare** la propria raccolta di media fisici (vinili, CD, cassette, ecc.)
- **Condividere** il proprio inventario con altri utenti della piattaforma
- **Scambiare** oggetti della collezione in modo tracciato e sicuro
- **Scoprire** nuovi contenuti grazie a recensioni, voti e commenti della community
- **Approfondire** ogni opera musicale nel dettaglio: brani, album, artisti, collaborazioni

L'applicazione è pensata sia per chi vuole partecipare attivamente agli scambi, sia per chi desidera semplicemente mantenere un catalogo digitale curato della propria collezione.

---

## 🛠 Tecnologie

| Componente | Tecnologia |
|---|---|
| Linguaggio | Java `JDK 21` |
| Build System | Gradle |
| Database | MySQL (via Docker / VPS) |
| Connettività DB | JDBC |
| GUI | Java Swing |
| Networking Team | Tailscale VPN |

---

## 🏗 Architettura

Il progetto adotta un'architettura **a layer** ispirata al pattern **MVC**, con una netta separazione delle responsabilità tra i componenti:

```
View  ──►  Controller  ──►  Service  ──►  DAO  ──►  MySQL
```

| Layer | Responsabilità |
|---|---|
| **Model** | Rappresentazione delle entità del dominio (`User`, `Inventory`, `Media`, `Trade`, ecc.) |
| **DAO** | Accesso al database tramite query SQL con `PreparedStatement` |
| **Service** | Logica applicativa; orchestra le operazioni tra Controller e DAO |
| **Controller** | Coordinamento tra View e Service; gestisce il flusso applicativo |
| **View** | Interfaccia grafica Swing; pannelli e componenti riutilizzabili |

### Pattern e scelte implementative

- **Singleton Pattern** — `SessionManager` per la gestione globale della sessione utente
- **Dependency Injection** — I controller ricevono i service come dipendenze
- **Connection Pooling** — `DatabaseConnection` per la gestione centralizzata delle connessioni
- **PreparedStatement** — Prevenzione di SQL injection su tutte le query
- **Transazioni** — Operazioni atomiche garantite su operazioni critiche (es. registrazione)
- **CardLayout** — Navigazione fluida tra i pannelli dell'interfaccia
- **Componenti riutilizzabili** — `StyledButton`, `UserBar`, `SquareImagePanel`, `CircularImagePanel`

---

## ✅ Funzionalità Implementate

### Gestione Utenti
- Registrazione con validazione email (regex) e campi obbligatori
- Login / Logout con gestione della sessione
- Profilo personale: nome, cognome, sesso, telefono, indirizzo
- Livello utente globale

### Inventario
- Ogni utente dispone di un **inventario personale**
- Aggiunta di media con: titolo, formato, foto, condizione, note, data di acquisizione
- Visibilità **pubblica o privata** dell'inventario
- Visualizzazione degli inventari degli altri utenti

### Sistema di Scambio (Trade)
- Invio e ricezione di richieste di scambio tra utenti
- Selezione degli item offerti e degli item richiesti
- Tracciamento dello stato (`Pending`, ecc.) e della data dello scambio

### Media e Contenuti Musicali
- Ogni media è legato a un oggetto `Media` (titolo, formato, copertina)
- Associazione di brani al media: titolo, durata, anno di pubblicazione
- Supporto ad album (nome, genere, anno) e singoli
- Scheda artista: nome d'arte, dati anagrafici, biografia
- Gestione collaborazioni tra artisti su singoli brani

> ℹ️ Alcune funzionalità secondarie (es. sistema amicizie, recensioni) sono state progettate ma non completamente implementate per ragioni di tempo.

---

## 📦 Prerequisiti

- **Java JDK 21** installato e configurato nel `PATH`
- **Server MySQL** in esecuzione (locale o remoto)
- Accesso alla rete VPN Tailscale (se si utilizza il server condiviso del team)

---

## ⚙️ Setup e Configurazione

### 1. Configurazione della connessione al database

Aprire il file di configurazione e aggiornare i parametri con le proprie credenziali:

```
app/src/main/java/org/example/database/DatabaseConnection.java
```

### 2. Creazione dello schema

Eseguire lo script DDL per creare la struttura del database:

```bash
mysql -u <user> -p < database-progetto-2732.sql
```

### 3. Importazione dei dati di test

Caricare il dump con i dati di esempio (utenti, media, inventari):

```bash
mysql -u <user> -p < database-dump-2732.sql
```

---

## ▶️ Esecuzione

### Tramite Gradle (sviluppo)

```bash
./gradlew run
```

### Tramite JAR (distribuzione)

```bash
# Compilazione del fat JAR
./gradlew shadowJar

# Esecuzione
java -jar app/build/libs/app-all.jar
```

---

## 🔑 Credenziali di Test

| Utente | Password |
|---|---|
| `admin` | `admin` |

> Sono disponibili ulteriori utenti di test nel dump per verificare le funzionalità multi-utente (inventari, scambi, ecc.).

---

## 📁 Struttura del Progetto

```
app/
└── src/
    └── main/
        └── java/
            └── org/example/
                ├── database/        # Connessione e gestione pool JDBC
                ├── model/           # Entità del dominio (User, Media, Trade, …)
                ├── dao/             # Data Access Object — query SQL
                ├── service/         # Logica applicativa
                │   ├── UserService
                │   ├── ItemService
                │   ├── InventoryService
                │   ├── TradeService
                │   └── UsersInventoryService
                ├── controller/      # Coordinamento View ↔ Service
                └── view/            # Pannelli e componenti Swing
build.gradle
database-progetto-2732.sql          # Script DDL
database-dump-2732.sql              # Dati di test
```

---

## 🌐 Note Infrastrutturali

Il progetto è stato sviluppato in team con il database MySQL ospitato su un **server privato in rete domestica**, containerizzato tramite **Docker**.

Per consentire l'accesso simultaneo da parte di più membri del team, è stato utilizzato **[Tailscale](https://tailscale.com/)** — un servizio VPN mesh che crea una rete privata tra dispositivi, permettendo di raggiungere il server remoto come se fosse in locale.

Per l'esecuzione in autonomia è sufficiente configurare una propria istanza MySQL seguendo i passaggi della sezione [Setup](#️-setup-e-configurazione).

---

> Per una panoramica completa dell'architettura, del modello ER e delle scelte progettuali, consultare la [Relazione di Progetto](report.pdf) allegata al repository.
