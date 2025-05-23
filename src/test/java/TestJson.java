import org.junit.jupiter.api.Test;

public class TestJson {

    @Test
    void name() {
        String str = "he said \"hi\"";

        String replace = str.replace("\\", "\\\\").replace("\"", "\\\"");
        System.out.println(replace);

    }
}
