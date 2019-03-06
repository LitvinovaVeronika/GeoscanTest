import dto.BooleanDto;
import dto.EmployeeDto;
import dto.Response;
import service.RestConnector;

import static org.junit.Assert.*;


public interface ServiceTest {

    String ADMIN = "admin";
    String ADMIN_PASSWORD = "admin123";
    String USER = "user";
    String USER_PASSWORD = "user123";

    default void checkRes(Response res, boolean testType) {

        assertNotNull(res);
        assertNotNull(res.getOperationResult());

        if (testType && !res.getOperationResult()) {

            System.out.println(res.getMessage());
            assertTrue(res.getOperationResult());
        }
    }

    default void createEmpl(EmployeeDto empl) {

        Response res = RestConnector.createEmployee(ADMIN, ADMIN_PASSWORD, empl);
        checkRes(res, true);

        EmployeeDto emplFromServer = (EmployeeDto)res.getBody();

        assertNotNull(emplFromServer);
        assertNotNull(emplFromServer.getId());

        empl.setId(emplFromServer.getId());
        assertEquals(emplFromServer, empl);
    }

    default void deleteEmpl(Integer id) {

        Response res = RestConnector.deleteEmployee(ADMIN, ADMIN_PASSWORD, id);
        checkRes(res, true);
        BooleanDto confirmDelete = (BooleanDto)res.getBody();

        assertNotNull(confirmDelete);
        assertTrue(confirmDelete.isDeleted());

    }

}
