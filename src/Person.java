import java.util.Comparator;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private String surName;
    private String name;
    private String patronymic;
    private int lehgthOfFio = 0;

    public Person(String surName, String name, String patronymic) {
        this.surName = surName;
        this.name = name;
        this.patronymic = patronymic;
        lehgthOfFio = surName.length() + name.length() + patronymic.length();
    }

    public String getSurName() {
        return surName;
    }

    public String getName() {
        return name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public static class ComparatorByLength implements Comparator<Person> {
        @Override
        public int compare(Person o1, Person o2) {
            return Integer.compare(o1.lehgthOfFio, o2.lehgthOfFio);
        }
    }


    @Override
    public String toString() {
        return "Person{" +
                "surName='" + surName + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return Objects.equals(getSurName(), person.getSurName()) &&
                Objects.equals(getName(), person.getName()) &&
                Objects.equals(getPatronymic(), person.getPatronymic());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getSurName(), getName(), getPatronymic());
    }

    @Override
    public int compareTo(Person o) {
        if (this.surName.compareTo(o.surName) == 0) {
            if (this.name.compareTo(o.name) == 0) {
                return this.patronymic.compareTo(o.patronymic);
            } else return this.name.compareTo(o.name);
        } else return this.surName.compareTo(o.surName);
    }
}
