package az.ibar.ms.phonebook.repository;

import az.ibar.ms.phonebook.entity.PhoneBookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PhoneBookRepository extends JpaRepository<PhoneBookEntity, String> {
    @Query(value = "select 1 from dual",nativeQuery = true)
    List<Object> checkConnection();

    PhoneBookEntity getPhoneBookEntityById(String userId);
}


