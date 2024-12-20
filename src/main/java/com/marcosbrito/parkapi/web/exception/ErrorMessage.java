package com.marcosbrito.parkapi.web.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.ToString;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Getter
@ToString
public class ErrorMessage {

    private String path;
    private String method;
    private int status;
    private String statusText;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL) //Excluir todos os campos nulos do corpo JSON4
    private Map<String, String> errors;

    public ErrorMessage() {
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result);
    }

    public ErrorMessage(HttpServletRequest request, HttpStatus status, String message, BindingResult result, MessageSource messageSource) {
        this.path = request.getRequestURI();
        this.method = request.getMethod();
        this.status = status.value();
        this.statusText = status.getReasonPhrase();
        this.message = message;
        addErrors(result, messageSource, request.getLocale());
    }

    private void addErrors(BindingResult result, MessageSource messageSource, Locale locale) {

        this.errors = new HashMap<>();

        for (FieldError fieldError : result.getFieldErrors()) {

            /**
             * fildError.getCodes() retorna os codigos/path ligados ao @NotBlank ou @Size
             */

            String code = fieldError.getCodes()[0];

            /*Explicando isso aqui. O mesesage source realiza a busca nos arquivos messagem.propertires e restadas a mensagem de erro passando como argumentos:
             *  - code -> Path, ou seja o codigo
             *  - fieldError.getArguments() -> Ele passa o Array de valores que os arquivos.properties usam . Por exemplo: Ao usar um @Size(min = 4, max = 4), o getArguments buscan em formato de Array o min e max
             *  - Locale -> Informa a localização
             * */

            String message = messageSource.getMessage(code, fieldError.getArguments(), locale);
            this.errors.put(fieldError.getField(), message);
        }


    }

    private void addErrors(BindingResult result) {

        /**
         *BindinResult contem os erros detectados durante a validação de um objeto.
         * No contexto da nossa aplicação, ele está normalmente associado a erros no @NotBlank ou @Size
         *
         */

        this.errors = new HashMap<>();

        for (FieldError fieldError : result.getFieldErrors()) {
            /**
             * getField() contem o nome do campo que contém o erro. Por exemplo:
             * {
             *     "nome": ""
             * }
             *
             * caso o dto que recebe esse corpo possui um @NotBlank, o erro será lancado e o
             * getField() conterá o campo que conteve o erro, no caso, o campo "nome"
             */

            /**
             * getDefaultMessage() fornece a mensagem de erro padrão associada a validação
             */
            this.errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }


}
