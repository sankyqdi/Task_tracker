package app.format;


import app.dto.TaskDeletedDTO;
import app.exception.FormatFailed;
import app.exception.IncorrectDataEntry;
import app.exception.TaskNotDeleted;
import app.model.Task;
import app.service.Console;

import java.io.FileNotFoundException;
import java.util.List;

public class DeleteFormat extends BaseFormat{

    public static String taskFormat(List<String> nameTasks) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════════════════════╗\n");
        sb.append("║              🗑️  ЗАДАЧИ УДАЛЕНЫ ИЗ ПАМЯТИ            ║\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n\n");
        for (int i = 0; i < nameTasks.size(); i++) {
            sb.append("  ").append(i + 1).append(". ❌ ").append(nameTasks.get(i)).append(" — удалено\n");
        }
        sb.append("\n✅ Всего удалено задач: ").append(nameTasks.size()).append("\n");
        sb.append("ℹ️  Данные остаются в хранилище и восстановятся при перезапуске.\n\n");
        return sb.toString();

    }

    public static String taskFormatToOne(TaskDeletedDTO task)  {

        StringBuilder sb = new StringBuilder();

        sb.append("\n╔════════════════════════════════════════════════════════╗\n");
        sb.append("║           🗑️  ЗАДАЧА УДАЛЕНА ИЗ ПАМЯТИ               ║\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n");
        sb.append("║ ID:       #").append(String.format("%04d", task.getId())).append("\n");
        sb.append("║ Название: ").append(task.getName()).append("\n");
        sb.append("╠════════════════════════════════════════════════════════╣\n");
        sb.append("║ 📦 Данные СОХРАНЕНЫ в хранилище (JSON файл)\n");
        sb.append("║ 🔄 Восстановятся при перезапуске приложения\n");
        sb.append("║ 📍 ID задачи в хранилище: #").append(String.format("%04d", task.getId())).append("\n");
        sb.append("║\n");
        sb.append("║ ℹ️  Для полного удаления используйте: /delete -iR ").append(task.getId()).append("\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n\n");
        return sb.toString();

    }

    public static String rootTaskFormat(List<String> nameTasks) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n╔════════════════════════════════════════════════════════╗\n");
        sb.append("║              🗑️  ЗАДАЧИ УДАЛЕНЫ ИЗ ПАМЯТИ            ║\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n\n");

        if (nameTasks == null || nameTasks.isEmpty()) {
            sb.append("⚠️  Нет задач для удаления.\n\n");
            return sb.toString();
        }

        for (int i = 0; i < nameTasks.size(); i++) {
            sb.append("  ").append(i + 1).append(". ❌ ").append(nameTasks.get(i)).append(" — удалено\n");
        }

        sb.append("\n✅ Всего удалено задач: ").append(nameTasks.size()).append("\n");
        sb.append("⚠️ Данные полностью удалены. Восстановление невозможно \n\n");

        return sb.toString();

    }

    public static String rootTaskFormat(TaskDeletedDTO task) {

        StringBuilder sb = new StringBuilder();

        sb.append("\n╔════════════════════════════════════════════════════════╗\n");
        sb.append("║           🗑️  ЗАДАЧА УДАЛЕНА ИЗ ПАМЯТИ               ║\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n");
        sb.append("║ ID:       #").append(String.format("%04d", task.getId())).append("\n");
        sb.append("║ Название: ").append(task.getName()).append("\n");
        sb.append("╠════════════════════════════════════════════════════════╣\n");
        sb.append("║ ⚠️ Данные задачи полностью удалены. \n");
        sb.append("║ ❌  Восстановление невозможно\n");
        sb.append("╚════════════════════════════════════════════════════════╝\n\n");
        return sb.toString();

    }

}


