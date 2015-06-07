package org.wordcloud.handler;

import org.junit.Test;
import org.mockito.exceptions.verification.TooManyActualInvocations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.wordcloud.handler.impl.WordProcessorImpl;
import org.wordcloud.handler.transformer.WordTransformerChain;
import org.wordcloud.objects.Word;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;


public class WordProcessorImplTest {

    private WordTransformerChain chain = mock(WordTransformerChain.class);
    private WordProcessor<Word> wordProcessor = new WordProcessorImpl(chain);

    @Test
    public void transformerChainCalledTest() {
        try {
            when(chain.transformChain("test")).thenReturn("test");
            wordProcessor.process(new StringReader("test"));
            verify(chain).transformChain("test");
        } catch (IOException e) {
            fail(e.toString());
        }
    }

    @Test(expected = NullPointerException.class)
    public void nullTest() throws IOException {
        wordProcessor.process(null);
    }


    @Test
    public void emptyTest() {
        String line = "     ";
        when(chain.transformChain("")).thenReturn("");
        try {
            Set<Word> words = wordProcessor.process(new StringReader(line));
            assertEquals(words.size(), 0);
        } catch (IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void wordOrderTest() {
        String line = "a b c d a a b c a a b d";
        final Map<String, Integer> counter = count(line);
        for (final String w : line.split(" ")) {
            when(chain.transformChain(w)).thenAnswer(new Answer<String>() {
                private int count = 0;

                @Override
                public String answer(InvocationOnMock invocationOnMock) throws Throwable {
                    if (counter.get(w) <= count) {
                        throw new TooManyActualInvocations("too many invokations for word: " + w);
                    }
                    count += 1;
                    return w;
                }
            });

        }
        try {
            when(chain.transformChain("test")).thenReturn("test");
            Set<Word> words = wordProcessor.process(new StringReader(line));
            Iterator<Word> it = words.iterator();
            Word prev = it.next();
            while (it.hasNext()) {
                Word word = it.next();
                assertTrue(prev.getCount() >= word.getCount());
                prev = word;
            }
            assertEquals(words.size(), 4);
            for (String w : line.split(" ")) {
                verify(chain, times(counter.get(w))).transformChain(w);
            }
        } catch (IOException e) {
            fail(e.toString());
        }
    }

    private Map<String, Integer> count(String line) {
        Map<String, Integer> countMap = new HashMap<>();
        for (String w : line.split(" ")) {
            if (!countMap.containsKey(w)) {
                countMap.put(w, 1);
            } else {
                countMap.put(w, countMap.get(w) + 1);
            }
        }
        return countMap;
    }


}
