package school.project.constants;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {

    @Test
    void testConstantValues() {
        assertEquals("schools", Constants.REDIRECT_SCHOOLS, "REDIRECT_SCHOOLS should be 'schools'");
        assertEquals("create", Constants.CREATE_VIEW, "CREATE_VIEW should be 'create'");
        assertEquals("edit", Constants.EDIT_VIEW, "EDIT_VIEW should be 'edit'");
    }
}