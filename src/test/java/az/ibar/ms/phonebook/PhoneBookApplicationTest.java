package az.ibar.ms.phonebook;

import az.ibar.ms.phonebook.service.PhoneBookService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@EnableAutoConfiguration(exclude = {LiquibaseAutoConfiguration.class, DataSourceAutoConfiguration.class})
@SpringBootTest(classes = PhoneBookApplication.class)
public class PhoneBookApplicationTest {
    @MockBean
    private PhoneBookService phoneBookService;

    @Test
    public void contextLoads() {
        Assertions.assertThat(phoneBookService).isNotNull();
    }
}
