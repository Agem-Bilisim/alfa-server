package tr.com.agem.alfa.exception;

/**
 * @author <a href="mailto:ali.ozeren@agem.com.tr">Ali Ozkan Ozeren</a>
 *
 */
public class AlfaException extends RuntimeException 
{

	private static final long serialVersionUID = 295511169041299325L;

	public AlfaException()
	{
		super();
	}

	public AlfaException(Throwable exception)
	{
		super(exception);
	}

	public AlfaException(String message)
	{
	}

}
