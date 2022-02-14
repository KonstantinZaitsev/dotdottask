package by.zaitsev.dotdottask.model.entity;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * The AbstractEntity is a superclass for all entities in a project. Implements {@link Serializable}, so all entities
 * implement this interface too.
 *
 * @author Konstantin Zaitsev
 */
public abstract class AbstractEntity implements Serializable {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AbstractEntity that = (AbstractEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractEntity.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .toString();
    }
}
