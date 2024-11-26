package school.dto;

import lombok.Data;

@Data
public class NoificationResDto {
    private Long id;
    private String name;
    private String address;
    private boolean isNew;

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }
}