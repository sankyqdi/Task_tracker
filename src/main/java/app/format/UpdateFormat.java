package app.format;

import app.dto.TaskShowDTO;
import app.model.Task;

public class UpdateFormat extends BaseFormat{

    public static String taskFormat(Task task) {
        return "\n╔════════════════════════════════════════════════════════╗\n"
                + "║           ✏️  ЗАДАЧА УСПЕШНО ОБНОВЛЕНА               ║\n"
                + "╚════════════════════════════════════════════════════════╝\n"
                + "║ ID:               #" + String.format("%04d", task.getId()) + "\n"
                + "║ Название:        " + task.getName() + "\n"
                + "╠════════════════════════════════════════════════════════╣\n"
                + "║ Описание:         " + task.getBody() + "\n"
                + "║ Важность:        " + getImportanceLabel(task.getImportanceLevel()) + " (" + task.getImportanceLevel() + "/10)\n"
                + "║ Приоритет:       " + getPriorityLabel(task.getPriority()) + " (" + task.getPriority() + "/10)\n"
                + "║ Стадия:          " + task.getStage() + "\n"
                + "╠════════════════════════════════════════════════════════╣\n"
                + "║ Создано:         " + task.getCreatedAt() + "\n"
                + "║ Обновлено:       " + task.getUpdatedAt() + "\n"
                + "║ Срок выполнения: " + task.getDueDate() + "\n"
                + "║ Время (ч):       " + (task.getEstimatedHours() != null ? String.format("%.1f", task.getEstimatedHours()) + " ч" : "—") + "\n"
                + "║ Статус:          " + (task.isCompleted() ? "✅ Завершено" : "⏳ В процессе") + "\n"
                + "║ Теги:            " + formatAllTags(task) + "\n"
                + "╚════════════════════════════════════════════════════════╝\n";

    }

    public static String taskFormat(TaskShowDTO task) {
        return "\n╔════════════════════════════════════════════════════════╗\n"
                + "║           ✏️  ЗАДАЧА УСПЕШНО ОБНОВЛЕНА               ║\n"
                + "╚════════════════════════════════════════════════════════╝\n"
                + "║ ID:               #" + String.format("%04d", task.getId()) + "\n"
                + "║ Название:        " + task.getName() + "\n"
                + "╠════════════════════════════════════════════════════════╣\n"
                + "║ Описание:         " + task.getBody() + "\n"
                + "║ Важность:        " + getImportanceLabel(task.getImportanceLevel()) + " (" + task.getImportanceLevel() + "/10)\n"
                + "║ Приоритет:       " + getPriorityLabel(task.getPriority()) + " (" + task.getPriority() + "/10)\n"
                + "║ Стадия:          " + task.getStage() + "\n"
                + "╠════════════════════════════════════════════════════════╣\n"
                + "║ Создано:         " + task.getCreatedAt() + "\n"
                + "║ Обновлено:       " + task.getUpdatedAt() + "\n"
                + "║ Срок выполнения: " + task.getDueDate() + "\n"
                + "║ Время (ч):       " + (task.getEstimatedHours() != null ? String.format("%.1f", task.getEstimatedHours()) + " ч" : "—") + "\n"
                + "║ Статус:          " + (task.isCompleted() ? "✅ Завершено" : "⏳ В процессе") + "\n"
                + "║ Теги:            " + formatTagsFromDTO(task.getBuiltInTags(), task.getCustomTags()) + "\n"
                + "╚════════════════════════════════════════════════════════╝\n";

    }

    private static String getImportanceLabel(byte level) {
        if (level <= 3) return "🟢 Низкий";
        if (level <= 6) return "🟡 Средний";
        return "🔴 Высокий";
    }

    private static String getPriorityLabel(byte priority) {
        if (priority <= 3) return "Низ";
        if (priority <= 6) return "Сред";
        return "Выс";
    }

    private static String formatAllTags(Task task) {
        StringBuilder tags = new StringBuilder();

        if (task.getBuiltInTags() != null && !task.getBuiltInTags().isEmpty()) {
            for (Object tag : task.getBuiltInTags()) {
                tags.append(tag).append(" ");
            }
        }

        if (task.getCustomTags() != null && !task.getCustomTags().isEmpty()) {
            for (String tag : task.getCustomTags()) {
                tags.append("📌 ").append(tag).append(" ");
            }
        }

        return tags.length() > 0 ? tags.toString().trim() : "—";
    }

    private static String formatTagsFromDTO(java.util.List<String> builtInTags, java.util.List<String> customTags) {
        StringBuilder tags = new StringBuilder();

        if (builtInTags != null && !builtInTags.isEmpty()) {
            for (String tag : builtInTags) {
                tags.append(tag).append(" ");
            }
        }

        if (customTags != null && !customTags.isEmpty()) {
            for (String tag : customTags) {
                tags.append("📌 ").append(tag).append(" ");
            }
        }

        return tags.length() > 0 ? tags.toString().trim() : "—";
    }
}
