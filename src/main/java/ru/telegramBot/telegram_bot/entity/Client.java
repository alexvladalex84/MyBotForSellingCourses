package ru.telegramBot.telegram_bot.entity;

import ru.telegramBot.telegram_bot.DTO.StatusClient;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long pk;
    Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private Timestamp registeredAt;

    private String numberPhone;
    private StatusClient statusDiscount;
    @ManyToOne
    private Consultant consultant;

    public Client() {

    }

    public Client(Long pk, Long chatId, String firstName, String lastName, String userName, Timestamp registeredAt, String numberPhone, StatusClient statusDiscount, Consultant consultant) {
        this.pk = pk;
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.registeredAt = registeredAt;
        this.numberPhone = numberPhone;
        this.statusDiscount = statusDiscount;
        this.consultant = consultant;
    }

    public Long getPk() {
        return pk;
    }

    public void setPk(Long pk) {
        this.pk = pk;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }



    public String getFirstName() {
        try {

            return firstName;
        } catch (NullPointerException e) {

        }
        return null;

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {

        try {
            return lastName;
        } catch (NullPointerException e) {
        }
        return null;
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

    public Timestamp getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Timestamp registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public StatusClient getStatusDiscount() {
        return statusDiscount;
    }

    public void setStatusDiscount(StatusClient statusDiscount) {
        this.statusDiscount = statusDiscount;
    }

    public Consultant getConsultant() {
        return consultant;
    }

    public void setConsultant(Consultant consultant) {
        this.consultant = consultant;
    }

    @Override
    public String toString() {
        return "Client{" +
                "pk=" + pk +
                ", chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", registeredAt=" + registeredAt +
                ", numberPhone='" + numberPhone + '\'' +
                ", statusDiscount=" + statusDiscount +
                ", consultant=" + consultant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(pk, client.pk) && Objects.equals(chatId, client.chatId) && Objects.equals(firstName, client.firstName) && Objects.equals(lastName, client.lastName) && Objects.equals(userName, client.userName) && Objects.equals(registeredAt, client.registeredAt) && Objects.equals(numberPhone, client.numberPhone) && statusDiscount == client.statusDiscount && Objects.equals(consultant, client.consultant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, chatId, firstName, lastName, userName, registeredAt, numberPhone, statusDiscount, consultant);
    }
}
