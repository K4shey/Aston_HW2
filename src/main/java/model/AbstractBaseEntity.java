package model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractBaseEntity {
    private Long id;

    public boolean isNew() {
        return id == null;
    }
}
