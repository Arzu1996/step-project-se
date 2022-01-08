package az.ibar.ms.phonebook.service.impl;

import az.ibar.ms.phonebook.dto.ApiResponse;
import az.ibar.ms.phonebook.dto.PhoneBookDto;
import az.ibar.ms.phonebook.dto.PhoneBookResponseDto;
import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import az.ibar.ms.phonebook.repository.PhoneBookRepository;
import az.ibar.ms.phonebook.service.PhoneBookService;
import lombok.AllArgsConstructor;
import org.hibernate.tool.schema.ast.SqlScriptParserException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneBookServiceImpl implements PhoneBookService {

    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";
    private final PhoneBookRepository phoneBookRepository;


    @Override
    public PhoneBookResponseDto save(PhoneBookDto phoneBookDto) {
        PhoneBookEntity phoneBookEntity = PhoneBookEntity.builder()
                .phoneNumber(phoneBookDto.getPhone())
                .id(phoneBookDto.getUserId())
                .userName(phoneBookDto.getName())
                .build();
        try {
            phoneBookRepository.save(phoneBookEntity);
            return PhoneBookResponseDto.builder().operationType("add")
                    .userId(phoneBookDto.getUserId())
                    .operationStatus(SUCCESS)
                    .build();
        } catch (Exception e) {
            return PhoneBookResponseDto.builder().operationType("add")
                    .userId(phoneBookDto.getUserId())
                    .operationStatus(FAIL)
                    .build();
        }
    }

    @Override
    public PhoneBookResponseDto edit(String userId, PhoneBookDto phoneBookDto) {
        PhoneBookEntity phoneBookEntity = phoneBookRepository.getPhoneBookEntityById(userId);
        phoneBookEntity.setPhoneNumber(phoneBookDto.getPhone());
        phoneBookEntity.setUserName(phoneBookDto.getName());
        try {
            phoneBookRepository.save(phoneBookEntity);
            return PhoneBookResponseDto.builder().operationType("edit")
                    .userId(userId)
                    .operationStatus(SUCCESS)
                    .build();
        } catch (Exception e) {
            return PhoneBookResponseDto.builder().operationType("edit")
                    .userId(userId)
                    .operationStatus(FAIL)
                    .build();
        }
    }


    @Override
    public PhoneBookResponseDto delete(String userId) {
        phoneBookRepository.deleteById(userId);

        return PhoneBookResponseDto.builder().operationType("delete")
                    .userId(userId)
                    .operationStatus(SUCCESS)
                    .build();

    }


    @Override
    public List<PhoneBookEntity> getAllUsers() {
        return phoneBookRepository.findAll();
    }


    @Override
    public ApiResponse dbHealthCheck(){
        List<Object> results = phoneBookRepository.checkConnection();
        try {
            int errorCode = results.size();
            if (errorCode != 1)
                return new ApiResponse("not ok");
            return new ApiResponse("ok");
        } catch (Exception ex) {
            return new ApiResponse("not ok");
        }
    }
}


