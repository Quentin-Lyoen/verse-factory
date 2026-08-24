package fr.versefactory.template.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorMessages {
    BAD_REQUEST_INVALID_ID("Identifiant invalide"),
    BAD_REQUEST_UNEXISTANT_PATH("Le chemin %s n'existe pas"),
    BAD_REQUEST_INVALID_RANGE_VALUE("La plage demandée est invalide"),
    BAD_REQUEST_INSUFFICIENT_BALANCE("Solde insuffisant"),
    BAD_REQUEST_FACTORY_FULL("La factory a atteint sa capacité maximale (%s pets max)"),
    UNAUTHORIZED_NO_USER_AUTHENTICATED("Aucun utilisateur authentifié"),
    UNAUTHORIZED_AUTHORIZATION_HEADER_SHOULD_START_WITH_BEARER("Le header Authorization doit commencer par 'Bearer '"),
    UNAUTHORIZED_AUTHORIZATION_HEADER_SHOULD_CONTAINS_A_TOKEN("Le header Authorization doit contenir un token"),
    UNAUTHORIZED_ACCESS_DENIED("Accès refusé"),
    FORBIDDEN_ACCESS("Accès interdit"),
    NOT_FOUND_RESOURCE("Ressource non trouvée"),
    METHOD_NOT_ALLOWED("Méthode non autorisée"),
    UNSUPPORTED_MEDIA_TYPE_EXCEPTION("Le type de média %s n'est pas supporté. Types supportés : %s"),
    INTERNAL_SERVER_ERROR("Erreur interne du serveur"),
    NOT_IMPLEMENTED("Fonctionnalité non implémentée");

    private final String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
