package ru.eldar.dto;

public class MessageDto {
    private String message;

    public MessageDto() {
    }

    public MessageDto(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static MessageDtoBuilder builder() {
        return new MessageDtoBuilder();
    }

    public static class MessageDtoBuilder {
        private String message;

        public MessageDtoBuilder message(String message) {
            this.message = message;
            return this;
        }

        public MessageDto build() {
            return new MessageDto(this.message);
        }
    }
}
