import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringExclude;
import org.junit.Test;

public class TestProduct {

    @Test
    public void test01(){
        String s = "0-9";
        String[] split = s.split("-");
        for (int i = 0; i < split.length; i++) {
            System.out.println("split[i] = " + split[i]);
        }
    }
}
