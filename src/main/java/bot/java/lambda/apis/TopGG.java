package bot.java.lambda.apis;

import bot.java.lambda.config.Config;
import net.dv8tion.jda.api.JDA;
import org.discordbots.api.client.DiscordBotListAPI;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TopGG {

    private static void postServerCount(JDA jda, int serverCount) {
        DiscordBotListAPI api = new DiscordBotListAPI.Builder()
                .token(Config.get("TopGG_Token"))
                .botId(jda.getSelfUser().getId())
                .build();

        api.setStats(serverCount);
    }

    public static void startPostingServerCount(JDA jda, int delay) {
        Runnable postServerCount = () -> {
            final int guildCount = jda.getGuilds().size();
            postServerCount(jda, guildCount);
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        executor.scheduleWithFixedDelay(postServerCount, 2, delay, TimeUnit.MINUTES);
    }

}
