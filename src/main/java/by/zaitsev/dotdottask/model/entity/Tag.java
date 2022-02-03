package by.zaitsev.dotdottask.model.entity;

import java.util.StringJoiner;

/**
 * Tag is the entity class used in the TagDao.
 *
 * @author Konstantin Zaitsev
 */
public class Tag extends AbstractEntity {
    private long userId;
    private String name;
    private String color;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Tag that = (Tag) o;
        return userId == that.userId &&
                name != null ? name.equals(that.name) : that.name == null &&
                color != null ? color.equals(that.color) : that.color == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + userId)
                .add("name='" + name + "'")
                .add("color='" + color + "'")
                .toString();
    }
}