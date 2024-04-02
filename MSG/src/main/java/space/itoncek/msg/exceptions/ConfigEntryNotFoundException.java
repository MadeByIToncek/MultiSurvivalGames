package space.itoncek.msg.exceptions;

public class ConfigEntryNotFoundException extends Throwable {
	public ConfigEntryNotFoundException(String key) {
		super("Unable to find config entry " + key);
	}
}
