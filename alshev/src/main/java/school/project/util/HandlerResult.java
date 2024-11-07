package school.project.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HandlerResult {
    private boolean success;
    private String message;
}