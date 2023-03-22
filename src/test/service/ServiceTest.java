package service;

import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    // create 2 tests for saving a student
    // one test should pass, the other should fail
    // the test that fails should throw an exception
    // the test that passes should return 1

    private StudentXMLRepository studentXmlRepo;
    private TemaXMLRepository temaXmlRepo;
    private NotaXMLRepository notaXmlRepo;

    @Test
    void saveStudent() {
        // create a service
        // call saveStudent
        // assert that the result is 1

        Service service = new Service(studentXmlRepo, temaXmlRepo, notaXmlRepo);
        int result = service.saveStudent("1", "name", 1);
        assertEquals(1, result);

    }

    @Test
    void saveStudentFail() {
        // create a service
        // call saveStudent
        // assert that the result is 1

        Service service = new Service(studentXmlRepo, temaXmlRepo, notaXmlRepo);
        int result = service.saveStudent("1", "name", 1);
        assertEquals(0, result);
    }

}