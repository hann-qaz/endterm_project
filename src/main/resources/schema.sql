DROP TABLE IF EXISTS deck_cards CASCADE;
DROP TABLE IF EXISTS decks CASCADE;
DROP TABLE IF EXISTS cards CASCADE;
DROP TABLE IF EXISTS players CASCADE;

---таблица игроков
create table players (
    id serial primary key,
    name varchar(50) unique not null,
    level int default 1,
    trophies int default 0
);

---таблица карт
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
);

--- таблица колод
create table decks (
    id serial primary key,
    player_id int not null references players(id) on delete cascade,
    deck_name varchar(50)
);

-- таблица для связи карт в колоде ( по 8 карт в одной колоде) многие ко многим
create table deck_cards (
deck_id int not null references decks(id) on delete cascade,
card_id int not null references cards(id) on delete cascade,
position int not null check (position >= 1 and position <=8),
primary key (deck_id, card_id),
unique (deck_id, position)
);

insert into players (name, level, trophies) values
                                                ('Amirhan', 12, 10000),
                                                ('Batyr', 3, 90);

insert into decks (player_id, deck_name) values
(1, 'Agro Deck'),
(2, 'Control Deck');

insert into cards (name, card_type, rarity, elixir_cost, level, damage, hp, radius, lifetime) values

('Knight', 'Warrior', 'Common', 3, 5, 150, 800, 0, 0),
('Fireball', 'Spell', 'Rare', 4, 3, 300, 0, 2, 0),
('Princess', 'Warrior', 'Legendary', 3, 1, 120, 400, 0, 0),
('Archer', 'Warrior', 'Common', 3, 4, 100, 400, 0, 0),
('Giant', 'Warrior', 'Rare', 5, 2, 200, 2000, 0, 0),
('Elixir Pump', 'Building', 'Common', 3, 6, 0, 1000, 0, 30),
('Wizard', 'Warrior', 'Epic', 5, 1, 250, 500, 0, 0),
('Lightning', 'Spell', 'Epic', 6, 4, 350, 0, 3, 1),
('Baby Dragon', 'Warrior', 'Epic', 4, 2, 180, 600, 1, 0);


insert into deck_cards (deck_id, card_id, position) values
(1, 1, 1),
(1, 2, 2),
(1, 3, 3),
(1, 4, 4),
(1, 5, 5),
(1, 6, 6),
(1, 7, 7),
(1, 8, 8),
(2, 1, 1),
(2, 4, 2),
(2, 5, 3),
(2, 7, 4),
(2, 2, 5),
(2, 8, 6),
(2, 3, 7),
(2, 9, 8);

select * from players;
select * from decks;
select * from cards;
select * from deck_cards;
