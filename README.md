# 🎮 Clash Royale Card Battle API

This endterm project topic is REST API for managing Clash Royale cards and players using Spring Boot, POSTMAN, Design Patterns and Component principles.

=====================================================================

## Project Overview

A Spring Boot REST API that demonstrates:
- **Design Patterns**: Singleton, Factory, Builder
- **SOLID Principles**: SRP, OCP, LSP, ISP, DIP
- **Component Principles**: REP, CCP, CRP
- **Multi-layer Architecture**: Controller → Service → Repository → Database

**Technologies**: Java 17, Spring Boot 3.2.0, PostgreSQL, Maven

=====================================================================

## REST API Endpoints

### **Cards**

Method - Endpoint - Description

GET - `/api/cards` - Get all cards

GET - `/api/cards/{id}` - Get card by ID 

POST - `/api/cards` - Create new card 

PUT - `/api/cards/{id}` - Update card 

DELETE - `/api/cards/{id}` - Delete card 

PUT - `/api/cards/{id}/upgrade` - Upgrade card level 

GET - `/api/cards/type/{type}` - Get cards by type 

### **Players**

**Method - Endpoint - Description**

GET - `/api/players` - Get all players 

GET - `/api/players/{id}` - Get player by ID 

POST - `/api/players` - Create new player 

PUT - `/api/players/{id}` - Update player 

DELETE - `/api/players/{id}` - Delete player 

POST - `/api/players/{id}/trophies?amount=X` - Add trophies 

### **Sample Requests**

___**Create Warrior Card:**___
`
POST /api/cards
{
  "name": "Knight",
  "type": "WARRIOR",
  "rarity": "COMMON",
  "elixirCost": 3,
  "level": 14,
  "hp": 2339,
  "damage": 267
} `

___**Create Player:**___

`
POST /api/players
{
  "name": "ProGamer123",
  "level": 10,
  "trophies": 5000
}`

=====================================================================

## Design Patterns

### **1. Singleton Pattern**
   Classes: LoggerService, DatabaseConfig
   Purpose: Single instance for logging and configuration
   Benefit: Centralized resource management
### **2. Factory Pattern**
   Class: CardFactory
   Purpose: Create card subtypes (Warrior/Spell/Building) based on type
   Benefit: Encapsulates object creation, easy to extend
### **3. Builder Pattern**
   Classes: PlayerBuilder, DeckBuilder
   Purpose: Construct complex objects with optional parameters
   Benefit: Fluent API, readable code 

===============================================================================

## Component Principles
   ### REP (Reuse/Release Equivalence)
   repository, patterns, exception packages are reusable modules
   Each package can be versioned independently
   ### CCP (Common Closure Principle)
   Classes that change together are grouped:
   controller - changes when API changes
   service - changes when business rules change
   repository - changes when database changes
   ### CRP (Common Reuse Principle)
   Interfaces separated by concern:
   Validatable, Upgradable, Printable
   No forced dependencies on unused functionality

===============================================================================

##  SOLID Principles
   ### S - Single Responsibility
   Controller: HTTP handling only
   Service: Business logic only
   Repository: Database operations only
   ### O - Open/Closed
   Can add new card types without modifying existing code
   CrudRepository<T> works for any entity type
   ### L - Liskov Substitution
   All card subclasses (WarriorCard, SpellCard, BuildingCard) substitutable for Card
   Polymorphic behavior preserved
   ### I - Interface Segregation
   Focused interfaces: Validatable, Upgradable, Printable
   Classes implement only needed interfaces
   ### D - Dependency Inversion
   Controllers depend on service interfaces
   Services depend on repository interfaces
   Constructor injection via @Autowired 
   

===============================================================================
## Database Schema
   ### SQL

`---table players
create table players (
id serial primary key,
name varchar(50) unique not null,
level int default 1,
trophies int default 0
);`

`---таблица карт
create table cards (
id serial primary key,
name varchar (50) not null,
card_type varchar(20), --воин, заклинание, здание
rarity varchar (20), --обычная, редкая, эпик, легендарка
elixir_cost int  check (elixir_cost > 0 and elixir_cost <= 10),
level int default 1 check ( level > 0 and level <= 16),
damage int default 0,
hp int default 0,
radius int default 0,
lifetime int default 0
);`

`--- таблица колод
create table decks (
id serial primary key,
player_id int not null references players(id) on delete cascade,
deck_name varchar(50)
);`

`-- таблица для связи карт в колоде
create table deck_cards (
deck_id int not null references decks(id) on delete cascade,
card_id int not null references cards(id) on delete cascade,
position int not null check (position >= 1 and position <=8),
primary key (deck_id, card_id),
unique (deck_id, position)
);`

### **Relationships:**

Players <-> Decks: One-to-Many

Decks <-> Cards: Many-to-Many (using deck_cards)

===============================================================================

## System Architecture
┌───────┐  
│  Controller │ ---> REST endpoints, HTTP handling  
└───┬───┘   
depends on  
ㅤㅤㅤ↓   
┌─────┐   
│   Service    │ --->  Business logic, validation  
└──┬──┘   
depends on  
ㅤ ㅤ↓   
┌───────┐   
│  Repository  │  ---> Database operations (JDBC)  
└───┬───┘  
ㅤㅤuses  
ㅤㅤㅤ↓    
┌────────┐   
│  PostgreSQL  │   ---> Data storage  
└────────┘
### **Request Flow:**

1) Client -> HTTP Request -> Controller
2) Controller -> Service (business logic)
3) Service -> Repository (database)
4) Repository -> PostgreSQL
5) Response flows back

===============================================================================

## Installation & Setup
### **Prerequisites**
Java 17+
Maven 3.0+
PostgreSQL 14+

### Steps
1) **Copy code from repository in github:**
git clone https://github.com/hann-qaz/endterm_project.git

2) **Create database:**
CREATE DATABASE card_battle_db;

3) **Run schema:**
src/main/resources/schema.sql
4) **Configure database** in src/main/resources/application.properties:
url = jdbc:postgresql://localhost:5432/card_battle_db
username = postgres
password = password
5) **Install dependencies:**
mvn clean install

===============================================================================
## Running the Application

* **Open IntelliJ IDEA**
* **Right-click** CardBattleApiApplication.java
* **Select "Run"**

===============================================================================

## Testing with Postman
### Example Requests

* **GET all cards:** GET http://localhost:8080/api/cards

* **Create card:** POST http://localhost:8080/api/cards

`JSON
{
"name": "Knight",
"type": "WARRIOR",
"rarity": "COMMON",
"elixirCost": 3,
"level": 14,
"hp": 2339,
"damage": 267
}`

* **Upgrade card:** PUT http://localhost:8080/api/cards/1/upgrade

* **Create player:** POST http://localhost:8080/api/players

`JSON
{
"name": "ProGamer123",
"level": 10,
"trophies": 5000
}`
* **Add trophies:** POST http://localhost:8080/api/players/1/trophies?amount=100

===============================================================================

## Bonus task

### **Overview**
I implemented a caching system to improve API by reducing database queries

### **Implementation**

#### **Design pattern**
- **Pattern**: Singleton Pattern
- **Location**: patterns/singleton/CacheService

#### **What is Cached**
The getAllCards() method is cached because:
- It's often called
- Card list doesn't change often
- Query is expensive (joins and mappings)

#### **How It Works**

**1st Request:**

GET /api/cards
```
Check cache -> Miss -> Query database -> Store in cache -> Return data (500 ms)
```

**2nd Requests:**

GET /api/cards
```
Check cache -> Hit -> Return from cache (5ms)
```

**After Card Created/Updated/Deleted:**

POST /api/cards
```
Create card in database -> Clear cache automatically -> Next GET request rebuilds cache
```

#### **Cache Invalidation**

The cache is automatically cleared when card created/ updated/ upgraded/ deleted

Manual clearing:
```http
DELETE http://localhost:8080/api/cards/cache
```

### **Details**

#### **Singleton Pattern**
Only one cache instance exists in the application:
```
CacheService cache = CacheService.getInstance();
```

#### **SOLID**
- **SRP**: Cache logic separated into dedicated CacheService class
- **OCP**: Can extend cache strategies without modifying existing code
- **DIP**: Service depends on cache abstraction

#### **Improvement**
- **Before**: 500ms per request
- **After cache**: ms per request (100x faster)

### **Testing Cache**

**Test cache hit:**
```
# First call - cache miss (slow)
curl http://localhost:8080/api/cards

# Second call - cache hit (fast)
curl http://localhost:8080/api/cards
```

**Test cache invalidation:**
```
# Create new card
curl -X POST http://localhost:8080/api/cards \
  -H "Content-Type: application/json" \
  -d '{"name":"TestCard", "type":"WARRIOR", ...}'

# Next GET will rebuild cache
http://localhost:8080/api/cards
```

**Manual cache clear:**
```
DELETE http://localhost:8080/api/cards/cache
```

### **Why This Approach?**

**Advantages:**
- Simple
- Fast access
- Automatic invalidation
- Follows Singleton pattern

**Limitations:**
- data lost on restart
- single server only
- Limited by memory


### **Reflection on Bonus Task**

Implementing the cache taught me:

1. **Performance Matters**: Small optimizations (caching) can improve user experience
2. **Cache Invalidation is Hard**: The hardest part wasn't storing data, but knowing when to clear it
3. **Singleton in Practice**: Cache is perfect for Singleton because you want one shared cache, not multiple copies

Bonus task taught how Singleton pattern solve problems (shared cache instance) and how SOLID principles keep the solution clean and maintainable.

=============================================================================================

## Reflection
### What I Learned
1. [x] **Design Patterns:** Factory simplified card creation, Builder improved readability, Singleton managed shared resources
2. [x] **SOLID Principles:** Made code modular and testable - each class has clear responsibility
3. [x] **Spring Boot:** Dependency injection eliminated boilerplate, made testing easier
4. [x] **REST API Design:** Proper HTTP methods and status codes improve API clarity
### Challenges
* Watching and reading lectures
* Creating generic CrudRepository<T> for different entity types
* Managing polymorphism in CardFactory with database mapping
* Understanding Spring Boot dependency injection vs manual object creation
* Applying all SOLID principles simultaneously
### Value
* Before: Monolithic code, hard to modify
* After: Layered architecture, easy to extend and test
* Can add new card types without modifying existing code (OCP)
* Easy to unit test each layer independently (DIP)