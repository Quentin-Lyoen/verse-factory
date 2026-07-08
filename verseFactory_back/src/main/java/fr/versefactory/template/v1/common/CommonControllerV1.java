package fr.versefactory.template.v1.common;

import fr.versefactory.template.v1.core.TemplateControllerV1;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe de base pour tous les contrôleurs publics/communs v1.
 * Les endpoints sont exposés sous /api/v1/common/...
 */
@RequestMapping("/v1/common")
public abstract class CommonControllerV1 extends TemplateControllerV1 {
}
