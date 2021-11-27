package az.ibar.ms.phonebook.repository;

import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBookEntity, UUID> {

}


