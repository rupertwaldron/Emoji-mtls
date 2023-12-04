package com.ruppyrup.emojiapi.translator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class EmojiTranslator implements Translator {
    private static final Logger logger = LogManager.getLogger(EmojiTranslator.class);

    private static final List<String> emojis = getStrings();
    private static final Map<String, String> encodedDict = fetchEncodeDictionary();
    private static final Map<String, String> decodeDict = fetchDecodeDictionary();

    @Override
    public String decode(String input) {
        StringBuilder sb = new StringBuilder();


        for (int i = 0; i < input.length() / 2; i++) {
            String string = new StringBuilder().appendCodePoint(
                    input.codePointAt(input.offsetByCodePoints(0, i))).toString();
            logger.debug("Appending value :: " + string);
            sb.append(decodeDict.get(string));
        }
        return sb.toString();
    }

    @Override
    public String encode(String input) {
        StringBuilder sb = new StringBuilder();

        for (char c : input.toLowerCase().toCharArray()) {
            logger.debug("Replacing value :: " + c);
            sb.append(encodedDict.getOrDefault(String.valueOf(c), "😇"));
        }
        return sb.toString();
    }


    private static Map<String, String> fetchEncodeDictionary() {

        return emojis.stream()
                .map(word -> word.split(":"))
                .collect(Collectors.toMap(tuple -> tuple[0], tuple -> tuple[1]));
    }

    private static Map<String, String> fetchDecodeDictionary() {
        return emojis.stream()
                .map(word -> word.split(":"))
                .collect(Collectors.toMap(tuple -> tuple[1], tuple -> tuple[0]));
    }

    private static List<String> getStrings() {
        List<String> symbolMap = new ArrayList<>();

        try (InputStream emojiInputStream = getFileFromResourceAsStream("emoji.dict");
             var streamReader = new InputStreamReader(emojiInputStream, StandardCharsets.UTF_8);
             var reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                symbolMap.add(line);
            }

        } catch (IOException e) {
            System.out.println("Couldn't read emoji.dict :: " + e.getMessage());
        }
        return symbolMap;
    }

    private static InputStream getFileFromResourceAsStream(String fileName) {

        // The class loader that loaded the class
        ClassLoader classLoader = EmojiTranslator.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        // the stream holding the file content
        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }

    }
}
