package fr.versefactory.template.v1.core;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;


import java.net.URI;
import java.util.Optional;

/**
 * Classe abstraite de base pour tous les contrôleurs de l'API.
 */
public abstract class TemplateControllerV1 {

    @Autowired
    protected HttpServletRequest request;

    @Autowired
    protected HttpServletResponse response;

    /**
     * Fournit le {@link NativeWebRequest} courant, utilisé par les interfaces
     * OpenAPI générées pour la négociation de contenu des exemples.
     */
    public final Optional<NativeWebRequest> getRequest() {
        return Optional.of(new ServletWebRequest(request, response));
    }

    /**
     * Retourne une réponse 201 Created avec le header Location pointant vers la ressource créée.
     * On suppose que la ressource est accessible via un GET sur l'URL courante + /{id}.
     *
     * @param id L'identifiant de la ressource créée.
     * @return ResponseEntity avec le header Location.
     */
    protected ResponseEntity<Void> created(Object id) {
        return ResponseEntity.created(getLocationUri(id)).build();
    }

    /**
     * Retourne une réponse 201 Created avec le header Location et un corps de réponse.
     * On suppose que la ressource est accessible via un GET sur l'URL courante + /{id}.
     *
     * @param id L'identifiant de la ressource créée.
     * @param body Le corps de la réponse.
     * @param <T> Le type du corps.
     * @return ResponseEntity avec le header Location et le corps.
     */
    protected <T> ResponseEntity<T> created(Object id, T body) {
        return ResponseEntity.created(getLocationUri(id)).body(body);
    }

    /**
     * Retourne une réponse 201 Created avec le header Location basé sur un template de path et des paramètres.
     * Le path est relatif à la racine de l'API (context path).
     *
     * @param path Le template de path (ex: "/v1/admin/users/{id}" ou "/v1/items/{0}/sub/{1}").
     * @param params Les paramètres pour remplir le template.
     * @return ResponseEntity avec le header Location.
     */
    protected ResponseEntity<Void> created(String path, Object... params) {
        return ResponseEntity.created(getLocationUri(path, params)).build();
    }

    /**
     * Retourne une réponse 201 Created avec le header Location (via template) et un corps de réponse.
     *
     * @param body Le corps de la réponse.
     * @param path Le template de path.
     * @param params Les paramètres pour remplir le template.
     * @param <T> Le type du corps.
     * @return ResponseEntity avec le header Location et le corps.
     */
    protected <T> ResponseEntity<T> created(T body, String path, Object... params) {
        return ResponseEntity.created(getLocationUri(path, params)).body(body);
    }

    private URI getLocationUri(Object id) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    private URI getLocationUri(String path, Object... params) {
        return UriComponentsBuilder.fromPath(path)
                .buildAndExpand(params)
                .toUri();
    }
}
