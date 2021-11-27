package az.ibar.ms.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneBookResponseDto {
    private UUID user_id;
    private String operation_type;
    private String operation_status;

}
