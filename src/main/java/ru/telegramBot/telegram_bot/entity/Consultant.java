package ru.telegramBot.telegram_bot.entity;

import ru.telegramBot.telegram_bot.DTO.StatusConsultant;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "consultants")
public class Consultant {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long pk;
    private Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private String numberPhone;
    private StatusConsultant statusWork;
    @OneToMany
    private List<Client> listClients;

    public Consultant() {

    }

    public Consultant(long pk, Long chatId, String firstName, String lastName, String userName, String numberPhone, StatusConsultant statusWork, List<Client> listClients) {
        this.pk = pk;
        this.chatId = chatId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.numberPhone = numberPhone;
        this.statusWork = statusWork;
        this.listClients = listClients;
    }

    public long getPk() {
        return pk;
    }

    public void setPk(long pk) {
        this.pk = pk;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
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

    public List<Client> getListClients() {
        return listClients;
    }

    public void setListClients(List<Client> listClients) {
        this.listClients = listClients;
    }

    @Override
    public String toString() {
        return "Consultant{" +
                "pk=" + pk +
                ", chatId=" + chatId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", numberPhone='" + numberPhone + '\'' +
                ", statusWork=" + statusWork +
                ", listClients=" + listClients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consultant that = (Consultant) o;
        return pk == that.pk && Objects.equals(chatId, that.chatId) && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(userName, that.userName) && Objects.equals(numberPhone, that.numberPhone) && statusWork == that.statusWork && Objects.equals(listClients, that.listClients);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, chatId, firstName, lastName, userName, numberPhone, statusWork, listClients);
    }
}
