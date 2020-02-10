package Bundle;

import static org.junit.Assert.assertEquals;
import info.revenberg.domain.Bundle;
import org.junit.Test;

public class BundleTest {

    @Test
    public void testGreet() {
        Bundle Bundle = new Bundle("world");
        assertEquals(Bundle.getName(), "world");
    }

}
