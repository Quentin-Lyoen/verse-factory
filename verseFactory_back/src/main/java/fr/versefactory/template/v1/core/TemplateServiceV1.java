package fr.versefactory.template.v1.core;

import lombok.RequiredArgsConstructor;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Classe abstraite de base pour tous les services de l'API v1.
 * <p>
 * {@code @Transactional} s'applique à toutes les méthodes publiques des sous-classes.
 * La méthode {@link #self()} permet l'auto-invocation transactionnelle via le proxy AOP.
 */
@Service
@Transactional
@RequiredArgsConstructor
public abstract class TemplateServiceV1 {

    /**
     * Retourne le proxy AOP de l'instance courante, permettant à une méthode
     * d'en appeler une autre en passant par le proxy (et donc en respectant
     * les annotations {@code @Transactional}, {@code @Cacheable}, etc.).
     * <p>
     * Nécessite {@code @EnableAspectJAutoProxy(exposeProxy = true)} sur la configuration.
     */
    @SuppressWarnings("unchecked")
    protected final <T> T self() {
        return (T) AopContext.currentProxy();
    }
}
