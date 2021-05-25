package bot.java.lambda.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringUtils {
    private static final List<Character> NORMAL_TEXT = "abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ ɐqɔpǝɟɓɥᴉɾʞlɯuodbɹsʇnʌʍxʎz ⱯᗺƆᗡƎℲ⅁HIᒋꓘ⅂WNOԀტᴚS⊥∩ΛMX⅄Z _,.?!/\\‾'˙¿¡/\\ 0⇂ᘔƐㄣ59ㄥ86⅋>< 0123456789&<>".chars().mapToObj(e -> (char) e).collect(Collectors.toSet()).stream().toList();
    private static final List<Character> FLIPPED_TEXT = "ɐqɔpǝɟɓɥᴉɾʞlɯuodbɹsʇnʌʍxʎz ⱯᗺƆᗡƎℲ⅁HIᒋꓘ⅂WNOԀტᴚS⊥∩ΛMX⅄Z abcdefghijklmnopqrstuvwxyz ABCDEFGHIJKLMNOPQRSTUVWXYZ ‾'˙¿¡/\\_,.?!/\\ 0123456789&<> 0⇂ᘔƐㄣ59ㄥ86⅋><".chars().mapToObj(e -> (char) e).collect(Collectors.toSet()).stream().toList();

    private static final Map<Character, Character> LETTER_PAIRS = IntStream.range(0, NORMAL_TEXT.size())
            .boxed()
            .collect(Collectors.toMap(NORMAL_TEXT::get, FLIPPED_TEXT::get));

    private static final String PROFANITY_URL = "https://raw.githubusercontent.com/RobertJGabriel/Google-profanity-words/master/list.txt";
    private static final List<String> profanityWords = new ArrayList<>();

    static {
        try {
            final URL url = new URL(PROFANITY_URL);
            final BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            in.lines().forEach(profanityWords::add);
            in.close();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static String flipText(String text) {
        return text.chars()
                .mapToObj(p -> {
                    final char c = (char) p;
                    return LETTER_PAIRS.getOrDefault(c, c);
                })
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .reverse()
                .toString();
    }

    public static boolean hasProfanity(String text) {
        return profanityWords.stream().anyMatch(text::contains);
    }
}
