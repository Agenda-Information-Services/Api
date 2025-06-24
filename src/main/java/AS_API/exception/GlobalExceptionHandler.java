package AS_API.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        e.printStackTrace(); // 콘솔 로그 출력
        return ResponseEntity.internalServerError().body(Map.of(
                "error", "RuntimeException",
                "message", e.getMessage()
        ));
    }
}
