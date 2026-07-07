package app.format;

import app.dto.TaskCreatedDTO;
import app.dto.TaskShowDTO;
import app.model.Task;

import java.util.List;

public class BaseFormat {

    public static String formatCreateTask(TaskCreatedDTO task) {

        return "\n"
                + "╔════════════════════════════════════════════════════════╗\n"
                + "║           ✅ ЗАДАЧА УСПЕШНО СОЗДАНА                  ║\n"
                + "╚════════════════════════════════════════════════════════╝\n"
                + "║ ID:               #" + String.format("%04d", task.getId()) + "\n"
                + "║ Название:        " + task.getName() + "\n"
                + "╠════════════════════════════════════════════════════════╣\n"
                + "║ Описание:         " + task.getBody() + "\n"
                + "║ Важность:        " + getImportanceLabel(task.getImportanceLevel()) + " (" + task.getImportanceLevel() + "/10)\n"
                + "║ Приоритет:       " + getPriorityLabel(task.getPriority()) + " (" + task.getPriority() + "/10)\n"
                + "╠════════════════════════════════════════════════════════╣\n"
                + "║ Создано:         " + task.getCreatedAt() + "\n"
                + "║ Обновлено:       " + task.getUpdatedAt() + "\n"
                + "║ Срок выполнения: " + task.getDueDate() + "\n"
                + "║ Время (ч):       " + (task.getEstimatedHours() != null ? String.format("%.1f", task.getEstimatedHours()) + " ч" : "—") + "\n"
                + "║ Статус:          " + (task.isCompleted() ? "✓ Завершено" : "○ В процессе") + "\n"
                + "║ Теги:            " + formatTags(task.getBuiltInTags(), task.getCustomTags()) + "\n"
                + "╚════════════════════════════════════════════════════════╝\n";

    }

    public static String formatShowTask(List<TaskShowDTO> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            return "\n⚠️  Нет задач для отображения.\n";
        }

        StringBuilder sb = new StringBuilder("\n");
        int count = 1;

        for (var task : tasks) {
            sb.append("╔════════════════════════════════════════════════════════╗\n");
            sb.append("║ #").append(String.format("%04d", task.getId()))
                    .append(" | ").append(task.getName()).append("\n");
            sb.append("╠════════════════════════════════════════════════════════╣\n");
            sb.append("║ 📝 Описание:    ").append(truncate(task.getBody(), 45)).append("\n");
            sb.append("║ 📌 Стадия:      ").append(task.getStage()).append("\n");
            sb.append("║ ⚡ Важность:    ").append(getImportanceLabel(task.getImportanceLevel()))
                    .append(" (").append(task.getImportanceLevel()).append("/10)\n");
            sb.append("║ 🎯 Приоритет:   ").append(getPriorityLabel(task.getPriority()))
                    .append(" (").append(task.getPriority()).append("/10)\n");
            sb.append("║ 📅 Срок:        ").append(task.getDueDate() != null ? task.getDueDate() : "—").append("\n");
            sb.append("║ ⏱️  Время (ч):   ").append(task.getEstimatedHours() != null ? String.format("%.1f", task.getEstimatedHours()) + " ч" : "—").append("\n");
            sb.append("║ ✓ Статус:       ").append(task.isCompleted() ? "✅ Завершено" : "⏳ В процессе").append("\n");
            sb.append("║ 🏷️  Теги:        ").append(formatTags(task.getBuiltInTags(), task.getCustomTags())).append("\n");
            sb.append("║ 🕐 Создано:     ").append(task.getCreatedAt()).append("\n");
            sb.append("║ 🔄 Обновлено:   ").append(task.getUpdatedAt()).append("\n");
            sb.append("╚════════════════════════════════════════════════════════╝\n");

            count++;
        }

        sb.append("\n📊 Всего задач: ").append(count - 1).append("\n\n");
        return sb.toString();
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

    private static String formatTags(List<String> builtInTags, List<String> customTags) {
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

    private static String truncate(String text, int maxLength) {
        if (text == null) return "—";
        if (text.length() <= maxLength) return text;
        return text.substring(0, maxLength - 3) + "...";
    }
}
