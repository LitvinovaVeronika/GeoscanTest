import dto.EmployeeDto;
import dto.Response;
import org.junit.After;
import org.junit.Test;
import service.RestConnector;

import static org.junit.Assert.*;

public class NegativeTest implements ServiceTest{

    private static EmployeeDto empl;

    private static EmployeeDto otherEmpl;

    private final static boolean TEST_TYPE = false;

    private void initEmpl() {

        if (empl == null)
            empl = new EmployeeDto();
    }

    private void initOtherEmpl() {

        if (otherEmpl == null)
            otherEmpl = new EmployeeDto();
    }

    private void initIncompleteEmpl() {

        initEmpl();

        String emailId = "koko@gmail.com";
        String username = "Masha";

        empl.setEmailId(emailId);
        empl.setUsername(username);
        empl.setLastName(null);
        empl.setFirstName(null);
    }

    private void initEmptyDataEmpl() {

        initEmpl();

        empl.setFirstName("");
        empl.setUsername("");
        empl.setLastName("");
        empl.setEmailId("");
    }

    private void initNormalEmpl() {

        initEmpl();

        String emailId = "margo@gmail.com";
        String firstName = "Margarita";
        String lastName = "Litvinova";
        String username = "Rita";

        empl.setEmailId(emailId);
        empl.setFirstName(firstName);
        empl.setLastName(lastName);
        empl.setUsername(username);
    }

    private void initOtherNormalEmpl() {

        initOtherEmpl();

        String emailId = "girl_smile@mail.ru";
        String firstName = "Alena";
        String lastName = "Sizova";
        String username = "Lena";

        otherEmpl.setEmailId(emailId);
        otherEmpl.setFirstName(firstName);
        otherEmpl.setLastName(lastName);
        otherEmpl.setUsername(username);
    }

    private void initEmplWithTheSameUsername() {

        initOtherEmpl();

        String emailId = "rita@gmail.com";
        String firstName = "Rita";
        String lastName = "Ivanova";
        String username = "Rita";

        otherEmpl.setEmailId(emailId);
        otherEmpl.setFirstName(firstName);
        otherEmpl.setLastName(lastName);
        otherEmpl.setUsername(username);
    }

    private void initEmplWithTheSameEmailId() {

        initOtherEmpl();

        String emailId = "margo@gmail.com";
        String firstName = "Ro";
        String lastName = "Sidorova";
        String username = "Ro";

        otherEmpl.setEmailId(emailId);
        otherEmpl.setFirstName(firstName);
        otherEmpl.setLastName(lastName);
        otherEmpl.setUsername(username);
    }

    private void checkFalseRes(Response res) {

        EmployeeDto emplFromServer = (EmployeeDto)res.getBody();

        if (res.getOperationResult() && emplFromServer != null && emplFromServer.getId() != null)
            empl.setId(emplFromServer.getId());

        assertFalse(res.getOperationResult());
        assertNull(emplFromServer);
    }

    @After
    public void cleanUp() {

        if (empl != null && empl.getId() != null) {
            deleteEmpl(empl.getId());
            empl = null;
        }

    }

    //CreateTest
    @Test
    public void createIncompleteEmplTest() {

        initIncompleteEmpl();
        Response res = RestConnector.createEmployee(ADMIN, ADMIN_PASSWORD, empl);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void createEmptyEmplTest() {

        Response res = RestConnector.createEmployee(ADMIN, ADMIN_PASSWORD, new EmployeeDto());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void createEmptyDataEmplTest() {

        initEmptyDataEmpl();
        Response res = RestConnector.createEmployee(ADMIN, ADMIN_PASSWORD, empl);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void createEmplByStrangerTest() {

        initNormalEmpl();
        Response res = RestConnector.createEmployee("NoAdmin", "NoPassword", empl);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void createEmplByEmptyUserTest() {

        initNormalEmpl();
        Response res = RestConnector.createEmployee("", "", empl);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void createEmplByUserTest() {

        initNormalEmpl();
        Response res = RestConnector.createEmployee(USER, USER_PASSWORD, empl);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void createAlreadyExistEmplTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.createEmployee(ADMIN, ADMIN_PASSWORD, empl);
        checkRes(res, TEST_TYPE);
        EmployeeDto emplFromServer = (EmployeeDto)res.getBody();

        if (res.getOperationResult() && emplFromServer != null)
            deleteEmpl(emplFromServer.getId());

        assertFalse(res.getOperationResult());
        assertNull(emplFromServer);
    }

    @Test
    public void createEmplWithTheSameUsernameTest() {

        initNormalEmpl();
        createEmpl(empl);

        initEmplWithTheSameUsername();
        Response res = RestConnector.createEmployee(ADMIN, ADMIN_PASSWORD, otherEmpl);
        checkRes(res, TEST_TYPE);
        EmployeeDto emplFromServer = (EmployeeDto)res.getBody();

        if (res.getOperationResult() && emplFromServer != null) {
            deleteEmpl(emplFromServer.getId());
            otherEmpl = null;
        }

        assertFalse(res.getOperationResult());
        assertNull(emplFromServer);
    }

    @Test
    public void createEmplWithTheSameEmailIdTest() {

        initNormalEmpl();
        createEmpl(empl);

        initEmplWithTheSameEmailId();
        Response res = RestConnector.createEmployee(ADMIN, ADMIN_PASSWORD, otherEmpl);
        checkRes(res, TEST_TYPE);
        EmployeeDto emplFromServer = (EmployeeDto)res.getBody();

        if (res.getOperationResult() && emplFromServer != null) {
            deleteEmpl(emplFromServer.getId());
            otherEmpl = null;
        }

        assertFalse(res.getOperationResult());
        assertNull(emplFromServer);
    }

    //GetTest
    @Test
    public void getAllEmplByStrangerTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.getAllEmployees("NoAdmin", "NoPassword");
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void getAllEmplByEmptyUserTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.getAllEmployees("", "");
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void getEmplByStrangerTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.getEmployeeById("NoAdmin", "NoPassword", empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void getEmplByEmptyUserTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.getEmployeeById("", "", empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void getEmplByWrongIdTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.getEmployeeById(ADMIN, ADMIN_PASSWORD, -1);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    //PutTest
    @Test
    public void putEmplByStrangerTest() {

        initNormalEmpl();
        createEmpl(empl);
        empl.setFirstName("Rita");

        Response res = RestConnector.updateEmployee("NoAdmin", "NoPassword", empl, empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void putEmplByEmptyUserTest() {

        initNormalEmpl();
        createEmpl(empl);
        empl.setFirstName("Rita");

        Response res = RestConnector.updateEmployee("", "", empl, empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void putEmplByWrongIdTest() {

        initNormalEmpl();
        createEmpl(empl);
        empl.setFirstName("Rita");

        Response res = RestConnector.updateEmployee(ADMIN, ADMIN_PASSWORD, empl,-1);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void putEmplByUserTest() {

        initNormalEmpl();
        createEmpl(empl);
        empl.setFirstName("Rita");

        Response res = RestConnector.updateEmployee(USER, USER_PASSWORD, empl, empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void putIncompleteEmplTest() {

        initNormalEmpl();
        createEmpl(empl);
        initIncompleteEmpl();

        Response res = RestConnector.updateEmployee(ADMIN, ADMIN_PASSWORD, empl, empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void putEmptyEmplTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.updateEmployee(ADMIN, ADMIN_PASSWORD, new EmployeeDto(), empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void putEmptyDataEmplTest() {

        initNormalEmpl();
        createEmpl(empl);
        initEmptyDataEmpl();

        Response res = RestConnector.updateEmployee(ADMIN, ADMIN_PASSWORD, empl, empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void putEmplWithExistUsernameTest() {

        initNormalEmpl();
        createEmpl(empl);

        initOtherNormalEmpl();
        createEmpl(otherEmpl);

        empl.setUsername(otherEmpl.getUsername());

        Response res = RestConnector.updateEmployee(ADMIN, ADMIN_PASSWORD, empl, empl.getId());
        checkRes(res, TEST_TYPE);
        EmployeeDto emplFromServer = (EmployeeDto)res.getBody();

        deleteEmpl(otherEmpl.getId());
        otherEmpl = null;

        assertFalse(res.getOperationResult());
        assertNull(emplFromServer);
    }

    @Test
    public void putEmplWithExistEmailIdTest() {

        initNormalEmpl();
        createEmpl(empl);

        initOtherNormalEmpl();
        createEmpl(otherEmpl);

        empl.setEmailId(otherEmpl.getEmailId());

        Response res = RestConnector.updateEmployee(ADMIN, ADMIN_PASSWORD, empl, empl.getId());
        checkRes(res, TEST_TYPE);
        EmployeeDto emplFromServer = (EmployeeDto)res.getBody();

        deleteEmpl(otherEmpl.getId());
        otherEmpl = null;

        assertFalse(res.getOperationResult());
        assertNull(emplFromServer);
    }

    //DeleteTest
    @Test
    public void deleteEmplByStrangerTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.deleteEmployee("NoAdmin", "NoPassword", empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void deleteEmplByEmptyUserTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.deleteEmployee("", "", empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void deleteEmplByUserTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.deleteEmployee(USER, USER_PASSWORD, empl.getId());
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void deleteEmplByWrongIdWithFullTableTest() {

        initNormalEmpl();
        createEmpl(empl);

        Response res = RestConnector.deleteEmployee(ADMIN, ADMIN_PASSWORD, -1);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

    @Test
    public void deleteEmplByWrongIdWithEmptyTableTest() {

        Response res = RestConnector.deleteEmployee(ADMIN, ADMIN_PASSWORD, 1);
        checkRes(res, TEST_TYPE);
        checkFalseRes(res);
    }

}
