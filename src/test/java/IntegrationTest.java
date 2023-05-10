import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class IntegrationTest {
    private Service service;
    private StudentXMLRepository studentXMLRepository = mock(StudentXMLRepository.class);
    private TemaXMLRepository temaXMLRepository = mock(TemaXMLRepository.class);
    private NotaXMLRepository notaXMLRepository = mock(NotaXMLRepository.class);

    @BeforeEach
    public void setUp() {
        service = new Service(studentXMLRepository, temaXMLRepository, notaXMLRepository);
    }

    @Test
    public void addStudent() {
        when(studentXMLRepository.save(any())).thenReturn(null);
        service.saveStudent("1", "name", 935);
    }

    @Test
    public void addAssignment() {
        when(temaXMLRepository.save(any())).thenReturn(null);
        when(studentXMLRepository.save(any())).thenReturn(null);
        service.saveStudent("2", "name", 935);
        service.saveTema(String.valueOf(31), "Tema 1", 8, 7);
    }

    @Test
    public void addGrade() {
        when(temaXMLRepository.save(any())).thenReturn(null);
        when(studentXMLRepository.save(any())).thenReturn(null);
        when(notaXMLRepository.save(any())).thenReturn(null);
        service.saveStudent("3", "name", 935);
        service.saveTema(String.valueOf(32), "Tema 1", 8, 7);
        service.saveNota(String.valueOf(3), String.valueOf(32), 7.8, 10, "feedback");
    }
}
