package az.ibar.ms.phonebook.service.impl;

import az.ibar.ms.phonebook.dto.ApiResponse;
import az.ibar.ms.phonebook.dto.PhoneBookDto;
import az.ibar.ms.phonebook.dto.PhoneBookResponseDto;
import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import az.ibar.ms.phonebook.repository.PhoneBookRepository;
import org.hamcrest.MatcherAssert;
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

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
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

    private final List<PhoneBookEntity> phoneBookEntities = new ArrayList<>();

    private PhoneBookEntity phoneBookEntity;

    private PhoneBookDto phoneBookDto;

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

        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo("add")));
        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(SUCCESS)));
        MatcherAssert.assertThat(operation.getUserId(), is(equalTo(phoneBookEntity.getId())));
    }

    @Test
    void saveUserFailCaseTest() {
        when(phoneBookRepository.save(phoneBookEntity)).thenThrow(NullPointerException.class);

        PhoneBookResponseDto operation = phoneBookService.save(phoneBookDto);

        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo("add")));
        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(FAIL)));
    }

    @Test
    void editUser() {

        when(phoneBookRepository.getPhoneBookEntityById(anyString())).thenReturn(phoneBookEntity);

        PhoneBookResponseDto operation = phoneBookService.edit(userId, phoneBookDto);

        verify(phoneBookRepository).save(any());
        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo("edit")));
        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(SUCCESS)));
        MatcherAssert.assertThat(operation.getUserId(), is(equalTo(phoneBookEntity.getId())));
    }

    @Test
    void editFailCase() {

        when(phoneBookRepository.getPhoneBookEntityById(anyString())).thenReturn(phoneBookEntity);
        when(phoneBookRepository.save(phoneBookEntity)).thenThrow(NullPointerException.class);

        PhoneBookResponseDto operation = phoneBookService.edit(userId, phoneBookDto);

        verify(phoneBookRepository).save(any());
        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo("edit")));
        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(FAIL)));
    }

    @Test
    void deleteUser() {

        PhoneBookResponseDto operation = phoneBookService.delete(userId);
        verify(phoneBookRepository).deleteById(any());
        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo("delete")));
        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(SUCCESS)));

    }

    @Test
    void deleteFailCase() {
        Mockito.doThrow(NullPointerException.class).when(phoneBookRepository).deleteById(userId);
        PhoneBookResponseDto operation = phoneBookService.delete(userId);
        verify(phoneBookRepository).deleteById(any());
        MatcherAssert.assertThat(operation.getOperationType(), is(equalTo("delete")));
        MatcherAssert.assertThat(operation.getOperationStatus(), is(equalTo(FAIL)));

    }

    @Test
    void dbHealthCheck(){
        when(phoneBookRepository.checkConnection()).thenReturn(results);
        ApiResponse apiResponse = phoneBookService.dbHealthCheck();
        MatcherAssert.assertThat(apiResponse.getStatus(), is(equalTo("ok")));

    }

    @Test
    void dbHealthCheckFailCase(){
        when(phoneBookRepository.checkConnection()).thenThrow(NullPointerException.class);
        ApiResponse apiResponse = phoneBookService.dbHealthCheck();
        MatcherAssert.assertThat(apiResponse.getStatus(), is(equalTo("not ok")));

    }

    @Test
    void dbHealthCheckWhenNull(){
        when(phoneBookRepository.checkConnection()).thenReturn(resultsFail);
        ApiResponse apiResponse = phoneBookService.dbHealthCheck();
        MatcherAssert.assertThat(apiResponse.getStatus(), is(equalTo("not ok")));

    }

    @Test
    void check(){
        when(phoneBookRepository.checkConnection()).thenReturn(results);
        int size = phoneBookService.check();
        MatcherAssert.assertThat(size, is(equalTo(1)));

    }

}