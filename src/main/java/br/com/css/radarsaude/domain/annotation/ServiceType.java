package br.com.css.radarsaude.domain.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Esta classe de anotação utilitária
 * é utilizada para desambiguação de
 * IoC do Spring.
 *
 * Utilizamos o @code{enum ServiceEntity}
 * para garantir que não hajam erros de
 * digitação qdo definirmos os
 * @<code>@Qualifier/code>
 *
 * Pois, annotation lançam somente unchecked exceptions
 * e não são verificadas em tempo de compilação.
 *
 * Com o Enum evitamos este problema garantindo a
 * verificação em tempo de compilação.
 *
 * @author Cleber Souza
 * @version 1.0
 * @since 03/06/2022
 */
@Retention(RetentionPolicy.RUNTIME)
@Qualifier
public @interface ServiceType {
    ServiceTypeEntity value();
}
