package app.exception;

import app.util.LogUtil;
import org.slf4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GlobalExceptionHandler {

    private static final Logger log = LogUtil.getLogger(GlobalExceptionHandler.class);
    private static final String SEPARATOR = "=".repeat(58);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private static  GlobalExceptionHandler instance;
    private static boolean hint = false;

    private GlobalExceptionHandler() {}

    public static GlobalExceptionHandler getInstance() {

        if (instance == null) {

            instance = new GlobalExceptionHandler();

        }

        return instance;

    }

    public String handleException(Throwable throwable) {

        StringBuilder sb = new StringBuilder();
        sb.append("\n").append(SEPARATOR).append("\n");
        sb.append("  ❌ ОШИБКА\n");
        sb.append(SEPARATOR).append("\n");
        sb.append("  ⏰ ").append(LocalDateTime.now().format(TIME_FORMATTER)).append("\n");

        if (throwable instanceof FundamentError) {

            appendError(sb, (FundamentError) throwable);

        } else {

            appendGenericError(sb, throwable);

        }

        sb.append(SEPARATOR).append("\n");
        return sb.toString();


    }


    private void appendGenericError(StringBuilder sb, Throwable throwable) {
        sb.append("  📝 ").append(throwable.getMessage() != null ?
                throwable.getMessage() :
                "Неизвестная ошибка").append("\n");

        if (hint) {

            sb.append("  💡 ").append(getHint((FundamentError) throwable));

        }
    }

    public void appendError(StringBuilder sb, FundamentError fe) {

        ListErrors list = fe.getListErrors();

        sb.append("  🔢 Код: ").append(list.getCode()).append("\n");
        sb.append("  📝 ").append(fe.getMessage()).append("\n");


        if (fe instanceof DateError) {
            DateError de = (DateError) fe;

            sb.append("Неправильный формат: ").append(de.getMessage()).append("\n");

        }

        if (fe instanceof NumberError) {

            NumberError ne = (NumberError) fe;

            sb.append("Неправильный формат: ").append(ne.getMessage()).append("\n");

        }

        if (fe instanceof IncorrectDataEntry) {

            IncorrectDataEntry ide = (IncorrectDataEntry) fe;

            sb.append("Ввод пользователя: ").append(ide.getMessage()).append("\n");

        }

        if (fe instanceof TaskNotCreated) {

            TaskNotCreated tnc = (TaskNotCreated) fe;

            sb.append("Причина ошибки: ").append(tnc.getOriginalError().toString()).append("\n");

        }

        if (fe instanceof TaskNotUpdated) {

            sb.append("Причина ошибки: ").append(fe.getOriginalError().toString()).append("\n");

        }

        if (fe instanceof TaskNotFound) {

            sb.append("Не найденный идентификатор: ").append("'").append(fe.getArgs()[0]).append("'").append("\n");

        }

        if (fe instanceof TaskNotDeleted) {

            sb.append("Причина ошибки: ").append(fe.getOriginalError().toString()).append("\n");

        }

        if (fe instanceof JsonError) {

            if (fe.getOriginalError() != null) {

                sb.append("Причина JSON ошибки: ").append(fe.getOriginalError().toString()).append("\n");
                if (fe.getListErrors().getCode() == 402 || fe.getListErrors().getCode() == 401) {

                    sb.append(" 🔥 Внимание! Из-за ошибки все последующие изменения в программе не будут сохранены! 🔥 ").append("\n");
                    sb.append("  🔄  Просьба проверить целостность файлов и перезапустить программу");

                } else if (fe.getListErrors().getCode() == 406) {

                    sb.append(" ℹ️   Проблема с чтением файла. Возможно он не исправен").append("\n");

                } else {

                    sb.append("Из-за настроек приложения задачи не были сохранены ").append("\n");

                }


            } else {

                sb.append("Задач во внутренней памяти не обнаружено.");

            }

        }

        if (hint) {

            sb.append("  💡 ").append(getHint(fe));

        }

    }

    private String getHint(FundamentError fe) {

        ListErrors list = fe.getListErrors();

        switch (fe) {

            case DateError dateError -> {

                return """
                        Проверьте правильность записи формата даты.
                        Допустимые форматыЖ: YY.MM.DD(2000.06.23) и DD.MM.YY(23.06.2000)
                        """;
            }
            case NumberError numberError -> {

                return "Проверьте, что ваша числовая запись не имеет пробелов, символов и букв";
            }
            case IncorrectDataEntry incorrectDataEntry -> {

                return "Используйте команду /com, чтобы убедиться в правильности написания команды";
            }
            case TaskNotCreated taskNotCreated -> {

                return "Прочитайте документацию по созданию задачи(/com)";
            }
            case TaskNotUpdated taskNotUpdated -> {

                return "Убедитесь, что задача существует и ваши новые данные подходят по формату";
            }
            case TaskNotFound taskNotFound -> {

                return "Воспользуйтесь командой /show -a для просмотр всех задач и их идентификаторов";
            }
            case TaskNotDeleted taskNotDeleted -> {

                return "Возможно задачи не существует или у вас не хватает прав для удаления";
            }
            case JsonError jsonError -> {

                return "Проверьте, что файл с таким Path существует и он не открыт в другой программе";
            }
            case CommandParsingError commandParsingError -> {

                return "Прочитайте документацию для лучшего понимания требований к командам (/com)";
            }

            case FormatFailed formatFailed -> {

                return "Обратитесь к разработчику или проверьте хранилище на баги";

            }

            case SystemError systemError -> {

                return "Обратитесь к разработчику";

            }


            default -> {

                return "  💡 Попробуйте перезапустить приложение или обратитесь к разработчику\n";

            }

        }

    }


    public void setHint(boolean toggle) {

        hint = toggle;

    }


}
