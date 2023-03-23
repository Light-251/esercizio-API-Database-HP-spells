package it.softwareInside.FreeGamesApiLezione23.repository;

import org.springframework.data.repository.CrudRepository;
import it.softwareInside.FreeGamesApiLezione23.models.Spell;

public interface SpellRepository extends CrudRepository<Spell, String> {

}
