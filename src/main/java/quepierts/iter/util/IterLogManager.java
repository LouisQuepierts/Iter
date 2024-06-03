package quepierts.iter.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class IterLogManager {
    public static final Logger LOGGER = LogManager.getLogger("Iter");

    private static final Map<String, Boolean> logicEvents = new HashMap<>();

    public static Logger getLogger() {
        return LOGGER;
    }

    public static void callLogicalEvent(String event) {
        
    }
}
