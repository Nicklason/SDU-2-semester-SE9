package sdu.se9.tv2.management.system;

import sdu.se9.tv2.management.system.helpers.Config;
import sdu.se9.tv2.management.system.presentation.App;

public class Main {
    public static void main (String args[]) {
        // Load config
        Config.load();

        // Start JavaFX
        App.load();
    }
}
