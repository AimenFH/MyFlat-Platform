package FHCampus.CarRental.dtos;

import lombok.Data;

@Data
public class AuthenticationRequest {

    private String email;

    private String password;

    public static class MessageResponse {
        private String message;

        public MessageResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
