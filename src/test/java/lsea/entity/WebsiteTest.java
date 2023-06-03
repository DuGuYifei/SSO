package lsea.entity;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.UUID;
import lsea.LaboratoryApplication;
import lsea.dto.CreateWebsiteDto;
import lsea.utils.RandomBase64Generator;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.*;

/**
 * This is a JUnit test class for the Website class.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { LaboratoryApplication.class })
public class WebsiteTest {

  /**
   * The user object.
   */
  private User user;

  /**
   * Set up the test data before each test case.
   */
  @BeforeEach
  public void setup() {
    user = new User();
    user.setId(UUID.randomUUID());
  }

  /**
   * Test the getters and setters for the id field.
   */
  @Test
  public void testCreateWebsite() {
    CreateWebsiteDto createWebsiteDto = CreateWebsiteDto.builder()
            .displayName("Test Website")
            .redirectUrl("https://example.com")
            .build();

    Website website = Website.create(createWebsiteDto, user);

    assertNotNull(website.getId());
    assertEquals(createWebsiteDto.getDisplayName(), website.getDisplayName());
    assertEquals(user, website.getUser());
    assertNotNull(website.getCreatedAt());
    assertEquals(createWebsiteDto.getRedirectUrl(), website.getRedirectUrl());
    assertNotNull(website.getPrivateKey());
    assertFalse(website.getIsActive());
    assertEquals(website.getCreatedAt(), website.getUpdatedAt());
  }

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
        if (field.getName().equals("user"))
          continue;
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
