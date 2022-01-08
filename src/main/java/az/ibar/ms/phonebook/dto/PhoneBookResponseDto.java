package az.ibar.ms.phonebook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneBookResponseDto {
    private String userId;
    private String operationType;
    private String operationStatus;

}
