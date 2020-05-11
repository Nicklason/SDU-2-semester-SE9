package sdu.se9.tv2.management.system.presentation;

import sdu.se9.tv2.management.system.domain.Program;
import sdu.se9.tv2.management.system.persistence.PersistenceProgram;

import java.sql.SQLException;

public class ProgramTableViewItem {
    private final Program program;
    private final String id;

    public ProgramTableViewItem (Program program) throws SQLException {
        this.program = program;
        this.id = String.valueOf(PersistenceProgram.getInstance().getProgram(program.getID()));

    }

    public Program getProgram() {
        return program;
    }

    public String getId() {
        return id;
    }
}
