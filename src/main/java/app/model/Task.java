package app.model;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import lombok.EqualsAndHashCode;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Task {

    private static final AtomicLong idGenerator = new AtomicLong(1);

    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    private String body;

    private byte importanceLevel;

    private String stage;

    private LocalDate createdAt = LocalDate.now();

    private LocalDate updatedAt = LocalDate.now();

    private LocalDate dueDate;

    private boolean isCompleted = false;

    private byte priority;

    private Set<TaskTag> builtInTags = new HashSet<>();

    private List<String> customTags = new ArrayList<>();

    public Task(String name, String body, byte importanceLevel, String stage, LocalDate dueDate) {

        this.id = idGenerator.getAndIncrement();
        this.name = name;
        this.body = body;
        this.importanceLevel = importanceLevel;
        this.dueDate = dueDate;
        this.stage = stage;
        this.priority = importanceLevel;
        this.isCompleted = false;

    }

    public static void setIdGenerator(long lastId) {

        idGenerator.set(lastId + 1);

    }

    public Double getEstimatedHours() {
        if (this.dueDate == null) {
            return null;
        }
        
        long daysBetween = ChronoUnit.DAYS.between(this.createdAt, this.dueDate);
        if (daysBetween <= 0) {
            return 0.0;
        }
        
        return daysBetween * 8.0;
    }

    public void addBuiltInTag(TaskTag tag) {
        this.builtInTags.add(tag);
    }

    public void removeBuiltInTag(TaskTag tag) {
        this.builtInTags.remove(tag);
    }

    public void addCustomTag(String tag) {
        if (tag != null && !tag.trim().isEmpty() && !this.customTags.contains(tag)) {
            this.customTags.add(tag.trim());
        }
    }

    public void removeCustomTag(String tag) {
        this.customTags.remove(tag);
    }

    public boolean hasTag(TaskTag tag) {
        return this.builtInTags.contains(tag);
    }

    public boolean hasCustomTag(String tag) {
        return this.customTags.contains(tag);
    }

    public List<String> getAllTags() {
        List<String> allTags = new ArrayList<>();
        
        for (TaskTag tag : this.builtInTags) {
            allTags.add(tag.getFormattedTag());
        }
        
        allTags.addAll(this.customTags);
        
        return allTags;
    }

}
