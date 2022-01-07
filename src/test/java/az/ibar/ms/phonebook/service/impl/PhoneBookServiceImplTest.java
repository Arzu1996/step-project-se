package az.ibar.ms.phonebook.service.impl;


import az.ibar.ms.phonebook.dto.PhoneBookDto;
import az.ibar.ms.phonebook.dto.PhoneBookResponseDto;
import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import az.ibar.ms.phonebook.repository.PhoneBookRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PhoneBookServiceImplTest {

    @InjectMocks
    PhoneBookServiceImpl phoneBookService;

    @Mock
    private PhoneBookRepository phoneBookRepository;

    private final List<PhoneBookEntity> phoneBookEntities = new ArrayList<>();

    private PhoneBookEntity phoneBookEntity;
    private PhoneBookDto phoneBookDto;


    @BeforeEach
    public void setUp() {
        phoneBookEntity = new PhoneBookEntity();
        phoneBookEntity.setUserName("name");
        phoneBookEntities.add(phoneBookEntity);

        phoneBookDto = new PhoneBookDto();
        phoneBookDto.setName("name");
    }

    @Test
    public void getAllUsers() {
        when(phoneBookRepository.findAll()).thenReturn(phoneBookEntities);
        phoneBookService.getAllUsers();
        verify(phoneBookRepository).findAll();
    }

    @Test
    public void addUser() {
        when(phoneBookRepository.save(phoneBookEntity)).thenReturn(phoneBookEntity);

        PhoneBookResponseDto phoneBookResponseDto = phoneBookService.save(phoneBookDto);

        Assertions.assertThat(phoneBookResponseDto.getOperation_type()).isEqualTo("add");
        Assertions.assertThat(phoneBookResponseDto.getOperation_status()).isEqualTo("success");
        Assertions.assertThat(phoneBookResponseDto.getUser_id()).isEqualTo(phoneBookEntity.getId());
    }

    @Test
    public void addUserFailCaseTest() {
        when(phoneBookRepository.save(phoneBookEntity)).thenThrow(DuplicateKeyException.class);

        PhoneBookResponseDto phoneBookResponseDto = phoneBookService.save(phoneBookDto);

        Assertions.assertThat(phoneBookResponseDto.getOperation_type()).isEqualTo("add");
        Assertions.assertThat(phoneBookResponseDto.getOperation_status()).isEqualTo("fail");
    }

}
