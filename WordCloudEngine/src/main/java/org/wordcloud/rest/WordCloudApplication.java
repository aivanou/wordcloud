package org.wordcloud.rest;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.wordcloud.handler.WordProcessor;
import org.wordcloud.objects.Word;
import org.wordcloud.protocol.ProtocolFactory;

import java.io.IOException;
import java.io.InputStream;


public class WordCloudApplication extends Application<WordCloudConfiguration> {

    @Override
    public String getName() {
        return "org/wordcloud";
    }

    @Override
    public void initialize(Bootstrap<WordCloudConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(WordCloudConfiguration configuration,
                    Environment environment) {
        String config = "org.wordcloud.properties";
        String springConfig = "org.wordcloud.xml";
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            try (InputStream resourceStream = loader.getResourceAsStream(config)) {
                System.getProperties().load(resourceStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(String.valueOf(loader.getResource(springConfig)));
        ProtocolFactory protocolFactory = (ProtocolFactory) context.getBean("protocolFactory");
        WordProcessor<Word> wordProcessor = (WordProcessor<Word>) context.getBean("wordProcessor");
        final WordResource resource = new WordResource(protocolFactory, wordProcessor);
        environment.jersey().register(resource);
        ProtocolManager pm = new ProtocolManager();
        environment.lifecycle().manage(pm);
    }

    public static void main(String[] args) throws Exception {
        new WordCloudApplication().run(args);
    }

}
