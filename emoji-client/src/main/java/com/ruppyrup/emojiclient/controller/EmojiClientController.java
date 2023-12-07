package com.ruppyrup.emojiclient.controller;

import com.ruppyrup.emojiclient.model.NoteData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("emoji-client")
public class EmojiClientController {

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
        modelAndView.addObject("noteData", noteData);
        return modelAndView;
    }

    @PostMapping("/decrypt")
    public ModelAndView decrypt(@ModelAttribute NoteData noteData) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index2");
        modelAndView.addObject("noteData", noteData);
        return modelAndView;
    }

    @GetMapping("/encode/{text}")
    public String encode(@PathVariable String text) {
        String response = restTemplate.getForObject("https://localhost:8443/emoji/encode/" + text,
                String.class);
        logger.info("Encode response = {}", response);
        return response;
    }

    @GetMapping("/decode/{emojis}")
    public String decode(@PathVariable String emojis) {
        String response = restTemplate.getForObject("https://localhost:8443/emoji/decode/" + emojis,
                String.class);
        logger.info("Decode response = {}", response);
        return response;
    }
}
