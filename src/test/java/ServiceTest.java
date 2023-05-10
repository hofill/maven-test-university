import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import repository.NotaXMLRepository;
import repository.StudentXMLRepository;
import repository.TemaXMLRepository;
import service.Service;
import validation.*;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;

public class ServiceTest {

    // create 2 tests for saving a student
    // one test should pass, the other should fail
    // the test that fails should throw an exception
    // the test that passes should return 1

    static Service service;

    @Before
    public void init() {
        TemaXMLRepository fileRepository1 = new TemaXMLRepository(new TemaValidator(), "teme.xml");
        StudentXMLRepository fileRepository2 = new StudentXMLRepository(new StudentValidator(), "studenti.xml");
        NotaXMLRepository fileRepository3 = new NotaXMLRepository(new NotaValidator(), "note.xml");
        service = new Service(fileRepository2, fileRepository1, fileRepository3);
    }

    @Test
    public void testAddValidStudent() {
        assertEquals(0, service.saveStudent(String.valueOf(98), "Horia", 935));
    }

    @Test
    public void testAddStudentWithNegativeID() {
        assertEquals(1, service.saveStudent(String.valueOf(-1), "Horia", 935));
    }

    @Test
    public void testAddStudentWithExistingID() {
        assertEquals(0, service.saveStudent(String.valueOf(99), "Horia", 935));
        assertEquals(1, service.saveStudent(String.valueOf(99), "Nistor", 935));
    }

    @Test
    public void testAddStudentWithEmptyID() {
        assertThrows(ValidationException.class, () -> service.saveStudent("", "Horia", 935));
    }

    @Test
    public void testAddStudentWithNullID() {
        assertThrows(ValidationException.class, () -> service.saveStudent(null, "Horia", 935));
    }

    @Test
    public void testAddStudentWithEmptyName() {
        assertThrows(ValidationException.class, () -> service.saveStudent(String.valueOf(98), "", 935));
    }

    @Test
    public void testAddStudentWithInvalidName() {
        assertThrows(ValidationException.class, () -> service.saveStudent(String.valueOf(98), null, 935));
    }

    @Test
    public void testAddStudentWithGroupToSmall() {
        assertThrows(ValidationException.class, () -> service.saveStudent(String.valueOf(98), "Horia", 110));
    }

    @Test
    public void testAddStudentWithGroupToBig() {
        assertThrows(ValidationException.class, () -> service.saveStudent(String.valueOf(98), "Horia", 938));
    }

    @Test
    public void testAddValidTema() {
        assertEquals(0, service.saveTema(String.valueOf(98), "tema 1", 5, 2));
    }

    @Test
    public void testAddInvalidTema() {
        // assert throws ValidationException
        assertThrows(ValidationException.class, () -> {
            service.saveTema(String.valueOf(98), "tema 1", 15, 2);
        });
    }

    @Test
    public void testAddTemaInvalidDescription() {
        // assert throws ValidationException
        assertThrows(ValidationException.class, () -> {
            service.saveTema(String.valueOf(98), "", 5, 2);
        });
    }

    @Test
    public void testAddTemaWithNegativeID() {
        assertThrows(ValidationException.class, () -> service.saveTema(String.valueOf(-1), "tema 1", 5, 2));
    }

    @Test
    public void testAddTemaWithExistingID() {
        assertEquals(0, service.saveTema(String.valueOf(99), "tema 1", 5, 2));
        assertEquals(1, service.saveTema(String.valueOf(99), "tema 2", 5, 2));
    }

    @Test
    public void testBigBang() {
        testAddStudentBigBang();
        testAddAssignmentBigBang();
        service.saveTema(String.valueOf(98), "tema 1", 5, 2);
        service.saveStudent(String.valueOf(98), "Horia", 935);
        testAddGradeBigBang();
        service.deleteStudent("98");
        service.deleteTema("98");
    }


    public void testAddStudentBigBang() {
        assertEquals(0, service.saveStudent(String.valueOf(98), "Horia", 935));
        assertEquals(0, service.saveStudent(String.valueOf(99), "Horia", 935));
        assertEquals(1, service.saveStudent(String.valueOf(99), "Nistor", 935));
        assertThrows(ValidationException.class, () -> service.saveStudent("", "Horia", 935));
        assertThrows(ValidationException.class, () -> service.saveStudent(null, "Horia", 935));
        assertThrows(ValidationException.class, () -> service.saveStudent(String.valueOf(98), "", 935));
        assertThrows(ValidationException.class, () -> service.saveStudent(String.valueOf(98), null, 935));
    }

    public void testAddGradeBigBang() {
        assertEquals(1, service.saveNota(String.valueOf(98), String.valueOf(98), 10, 2, "feedback"));
        assertEquals(1, service.saveNota(String.valueOf(98), String.valueOf(98), 10, 2, "feedback"));
    }

    public void testAddAssignmentBigBang() {
        assertEquals(0, service.saveTema(String.valueOf(98), "tema 1", 5, 2));
        assertEquals(1, service.saveTema(String.valueOf(98), "tema 2", 5, 2));
        assertThrows(ValidationException.class, () -> service.saveTema(String.valueOf(98), "", 5, 2));
        assertThrows(ValidationException.class, () -> service.saveTema(String.valueOf(98), "tema 1", 15, 2));
        assertThrows(ValidationException.class, () -> service.saveTema(String.valueOf(98), "tema 1", 5, 15));
    }


    @After
    public void cleanUp() {
        service.deleteStudent("98");
        service.deleteStudent("99");
        service.deleteTema("98");
        service.deleteTema("99");
    }

}