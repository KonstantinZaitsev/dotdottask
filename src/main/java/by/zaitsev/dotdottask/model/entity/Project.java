package by.zaitsev.dotdottask.model.entity;

import java.util.List;
import java.util.StringJoiner;

/**
 * Project is the entity class used in the ProjectDao.
 *
 * @author Konstantin Zaitsev
 */
public class Project extends AbstractEntity {
    private long ownerId;
    private String title;
    private String description;
    private List<Task> taskList;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
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
                description != null ? description.equals(that.description) : that.description == null &&
                taskList != null ? taskList.equals(that.taskList) : that.taskList == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (ownerId ^ (ownerId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (taskList != null ? taskList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Project.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("ownerId=" + ownerId)
                .add("title='" + title + "'")
                .add("description='" + description + "'")
                .add("taskList=" + taskList)
                .toString();
    }
}
