package org.wordcloud;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping(value = "/")
public class WordController {

    @RequestMapping(value = "/wordcloud", method = RequestMethod.GET)
    public Collection<String> executeQuery(@RequestParam String query, @RequestParam String protocol) {
        RestTemplate restTemplate = new RestTemplate();
        Collection<Word> words = (Collection<Word>) restTemplate.getForObject("http://localhost:8090/api?query=%23superbowl&protocol=twitter", Word.class);
        Collection<String> strs = new ArrayList<>();
        for (Word w : words) {
            strs.add(w.getWord());
        }
        return strs;
    }

}