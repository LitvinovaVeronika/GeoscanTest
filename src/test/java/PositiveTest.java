import dto.EmployeeDto;
import dto.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import service.RestConnector;

import static org.junit.Assert.*;


public class PositiveTest implements ServiceTest{

    private static EmployeeDto empl;

    private static EmployeeDto otherEmpl;

    private final static boolean TEST_TYPE = true;

    private void initEmpl() {

        empl = new EmployeeDto();

        String emailId = "nikalitvinova@gmail.com";
        String firstName = "Veronika";
        String lastName = "Litvinova";
        String username = "VERTRON";

        empl.setEmailId(emailId);
        empl.setFirstName(firstName);
        empl.setLastName(lastName);
        empl.setUsername(username);
    }

    private void initOtherEmpl() {

        otherEmpl = new EmployeeDto();

        String emailId = "alex@mail.ru";
        String firstName = "Alexander";
        String lastName = "Po";
        String username = "AlexPo";

        otherEmpl.setEmailId(emailId);
        otherEmpl.setFirstName(firstName);
        otherEmpl.setLastName(lastName);
        otherEmpl.setUsername(username);
    }

    private void checkEmplFromServer(EmployeeDto emplFromServer, EmployeeDto employee) {

        assertNotNull(emplFromServer);
        assertEquals(emplFromServer, employee);
    }

    private void getEmplByIdTest(String user, String password) {

        Response res = RestConnector.getEmployeeById(user, password, empl.getId());
        checkRes(res, TEST_TYPE);
        checkEmplFromServer((EmployeeDto)res.getBody(), empl);
    }

    private void getAllEmplTest(String user, String password) {

        Response res = RestConnector.getAllEmployees(user, password);
        checkRes(res, TEST_TYPE);
        EmployeeDto[] emplFromServer = (EmployeeDto[])res.getBody();

        assertTrue(emplFromServer.length == 2);
        checkEmplFromServer(emplFromServer[0], empl);
        checkEmplFromServer(emplFromServer[1], otherEmpl);
    }

    @Before
    public void setUp() {
        initEmpl();
        createEmpl(empl);
    }

    @After
    public void cleanUp() {

        if (empl != null) {
            deleteEmpl(empl.getId());
            empl = null;
        }
    }

    @Test
    public void createEmplTest() {

        initOtherEmpl();
        createEmpl(otherEmpl);
        deleteEmpl(otherEmpl.getId());
        otherEmpl = null;
    }

    @Test
    public void deleteEmplTest() {
        deleteEmpl(empl.getId());
        empl = null;
    }

    @Test
    public void getEmplByIdTestForAdmin() {
        getEmplByIdTest(ADMIN, ADMIN_PASSWORD);
    }

    @Test
    public void getEmplByIdTestForUser() {
        getEmplByIdTest(USER, USER_PASSWORD);
    }

    @Test
    public void getAllEmplTestForAdmin() {
        initOtherEmpl();
        createEmpl(otherEmpl);
        getAllEmplTest(ADMIN, ADMIN_PASSWORD);
        deleteEmpl(otherEmpl.getId());
    }

    @Test
    public void getAllEmplTestForUser() {
        initOtherEmpl();
        createEmpl(otherEmpl);
        getAllEmplTest(USER, USER_PASSWORD);
        deleteEmpl(otherEmpl.getId());
    }

    @Test
    public void updateEmplTest() {

        empl.setFirstName("Nika");
        empl.setLastName("Li");
        empl.setUsername("VERTRON!");
        empl.setEmailId("nika@gmail.com");


        Response res = RestConnector.updateEmployee(ADMIN, ADMIN_PASSWORD, empl, empl.getId());
        checkRes(res, TEST_TYPE);
        checkEmplFromServer((EmployeeDto)res.getBody(), empl);
    }
}
