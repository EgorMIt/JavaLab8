package app.collection;


import java.io.Serializable;
import java.time.LocalDateTime;

public class Human implements Serializable {

    private Integer                 age;
    private LocalDateTime dateOfBirthday;

    public Human(Integer age, LocalDateTime dateOfBirthday) {
        this.age =              age;
        assert age              > 0;
        this.dateOfBirthday =   dateOfBirthday;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDateOfBirthday(LocalDateTime dateOfBirthday) {
        this.dateOfBirthday = dateOfBirthday;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDateTime getDateOfBirthday() {
        return dateOfBirthday;
    }

    @Override
    public String toString() {
        return "возраст - " + getAge() + ", дата рождения - " + getDateOfBirthday();
    }
}
