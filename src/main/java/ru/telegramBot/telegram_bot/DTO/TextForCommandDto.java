package ru.telegramBot.telegram_bot.DTO;

import java.util.Objects;

public class TextForCommandDto {
    private Integer pk;
    private String nameCommand;

    private String text;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "textForCommandDto{" +
                "pk=" + pk +
                ", nameCommand='" + nameCommand + '\'' +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextForCommandDto that = (TextForCommandDto) o;
        return Objects.equals(pk, that.pk) && Objects.equals(nameCommand, that.nameCommand) && Objects.equals(text, that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk, nameCommand, text);
    }
}
