package fr.versefactory.template.v1.admin;

import fr.versefactory.template.v1.core.TemplateControllerV1;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Classe de base pour tous les contrôleurs d'administration v1.
 * Les endpoints sont exposés sous /api/v1/admin/...
 */
@RequestMapping("/v1/admin")
public abstract class AdminControllerV1 extends TemplateControllerV1 {
}
