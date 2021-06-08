DROP TABLE IF EXISTS public.game_state;
CREATE TABLE public.game_state (
    id serial NOT NULL PRIMARY KEY,
    name text NOT NULL,
    current_map TEXT NOT NULL,
    discovered_maps TEXT [],
    saved_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    player_id integer NOT NULL
);

DROP TABLE IF EXISTS public.player CASCADE;
CREATE TABLE public.player (
    id serial NOT NULL PRIMARY KEY,
    player_name text NOT NULL,
    hp integer NOT NULL,
    strength integer NOT NULL,
    x integer NOT NULL,
    y integer NOT NULL
);

DROP TABLE IF EXISTS public.inventory;
CREATE TABLE public.inventory (
                               id serial NOT NULL PRIMARY KEY,
                               player_id integer NOT NULL,
                               item_name text NOT NULL,
                               strength_mod integer,
                               health_mod integer,
                               dodge_mod float
);



ALTER TABLE ONLY public.game_state
    ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);

ALTER TABLE ONLY public.inventory
ADD CONSTRAINT fk_player_id FOREIGN KEY (player_id) REFERENCES public.player(id);
