package school.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import school.dto.NoificationResDto;
import school.dto.SchoolEntityDTO;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final RestTemplate restTemplate;

    @Value("${NOTIFICATION_SERVICE_URL:http://localhost:8081/api/notifications}")
    private String notificationUrl;

    @Value("${NOTIFICATION_MAX_ATTEMPTS:3}")
    private int maxAttempts; // Максимальное количество попыток

    @Value("${NOTIFICATION_RETRY_DELAY:1000}")
    private long retryDelay; // Время ожидания между попытками в мсек

    // Обработка уведомления о создании школы
    public void handleSchoolCreationNotification(SchoolEntityDTO school) {
        NoificationResDto notificationResDto = createNotificationResDto(school);
        sendNotification(notificationResDto, "School registered successfully");
    }

    // Обработка уведомления об обновлении школы
    public void handleSchoolUpdateNotification(SchoolEntityDTO school) {
        NoificationResDto notificationResDto = createNotificationResDto(school);
        sendNotification(notificationResDto, "School updated successfully");
    }

    //  метод для отправки уведомлений с повторными попытками
    private void sendNotification(NoificationResDto school, String message) {
        int attempt = maxAttempts; // Текущая попытка
        boolean success = false; // Флаг успешной отправки

        while (attempt > 0) {
            try {
                HttpHeaders headers = new HttpHeaders();
                headers.set("Content-Type", "application/json");
                HttpEntity<NoificationResDto> entity = new HttpEntity<>(school, headers);

                ResponseEntity<String> response = restTemplate.postForEntity(notificationUrl, entity, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Notification sent successfully: " + message);
                    success = true; // Успешно отправлено
                    break; // Выход из цикла при успешной отправке
                } else {
                    System.out.println("Failed to send notification: " + message);
                }
            } catch (Exception e) {
                System.out.println("Error while sending notification on attempt " + (maxAttempts - attempt + 1) + ": " + e.getMessage());
            }

            attempt--; // Уменьшаем количество оставшихся попыток

            if (!success && attempt > 0) { // Если не удалось и это не последняя попытка
                try {
                    Thread.sleep(retryDelay); // Ждем перед следующей попыткой
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
                    System.out.println("Thread was interrupted: " + ie.getMessage());
                }
            }
        }

        if (!success) {
            System.out.println("Max attempts reached. Notification could not be sent.");
        }
    }
    // Метод для создания DTO уведомления
    private NoificationResDto createNotificationResDto(SchoolEntityDTO school) {
        NoificationResDto notificationResDto = new NoificationResDto();
        notificationResDto.setId(school.getId());
        notificationResDto.setName(school.getName());
        notificationResDto.setAddress(school.getAddress());
        notificationResDto.setNew(school.isNew());
        return notificationResDto;
    }
}