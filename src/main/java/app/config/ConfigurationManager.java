package app.config;

import app.model.TaskTag;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
public class ConfigurationManager {

    public static ConfigurationManager instance;

    @Setter
    private boolean hint = false;
    @Setter
    private boolean enableCustomTag = false;
    @Setter
    private boolean rootRights = false;
    @Setter
    private byte implementsLevel = 6;
    @Setter
    private String stage = "Created";
    @Setter
    private LocalDate dueDate = LocalDate.now();
    @Setter
    private TaskTag taskTag = TaskTag.FEATURE;

    public static ConfigurationManager getInstance() {

        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {

                    instance = new ConfigurationManager();

                }
            }
        }

        return instance;
    }

    public boolean getHint() {

        return hint;

    }

    public boolean getEnableCustomTag() {

        return enableCustomTag;

    }

    public boolean getRoot() {

        return rootRights;

    }

}
