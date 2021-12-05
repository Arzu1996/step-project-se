package az.ibar.ms.phonebook.service.impl;

import az.ibar.ms.phonebook.dto.ApiResponse;
import az.ibar.ms.phonebook.dto.PhoneBookDto;
import az.ibar.ms.phonebook.dto.PhoneBookResponseDto;
import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import az.ibar.ms.phonebook.repository.PhoneBookRepository;
import az.ibar.ms.phonebook.service.PhoneBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneBookServiceImpl implements PhoneBookService {

    private final PhoneBookRepository phoneBookRepository;

    @Override
    public PhoneBookResponseDto save(PhoneBookDto phoneBookDto){
        PhoneBookEntity phoneBookEntity = PhoneBookEntity.builder()
                .phoneNumber(phoneBookDto.getPhone())
                .id(phoneBookDto.getUserId())
                .userName(phoneBookDto.getName())
                .build();
        try {
            phoneBookRepository.save(phoneBookEntity);
            return PhoneBookResponseDto.builder().operation_type("add")
                    .user_id(phoneBookDto.getUserId())
                    .operation_status("success")
                    .build();
        }
        catch (Exception e){
            return PhoneBookResponseDto.builder().operation_type("add")
                    .user_id(phoneBookDto.getUserId())
                    .operation_status("fail")
                    .build();
        }
    }

    @Override
    public PhoneBookResponseDto edit(String userId, PhoneBookDto phoneBookDto) {
        PhoneBookEntity phoneBookEntity = phoneBookRepository.findById(userId).orElseThrow();
        phoneBookEntity.setPhoneNumber(phoneBookDto.getPhone());
        phoneBookEntity.setUserName(phoneBookDto.getName());
        try {
            phoneBookRepository.save(phoneBookEntity);
            return PhoneBookResponseDto.builder().operation_type("edit")
                    .user_id(userId)
                    .operation_status("success")
                    .build();
        }
        catch (Exception e){
            return PhoneBookResponseDto.builder().operation_type("edit")
                    .user_id(userId)
                    .operation_status("fail")
                    .build();
        }
    }


    @Override
    public PhoneBookResponseDto delete(String userId) {
        try {
            phoneBookRepository.deleteById(userId);
            return PhoneBookResponseDto.builder().operation_type("delete")
                    .user_id(userId)
                    .operation_status("success")
                    .build();
        }
        catch (Exception e){
            return PhoneBookResponseDto.builder().operation_type("delete")
                    .user_id(userId)
                    .operation_status("fail")
                    .build();
        }
    }


    @Override
    public List<PhoneBookEntity> getAllUsers() {
        return phoneBookRepository.findAll();
    }


    @Override
    public ApiResponse dbHealthCheck() {
        try {
            int errorCode = check();
            if (errorCode != 1)
                return new ApiResponse("not ok");
            return new ApiResponse("ok");
        } catch (Exception ex) {
            return new ApiResponse("not ok");
        }
    }

    public int check() {
        List<Object> results = phoneBookRepository.checkConnection();
        return results.size();
    }
}


