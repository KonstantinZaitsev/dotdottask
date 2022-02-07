package by.zaitsev.dotdottask.model.entity;

import java.sql.Timestamp;
import java.util.List;
import java.util.StringJoiner;

/**
 * Task is the entity class used in the TaskDao.
 *
 * @author Konstantin Zaitsev
 */
public class Task extends AbstractEntity {
    private long projectId;
    private String title;
    private String description;
    private Timestamp creationDate;
    private Timestamp deadline;
    private boolean isDone;
    private long assignedUserId;
    private List<Tag> tagList;

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public long getAssignedUserId() {
        return assignedUserId;
    }

    public void setAssignedUserId(long assignedUserId) {
        this.assignedUserId = assignedUserId;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
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
        Task that = (Task) o;
        return projectId == that.projectId &&
                isDone == that.isDone &&
                assignedUserId == that.assignedUserId &&
                title != null ? title.equals(that.title) : that.title == null &&
                description != null ? description.equals(that.description) : that.description == null &&
                creationDate != null ? creationDate.equals(that.creationDate) : that.creationDate == null &&
                deadline != null ? deadline.equals(that.deadline) : that.deadline == null &&
                tagList != null ? tagList.equals(that.tagList) : that.tagList == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (projectId ^ (projectId >>> 32));
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (deadline != null ? deadline.hashCode() : 0);
        result = 31 * result + (isDone ? 1 : 0);
        result = 31 * result + (int) (assignedUserId ^ (assignedUserId >>> 32));
        result = 31 * result + (tagList != null ? tagList.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Task.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("projectId=" + projectId)
                .add("title='" + title + "'")
                .add("description='" + description + "'")
                .add("creationDate=" + creationDate)
                .add("deadline=" + deadline)
                .add("isDone=" + isDone)
                .add("assignedUserId=" + assignedUserId)
                .add("tagList=" + tagList)
                .toString();
    }
}
