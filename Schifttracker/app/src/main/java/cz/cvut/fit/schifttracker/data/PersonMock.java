package cz.cvut.fit.schifttracker.data;

/**
 * Created by petr on 10/31/16.
 */

public class PersonMock {

    public static Person[] getPersons() {
        Person person1 = new Person("Name1", "Surname1");
        Person person2 = new Person("Name2", "Surname2");
        Person person3 = new Person("Name3", "Surname3");
        Person person4 = new Person("Name4", "Surname4");
        Person person5 = new Person("Name5", "Surname5");
        Person person6 = new Person("Name6", "Surname6");

        return new Person[]{person1, person2, person3, person4, person5, person6};
    }
}
