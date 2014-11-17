package demo;

import java.util.Optional;

import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;

@Component("personRepository")
public interface Person extends Repository<PersonEntry, Long>{

	void delete(Long id);

	PersonEntry save(PersonEntry entry);

	Optional<PersonEntry> findOne(Long id);

	Iterable<PersonEntry> findAll();

	int count();
}
