package space.itoncek.msg.exceptions;

public class ServerNotFoundException extends Throwable {
	public ServerNotFoundException(String reason) {
		super(reason);
	}
}
