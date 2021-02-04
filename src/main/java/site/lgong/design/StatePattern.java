package site.lgong.design;

import java.util.Scanner;

/**
 * 状态模式：允许一个对象在其内部状态改变时改变它的行为
 * 状态模式的目的是为了把上述一大串if...else...的逻辑给分拆到不同的状态类中，使得将来增加状态比较容易。
 */
public class StatePattern {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BotContext bot = new BotContext();
        for (; ; ) {
            System.out.print("> ");
            String input = scanner.nextLine();
            String output = bot.chat(input);
            System.out.println(output.isEmpty() ? "(no reply)" : "< " + output);
        }
    }

}

/**
 * 聊天机器人：有两个状态：未连线、已连线
 */
interface State {
    // 初始化
    String init();

    // 回复
    String reply(String input);
}

/**
 * 未连线状态
 */
class DisconnectedState implements State {
    public String init() {
        return "Bye!";
    }

    // 未连线状态，我们收到消息也不回复
    public String reply(String input) {
        return "";
    }
}

/**
 * 连线状态
 */
class ConnectedState implements State {
    public String init() {
        return "Hello, I'm Bob.";
    }

    // 已连线状态，我们回应收到的消息：
    public String reply(String input) {
        if (input.endsWith("?")) {
            return "Yes. " + input.substring(0, input.length() - 1) + "!";
        }
        if (input.endsWith(".")) {
            return input.substring(0, input.length() - 1) + "!";
        }
        return input.substring(0, input.length() - 1) + "?";
    }
}

/**
 * 忙碌状态
 */
class BusyState implements State {
    public String init() {
        return "Sorry, I'm busy now.";
    }

    // 忙碌状态，回应收到的消息：正在忙
    public String reply(String input) {
        return "Sorry, I'm busy now.";
    }
}

/**
 * 状态切换
 */
class BotContext {
    private State state = new DisconnectedState();

    public String chat(String input) {
        if ("hello".equalsIgnoreCase(input)) {
            // 收到hello切换到在线状态:
            state = new ConnectedState();
            return state.init();
        } else if ("bye".equalsIgnoreCase(input)) {
            //  收到bye切换到离线状态:
            state = new DisconnectedState();
            return state.init();
        } else if ("busy".equalsIgnoreCase(input)) {
            //  收到busy切换到忙碌状态:
            state = new BusyState();
            return state.init();
        }

        return state.reply(input);
    }
}