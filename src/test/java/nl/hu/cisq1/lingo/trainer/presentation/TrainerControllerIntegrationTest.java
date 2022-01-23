package nl.hu.cisq1.lingo.trainer.presentation;


import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Progress;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Import(CiTestConfiguration.class)
@AutoConfigureMockMvc
public class TrainerControllerIntegrationTest {
    //private static final Predicate<String> WORD_EXISTS = x -> true;
    @MockBean
    private SpringWordRepository wordRepository;
//    @MockBean
//    private GameRepository gameRepository;
    @Autowired
    private MockMvc mockMvc;

    //aangepast voorbeeld van school
    @Test
    @DisplayName("start a new game")
    void startNewGame() throws Exception {
        when(wordRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("baard")));
        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/games");
        String expectedHint = "b...." ;
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus").value("PLAYING"))
                .andExpect(jsonPath("$.score").value(0))
                .andExpect(jsonPath("$.hint").value(expectedHint))
                .andExpect(jsonPath("$.feedbacks", hasSize(0)));

    }

}