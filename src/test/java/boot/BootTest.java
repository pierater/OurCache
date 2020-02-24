package boot;

import org.junit.Test;

public class BootTest {

    @Test
    public void runs() throws Exception {
        Boot booter = new Boot();
        booter.run(null, null);
    }
}
