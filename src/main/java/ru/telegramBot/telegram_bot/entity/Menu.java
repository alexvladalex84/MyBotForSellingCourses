package ru.telegramBot.telegram_bot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "menu_bot")
public class Menu {
    @Id
    @GeneratedValue
    private Integer pk;
    private String nameCommand;
    private String descriptionCommand;
    private String text;
    public Menu() {

    }

    public Menu(Integer pk, String nameCommand, String descriptionCommand, String text) {
        this.pk = pk;
        this.nameCommand = nameCommand;
        this.descriptionCommand = descriptionCommand;
        this.text = text;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getNameCommand() {
        return nameCommand;
    }

    public void setNameCommand(String nameCommand) {
        this.nameCommand = nameCommand;
    }

    public String getDescriptionCommand() {
        return descriptionCommand;
    }

    public void setDescriptionCommand(String descriptionCommand) {
        this.descriptionCommand = descriptionCommand;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "pk=" + pk +
                ", nameCommand='" + nameCommand + '\'' +
                ", descriptionCommand='" + descriptionCommand + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(pk, menu.pk) && Objects.equals(nameCommand, menu.nameCommand) && Objects.equals(descriptionCommand, menu.descriptionCommand) && Objects.equals(text, menu.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, nameCommand, descriptionCommand, text);
    }
}
