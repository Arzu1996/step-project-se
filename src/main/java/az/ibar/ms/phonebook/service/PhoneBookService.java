package az.ibar.ms.phonebook.service;

import az.ibar.ms.phonebook.dto.PhoneBookDto;
import az.ibar.ms.phonebook.dto.PhoneBookResponseDto;
import az.ibar.ms.phonebook.entity.PhoneBookEntity;

import java.util.List;
import java.util.UUID;

public interface PhoneBookService {

    PhoneBookResponseDto save(PhoneBookDto phoneBookDto);
    PhoneBookResponseDto edit(String userId, PhoneBookDto phoneBookDto);
    PhoneBookResponseDto delete(String userId);
    List<PhoneBookEntity> getAllUsers();

}
