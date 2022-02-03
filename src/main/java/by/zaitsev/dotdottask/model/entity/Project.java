package by.zaitsev.dotdottask.model.entity;

import java.util.StringJoiner;

/**
 * Project is the entity class used in the ProjectDao.
 *
 * @author Konstantin Zaitsev
 */
public class Project extends AbstractEntity {
    private long ownerId;
    private String title;
    private String color;
    private String description;


    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        Project that = (Project) o;
        return ownerId == that.ownerId &&
                title != null ? title.equals(that.title) : that.title == null &&
                color != null ? color.equals(that.color) : that.color == null &&
                description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (ownerId ^ (ownerId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Project.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("ownerId=" + ownerId)
                .add("title='" + title + "'")
                .add("color='" + color + "'")
                .add("description='" + description + "'")
                .toString();
    }
}