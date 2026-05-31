package com.unifecaf.automotivo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção lançada quando um recurso não é encontrado no banco de dados.
 * @ResponseStatus mapeia automaticamente para HTTP 404.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(String.format("%s com ID %d não foi encontrado.", recurso, id));
    }

    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
