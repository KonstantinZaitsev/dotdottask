package by.zaitsev.dotdottask.model.entity;

import java.util.StringJoiner;

/**
 * User is the entity class used in the UserDao.
 *
 * @author Konstantin Zaitsev
 */
public class User extends AbstractEntity {
    private String email;
    private String name;
    private String surname;
    private String image;
    private boolean isAuthorized = false;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User that = (User) o;
        return isAuthorized == that.isAuthorized &&
                email != null ? email.equals(that.email) : that.email == null &&
                name != null ? name.equals(that.name) : that.name == null &&
                surname != null ? surname.equals(that.surname) : that.surname == null &&
                image != null ? image.equals(that.image) : that.image == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("email='" + email + "'")
                .add("name='" + name + "'")
                .add("surname='" + surname + "'")
                .add("image='" + image + "'")
                .add("isAuthorized=" + isAuthorized)
                .toString();
    }
}
