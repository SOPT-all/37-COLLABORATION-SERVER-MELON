package sopt_collaboration_team4.melon.global.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class ApiResponse<T> {

    @JsonProperty("isSuccess")
    private final Boolean isSuccess;

    @JsonProperty("code")
    private final HttpStatus status;

    @JsonProperty("message")
    private final String message;

    @JsonProperty("result")
    private final T result;

    public static <T> ApiResponse<T> onSuccess(HttpStatus status,String message,T result) {
        return new ApiResponse<T>(true,status,message,result);
    }

    public static <T> ApiResponse<T> onSuccess(HttpStatus status, String message) {
        return new ApiResponse<>(true, status, message, null);
    }


    public static <T> ApiResponse<T> onFailure(HttpStatus status, String message, T result) {
        return new ApiResponse<>(false, status, message, result);
    }
}
