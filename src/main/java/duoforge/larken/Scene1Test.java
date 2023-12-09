package duoforge.larken;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class Scene1Test {

    private Scene1 scene1;

    @Before
    public void setUp() {
        scene1 = new Scene1();
    }

    @Test
    public void testInitialize() {
        scene1.initialize(null, null);

        // Verify that the GamePlatform instance is properly initialized
        assertNotNull(scene1.pillars);
        assertNotNull(scene1.pillars.platforms);
        assertEquals(3, scene1.pillars.platforms.size());

        // You may need to add more verifications based on your actual implementation
    }
}
