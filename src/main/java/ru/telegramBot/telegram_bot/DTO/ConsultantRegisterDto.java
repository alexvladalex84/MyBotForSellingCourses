package ru.telegramBot.telegram_bot.DTO;

import java.util.Objects;

public class ConsultantRegisterDto {
    private long pk;
    private String firstName;
    private String lastName;
    private String userName;
    private String numberPhone;
    private StatusConsultant statusWork;
    private Long chatId;

    public ConsultantRegisterDto() {

    }


    public ConsultantRegisterDto(long pk, String firstName, String lastName,
                                 String userName, String numberPhone, StatusConsultant statusWork, Long chatId) {
        this.pk = pk;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.numberPhone = numberPhone;
        this.statusWork = statusWork;
        this.chatId = chatId;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public StatusConsultant getStatusWork() {
        return statusWork;
    }

    public void setStatusWork(StatusConsultant statusWork) {
        this.statusWork = statusWork;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    @Override
    public String toString() {
        return "ConsultantRegisterDto{" +
                "pk=" + pk +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", statusWork=" + statusWork +
                ", chatId=" + chatId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConsultantRegisterDto that = (ConsultantRegisterDto) o;
        return pk == that.pk && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(userName, that.userName) && Objects.equals(numberPhone, that.numberPhone) && statusWork == that.statusWork && Objects.equals(chatId, that.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, firstName, lastName, userName, numberPhone, statusWork, chatId);
    }
}
