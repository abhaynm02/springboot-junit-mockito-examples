import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rest.app.controller.BookController;
import com.rest.app.model.Book;
import com.rest.app.repository.BookRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class BookControllerTest {
    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();
    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookController bookController;

    Book RECORD_1 =new Book(1L,"Atomic Habits","how to build better habits",5);
    Book RECORD_2 = new Book(2L,"Rejection prof","how over come from fear of rejection ",5);
    Book RECORD_3 = new Book(3L,"How to survive  ","how can we survive a negative situation",5);

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.mockMvc= MockMvcBuilders.standaloneSetup(bookController).build();
    }
    @Test
    public void getAllRecords_success()throws Exception{
        List<Book> records= new ArrayList<>(Arrays.asList(RECORD_1,RECORD_2,RECORD_3));
        Mockito.when(bookRepository.findAll()).thenReturn(records);
        ResultMatcher jsonMatcher = jsonPath("$[1].name").value("Rejection prof");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/book")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$",hasSize(3)))
                .andExpect(jsonMatcher);

    }

    @Test
    public void getBookById_success()throws Exception{
        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(java.util.Optional.of(RECORD_1));
        ResultMatcher jsonMatcher = jsonPath("$.name").value("Atomic Habits");
        mockMvc.perform(MockMvcRequestBuilders
                .get("/book/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonMatcher);
    }
    @Test
    public void createRecord_success()throws Exception{
        Book record = new Book(4L,"hey","it is a book on hey ",4);
        Mockito.when(bookRepository.save(record)).thenReturn(record);
        String content =objectWriter.writeValueAsString(record);
        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        ResultMatcher jsonMatcher = jsonPath("$.name").value("hey");
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",notNullValue()))
                .andExpect(jsonMatcher);

    }
    @Test
    public void updateBookRecord_success()throws Exception{
        Book record = new Book(1L,"updatedName","updated Summery",2);
        Mockito.when(bookRepository.findById(RECORD_1.getBookId())).thenReturn(Optional.ofNullable(RECORD_1));
        Mockito.when(bookRepository.save(record)).thenReturn(record);
        String updatedContent= objectWriter.writeValueAsString(record);

        MockHttpServletRequestBuilder mockRequest =MockMvcRequestBuilders.put("/book")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updatedContent);
        ResultMatcher jsonMatcher = jsonPath("$.name").value("updatedName");
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonMatcher);
    }

    @Test
    public void deleteBookById_success()throws Exception{
        Mockito.when(bookRepository.findById(RECORD_2.getBookId())).thenReturn(Optional.of(RECORD_2));
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/book/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
