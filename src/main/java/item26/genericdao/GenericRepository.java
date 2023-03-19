package item26.genericdao;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class GenericRepository<E extends Entity> {

    private Set<E> entities;

    public GenericRepository() {
        this.entities = new HashSet<>();
    }

    public Optional<E> findById(Long id) {
        return entities.stream().filter(a -> a.getId().equals(id)).findAny();
    }

    public void add(E account) {
        this.entities.add(account);
    }
}

