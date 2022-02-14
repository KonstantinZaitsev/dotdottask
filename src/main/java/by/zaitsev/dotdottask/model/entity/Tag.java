package by.zaitsev.dotdottask.model.entity;

import java.util.StringJoiner;

/**
 * Tag is the entity class used in the TagDao.
 *
 * @author Konstantin Zaitsev
 */
public class Tag extends AbstractEntity {
    private long taskId;
    private String name;

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return taskId == that.taskId &&
                name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (taskId ^ (taskId >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Tag.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("userId=" + taskId)
                .add("name='" + name + "'")
                .toString();
    }
}
