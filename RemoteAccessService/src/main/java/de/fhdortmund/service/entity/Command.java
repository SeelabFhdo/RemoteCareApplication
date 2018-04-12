package de.fhdortmund.service.entity;


public class Command {

  private String commandtype;
  private String command;
  private String itemname;

  public Command(String commandtype, String command, String itemname) {
    this.commandtype = commandtype;
    this.command = command;
    this.itemname = itemname;
  }

  public Command() {

  }

  public String getCommandtype() {
    return commandtype;
  }

  public void setCommandtype(String commandtype) {
    this.commandtype = commandtype;
  }

  public String getCommand() {
    return command;
  }

  public void setCommand(String command) {
    this.command = command;
  }

  public String getItemname() {
    return itemname;
  }

  public void setItemname(String itemname) {
    this.itemname = itemname;
  }
}