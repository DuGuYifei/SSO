package lsea.entity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;
import lsea.LaboratoryApplication;
import lsea.utils.RandomBase64Generator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * This is a JUnit test class for the Website class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LaboratoryApplication.class })
public class WebsiteTest {

  /**
   * Test the deep clone method for the Website class.
   */
  @Test
  public void testDeepClone() {
    Website website = new Website();
    website.setId(UUID.randomUUID());
    website.setDisplayName("Example");
    website.setUser(new User());
    website.setCreatedAt(new Date());
    website.setRedirectUrl("https://www.example.com");
    website.setPrivateKey(RandomBase64Generator.generateLong());
    website.setIsActive(true);
    website.setUpdatedAt(new Date());

    Website cloned = (Website) website.clone();
    assert cloned != website;
    assert cloned.hashCode() == website.hashCode();
    Field[] fields = Website.class.getDeclaredFields();
    for (Field field : fields) {
      try {
        field.setAccessible(true);
        if(field.getName().equals("user")) continue;
        assert field.get(cloned) != field.get(website);
        System.out.println(
            field.getName() +
                ":\n" +
                field.get(cloned) +
                "\n" +
                field.get(website) +
                "\n");
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
}
