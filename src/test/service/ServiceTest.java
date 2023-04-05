package service;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import validation.NotaValidator;
import validation.StudentValidator;
import validation.TemaValidator;
import validation.Validator;

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

    Service service;
    @Before
    public void init(){
        TemaXMLRepository fileRepository1 = new TemaXMLRepository(new TemaValidator(), "teme.xml");
        StudentXMLRepository fileRepository2 = new StudentXMLRepository(new StudentValidator(), "studenti.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(new NotaValidator(), "note.xml");
        service = new Service(fileRepository2, fileRepository1, fileRepository3);
    }
    @Test
    public void testAddValidStudent() {
        assertEquals(0, service.saveStudent(String.valueOf(98),"Horia",935));
    }

    @Test
    public void testAddStudentWithNegativeID() {
        assertEquals(1, service.saveStudent(String.valueOf(-1),"Horia",935));
    }

    @Test
    public void testAddStudentWithExistingID() {
        assertEquals(0, service.saveStudent(String.valueOf(99),"Horia",935));
        assertEquals(1, service.saveStudent(String.valueOf(99),"Nistor",935));
    }

    @Test
    public void testAddStudentWithEmptyID() {
        assertEquals(1, service.saveStudent("","Horia",935));
    }

    @Test
    public void testAddStudentWithNullID() {
        assertEquals(1, service.saveStudent(null,"Horia",935));
    }

    @Test
    public void testAddStudentWithEmptyName() {
        assertEquals(1, service.saveStudent(String.valueOf(98),"",935));
    }

    @Test
    public void testAddStudentWithInvalidName() {
        assertEquals(1, service.saveStudent(String.valueOf(98),null,935));
    }

    @Test
    public void testAddStudentWithGroupToSmall() {
        assertEquals(1, service.saveStudent(String.valueOf(98),"Horia",110));
    }

    @Test
    public void testAddStudentWithGroupToBig() {
        assertEquals(1, service.saveStudent(String.valueOf(98),"Horia",938));
    }

    @After
    public void cleanUp() {
        service.deleteStudent("98");
        service.deleteStudent("99");
    }

}