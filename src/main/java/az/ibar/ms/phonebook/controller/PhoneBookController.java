package az.ibar.ms.phonebook.controller;

import az.ibar.ms.phonebook.dto.PhoneBookDto;
import az.ibar.ms.phonebook.dto.PhoneBookResponseDto;
import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import az.ibar.ms.phonebook.service.PhoneBookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Validated
@RequestMapping("user")
public class PhoneBookController {

    private final PhoneBookService phoneBookService;


    @PostMapping("add")
    public ResponseEntity<PhoneBookResponseDto> addUser(@RequestBody PhoneBookDto request) {
        return ResponseEntity.ok(phoneBookService.save(request));
    }

    @PutMapping("edit/{userId}")
    public ResponseEntity<PhoneBookResponseDto> editUser(@PathVariable String userId, @RequestBody PhoneBookDto request) {
        return ResponseEntity.ok(phoneBookService.edit(userId, request));
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<PhoneBookResponseDto> deleteUser(@PathVariable String userId) {
        return ResponseEntity.ok(phoneBookService.delete(userId));
    }

    @GetMapping("list")
    public ResponseEntity<List<PhoneBookEntity>> getAllUsers() {
        return ResponseEntity.ok(phoneBookService.getAllUsers());
    }


}
