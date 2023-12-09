package com.ruppyrup.emojiclient.controller;

import com.ruppyrup.emojiclient.model.NoteData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriBuilderFactory;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@Controller
@RequestMapping("emoji-client")
public class EmojiClientController {

    @Value("${emoji.port}")
    private int emojiPort;

    @Value("${emoji.scheme}")
    private String scheme;

    private final RestTemplate restTemplate;

    private static final Logger logger = LogManager.getLogger(EmojiClientController.class);

    public EmojiClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/")
    public String index(Model model) {
        NoteData noteData = new NoteData();
        model.addAttribute("noteData", noteData);
        return "index2";
    }
    @PostMapping("/encrypt")
    public ModelAndView encrypt(@ModelAttribute NoteData noteData) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index2");

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host("localhost")
                .port(emojiPort)
                .path("/emoji/encode/" + noteData.getPlainText())
                .build();

        String encrypted = restTemplate.getForObject(uriComponents.toString(), String.class);
        logger.info("Encode response = {}", encrypted);
        noteData.setEncrypted(encrypted);
        modelAndView.addObject("noteData", noteData);
        return modelAndView;
    }

    @PostMapping("/decrypt")
    public ModelAndView decrypt(@ModelAttribute NoteData noteData) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index2");

        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .scheme(scheme)
                .host("localhost")
                .port(emojiPort)
                .path("/emoji/decode/" + noteData.getEncrypted())
                .build();

        String decrypted = restTemplate.getForObject(uriComponents.toString(), String.class);
        logger.info("Decode response = {}", decrypted);
        noteData.setPlainText(decrypted);
        modelAndView.addObject("noteData", noteData);
        return modelAndView;
    }

//    @GetMapping("/encode/{text}")
//    public String encode(@PathVariable String text) {
//        String response = restTemplate.getForObject("https://localhost:8443/emoji/encode/" + text,
//                String.class);
//        logger.info("Encode response = {}", response);
//        return response;
//    }
//
//    @GetMapping("/decode/{emojis}")
//    public String decode(@PathVariable String emojis) {
//        String response = restTemplate.getForObject("https://localhost:8443/emoji/decode/" + emojis,
//                String.class);
//        logger.info("Decode response = {}", response);
//        return response;
//    }
}
