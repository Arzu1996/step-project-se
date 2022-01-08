package az.ibar.ms.phonebook.service.impl;

import az.ibar.ms.phonebook.dto.ApiResponse;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

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

    private ApiResponse apiResponse;

    private final String userId = UUID.randomUUID().toString();
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    List<Object> results = new ArrayList<>();
    List<Object> resultsFail = new ArrayList<>();

    @BeforeEach
    void setUp() {
        phoneBookEntity = new PhoneBookEntity();
        phoneBookEntity.setId(userId);
        phoneBookEntity.setUserName("name");
        phoneBookEntities.add(phoneBookEntity);

        phoneBookDto = new PhoneBookDto();
        phoneBookDto.setUserId(userId);
        phoneBookDto.setName("name");

        results.add(new Object());
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
        when(phoneBookRepository.save(phoneBookEntity)).thenThrow(NullPointerException.class);

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

    @Test
    public void editFailCase() {

        when(phoneBookRepository.getPhoneBookEntityById(anyString())).thenReturn(phoneBookEntity);
        when(phoneBookRepository.save(phoneBookEntity)).thenThrow(NullPointerException.class);

        PhoneBookResponseDto operation = phoneBookService.edit(userId, phoneBookDto);

        verify(phoneBookRepository).save(any());
        Assertions.assertThat(operation.getOperationType()).isEqualTo("edit");
        Assertions.assertThat(operation.getOperationStatus()).isEqualTo(FAIL);
    }

    @Test
    public void deleteUser() {

        PhoneBookResponseDto operation = phoneBookService.delete(userId);
        verify(phoneBookRepository).deleteById(any());
        Assertions.assertThat(operation.getOperationType()).isEqualTo("delete");
        Assertions.assertThat(operation.getOperationStatus()).isEqualTo(SUCCESS);

    }

    @Test
    public void deleteFailCase() {
        Mockito.doThrow(NullPointerException.class).when(phoneBookRepository).deleteById(userId);
        PhoneBookResponseDto operation = phoneBookService.delete(userId);
        verify(phoneBookRepository).deleteById(any());
        Assertions.assertThat(operation.getOperationType()).isEqualTo("delete");
        Assertions.assertThat(operation.getOperationStatus()).isEqualTo(FAIL);

    }

    @Test
    public void dbHealthCheck(){
        when(phoneBookRepository.checkConnection()).thenReturn(results);
        ApiResponse apiResponse = phoneBookService.dbHealthCheck();
        Assertions.assertThat(apiResponse.getStatus()).isEqualTo("ok");

    }

    @Test
    public void dbHealthCheckFailCase(){
        when(phoneBookRepository.checkConnection()).thenThrow(NullPointerException.class);
        ApiResponse apiResponse = phoneBookService.dbHealthCheck();
        Assertions.assertThat(apiResponse.getStatus()).isEqualTo("not ok");

    }

    @Test
    public void dbHealthCheckWhenNull(){
        when(phoneBookRepository.checkConnection()).thenReturn(resultsFail);
        ApiResponse apiResponse = phoneBookService.dbHealthCheck();
        Assertions.assertThat(apiResponse.getStatus()).isEqualTo("not ok");

    }

    @Test
    public void check(){
        when(phoneBookRepository.checkConnection()).thenReturn(results);
        int size = phoneBookService.check();
        Assertions.assertThat(size).isEqualTo(1);

    }

}