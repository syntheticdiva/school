package school.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // Обработка ошибки, когда школа не найдена
    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleResourceNotFoundException(ResourceNotFoundException e, Model model) {
        log.error("Ресурс не найден", e);
        return createErrorResponse("Школа не найдена", e.getMessage(), model);
    }

    // Обработка ошибок валидации формы
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationErrors(BindException e, Model model) {
        log.error("Ошибка валидации", e);
        String errorMessage = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));
        return createErrorResponse("Ошибка валидации данных", errorMessage, model);
    }

    // Обработка ошибок неверного формата параметров (например, неверный формат ID)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleTypeMismatch(MethodArgumentTypeMismatchException e, Model model) {
        log.error("Неверный формат параметра", e);
        return createErrorResponse(
                "Неверный формат параметра",
                "Параметр '" + e.getName() + "' имеет неверный формат",
                model
        );
    }

    // Обработка отсутствующих обязательных параметров
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingParams(MissingServletRequestParameterException e, Model model) {
        log.error("Отсутствует обязательный параметр", e);
        return createErrorResponse(
                "Отсутствует обязательный параметр",
                "Параметр '" + e.getParameterName() + "' является обязательным",
                model
        );
    }

    // Обработка случая, когда страница не найдена
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoHandlerFound(NoHandlerFoundException e, Model model) {
        log.error("Страница не найдена", e);
        return createErrorResponse(
                "Страница не найдена",
                "Запрошенная страница '" + e.getRequestURL() + "' не существует",
                model
        );
    }

    // Обработка всех остальных непредвиденных ошибок
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleAllUncaughtException(Exception e, Model model) {
        log.error("Непредвиденная ошибка", e);
        return createErrorResponse(
                "Произошла непредвиденная ошибка",
                "Пожалуйста, попробуйте позже или обратитесь к администратору",
                model
        );
    }

    private String createErrorResponse(String userMessage, String technicalMessage, Model model) {
        model.addAttribute("errorMessage", userMessage);
        model.addAttribute("technicalDetails", technicalMessage);
        model.addAttribute("timestamp", new java.util.Date());
        return "error";
    }
}