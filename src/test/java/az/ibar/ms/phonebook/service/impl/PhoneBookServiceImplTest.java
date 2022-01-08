package az.ibar.ms.phonebook.service.impl;

import az.ibar.ms.phonebook.dto.PhoneBookDto;
import az.ibar.ms.phonebook.dto.PhoneBookResponseDto;
import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import az.ibar.ms.phonebook.repository.PhoneBookRepository;
import az.ibar.ms.phonebook.service.PhoneBookService;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class PhoneBookServiceImplTest {

    @InjectMocks
    PhoneBookServiceImpl phoneBookService;

    @Mock
    private PhoneBookRepository phoneBookRepository;

    private List<PhoneBookEntity> phoneBookEntities = new ArrayList<>();

    private PhoneBookEntity phoneBookEntity;

    private PhoneBookDto phoneBookDto;

    private final String userId = UUID.randomUUID().toString();
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @BeforeEach
    void setUp() {
        phoneBookEntity = new PhoneBookEntity();
        phoneBookEntity.setId(userId);
        phoneBookEntity.setUserName("name");
        phoneBookEntities.add(phoneBookEntity);

        phoneBookDto = new PhoneBookDto();
        phoneBookDto.setUserId(userId);
        phoneBookDto.setName("name");
    }

    @Test
    void getAllUsers() {
        when(phoneBookRepository.findAll()).thenReturn(phoneBookEntities);

        phoneBookService.getAllUsers();
        verify(phoneBookRepository).findAll();
    }

    @Test
    void save() {
        when(phoneBookRepository.save(phoneBookEntity)).thenReturn(phoneBookEntity);

        PhoneBookResponseDto operation = phoneBookService.save(phoneBookDto);

        Assertions.assertThat(operation.getOperationType()).isEqualTo("add");
        Assertions.assertThat(operation.getOperationStatus()).isEqualTo(SUCCESS);
        Assertions.assertThat(operation.getUserId()).isEqualTo(phoneBookEntity.getId());
    }

    @Test
    void saveUserFailCaseTest() {
        when(phoneBookRepository.save(phoneBookEntity)).thenThrow(DuplicateKeyException.class);

        PhoneBookResponseDto operation = phoneBookService.save(phoneBookDto);

        Assertions.assertThat(operation.getOperationType()).isEqualTo("add");
        Assertions.assertThat(operation.getOperationStatus()).isEqualTo(FAIL);
    }

    @Test
    void editUser() {

        when(phoneBookRepository.getPhoneBookEntityById(anyString())).thenReturn(phoneBookEntity);

        PhoneBookResponseDto operation = phoneBookService.edit(userId, phoneBookDto);

        verify(phoneBookRepository).save(any());
        Assertions.assertThat(operation.getOperationType()).isEqualTo("edit");
        Assertions.assertThat(operation.getOperationStatus()).isEqualTo(SUCCESS);
        Assertions.assertThat(operation.getUserId()).isEqualTo(phoneBookEntity.getId());
    }
//
//    @Test
//    public void editUserWhenNoUserFound() {
//
//        when(phoneBookRepository.findById(any())).thenReturn(Optional.empty());
//
//        Operation operation = phoneBookService.editUser(phoneBookEntity);
//
//        verify(phoneBookRepository, never()).save(any());
//        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo(OperationType.EDIT)));
//        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(OperationStatus.FAIL)));
//    }
//
//    @Test
//    public void deleteUser() {
//
//        when(phoneBookRepository.findById(any())).thenReturn(Optional.of(phoneBookEntity));
//
//        Operation operation = phoneBookService.deleteUser(phoneBookEntity.getUserId());
//        verify(phoneBookRepository).delete(any());
//
//        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo(OperationType.DELETE)));
//        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(OperationStatus.SUCCESS)));
//
//    }
//
//    @Test
//    public void deleteUserWhenUserNotFound() {
//
//        when(phoneBookRepository.findById(any())).thenReturn(Optional.empty());
//
//        Operation operation = phoneBookService.deleteUser(phoneBookEntity.getUserId());
//        verify(phoneBookRepository, never()).delete(any());
//
//        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo(OperationType.DELETE)));
//        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(OperationStatus.FAIL)));
//
//    }
//
//    @Test
//    public void deleteUserWhenFailCase() {
//
//        when(phoneBookRepository.findById(any())).thenThrow(DuplicateKeyException.class);
//
//        Operation operation = phoneBookService.deleteUser(phoneBookEntity.getUserId());
//        verify(phoneBookRepository, never()).delete(any());
//
//        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo(OperationType.DELETE)));
//        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(OperationStatus.FAIL)));
//
//    }

}