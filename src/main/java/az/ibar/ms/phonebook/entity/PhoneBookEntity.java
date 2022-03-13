package az.ibar.ms.phonebook.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "PHONEBOOK_DETAILS", schema = "mysql")
@Builder
public class PhoneBookEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "ID")
    private String id;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;
}

