package sdu.se9.tv2.management.system;

public class Main {
    public static void main (String args[]) {
        ManagementSystem system = new ManagementSystem();

        system.run();

        //App.load();

        /*
        PersistenceProducer producerPersistence = new PersistenceProducer();

        PersistenceProgram programPersistence = new PersistenceProgram();

        Producer tv2 = producerPersistence.createProducer("TV2");

        System.out.println(producerPersistence.getProducer(tv2.getID()));

        Program badehotellet = programPersistence.createProgram(tv2.getID(), "Badehotellet", 1234);

        System.out.println(badehotellet);
        
        System.out.println(programPersistence.getProgram(badehotellet.getID()));

        PersistencePerson persistencePerson = new PersistencePerson();

        Person amalie = persistencePerson.createPerson("Amalie", "Dollerup");

        PersistenceCredit persistenceCredit = new PersistenceCredit();

        persistenceCredit.createCredit(badehotellet.getID(), amalie.getId(), "Amanda Madsen");*/
    }
}
