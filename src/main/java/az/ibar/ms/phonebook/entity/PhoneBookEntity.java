package az.ibar.ms.phonebook.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@NoArgsConstructor
@Entity
@Data
@Table(name = "PHONEBOOK_DETAILS")
@Builder
public class PhoneBookEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private UUID id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}

