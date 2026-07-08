package fr.versefactory.template.v1.core;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Classe abstraite de base pour tous les repositories de l'API v1.
 * <p>
 * Fournit le {@link DSLContext} jOOQ ainsi que des utilitaires de pagination.
 */
public abstract class TemplateRepositoryV1 {

    protected static final int CHUNK_SIZE = 1000;

    @Autowired
    protected DSLContext dslContext;

    /**
     * Retourne l'offset SQL à partir d'un {@link Pageable} Spring Data.
     */
    protected long getOffset(Pageable pageable) {
        return Optional.of(pageable)
                .filter(Pageable::isPaged)
                .map(Pageable::getOffset)
                .orElse(0L);
    }

    /**
     * Retourne la limite SQL (LIMIT) à partir d'un {@link Pageable} Spring Data.
     * Retourne {@link Optional#empty()} si la pagination est désactivée.
     */
    protected Optional<Integer> getLimit(Pageable pageable) {
        return Optional.of(pageable)
                .filter(Pageable::isPaged)
                .map(Pageable::getPageSize);
    }

    /**
     * Découpe une liste en sous-listes de taille {@link #CHUNK_SIZE},
     * utile pour les insertions/mises à jour en masse via jOOQ.
     */
    protected <T> List<List<T>> getChunks(List<T> items) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < items.size(); i += CHUNK_SIZE) {
            chunks.add(items.subList(i, Math.min(i + CHUNK_SIZE, items.size())));
        }
        return chunks;
    }
}
