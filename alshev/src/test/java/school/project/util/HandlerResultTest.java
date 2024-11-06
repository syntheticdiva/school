package school.project.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HandlerResultTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        boolean success = true;
        String message = "Test message";

        // Act
        HandlerResult result = new HandlerResult(success, message);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals(message, result.getMessage());
    }

    @Test
    void testConstructorWithFalseSuccess() {
        // Arrange
        boolean success = false;
        String message = "Error message";

        // Act
        HandlerResult result = new HandlerResult(success, message);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(message, result.getMessage());
    }

    @Test
    void testConstructorWithNullMessage() {
        // Arrange
        boolean success = true;
        String message = null;

        // Act
        HandlerResult result = new HandlerResult(success, message);

        // Assert
        assertTrue(result.isSuccess());
        assertNull(result.getMessage());
    }

    @Test
    void testConstructorWithEmptyMessage() {
        // Arrange
        boolean success = true;
        String message = "";

        // Act
        HandlerResult result = new HandlerResult(success, message);

        // Assert
        assertTrue(result.isSuccess());
        assertEquals("", result.getMessage());
    }
}