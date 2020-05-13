package sdu.se9.tv2.management.system.presentation;

import sdu.se9.tv2.management.system.domain.Program;

public class ProgramTableViewItem {
    private final String id;
    private final String name;

    public ProgramTableViewItem (Program program) {
        this.id = Integer.toString(program.getID());
        this.name = program.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
